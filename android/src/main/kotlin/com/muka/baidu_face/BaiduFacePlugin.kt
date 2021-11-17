package com.muka.baidu_face

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.annotation.NonNull

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import com.baidu.idl.face.platform.FaceConfig;
import com.baidu.idl.face.platform.FaceEnvironment;
import com.baidu.idl.face.platform.FaceSDKManager;
import com.baidu.idl.face.platform.LivenessTypeEnum;
import com.baidu.idl.face.platform.listener.IInitCallback;
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.PluginRegistry
import android.os.Bundle
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.ThreadMode

import org.greenrobot.eventbus.Subscribe





/** BaiduFacePlugin */
class BaiduFacePlugin : FlutterPlugin, MethodCallHandler, ActivityAware,
    PluginRegistry.ActivityResultListener {
    private lateinit var channel: MethodChannel
    private lateinit var activity: Activity
    private var livenessCallback: LivenessCallback? = null
    private var detectCallback: DetectCallback? = null

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "plugins.muka.com/baidu_face")
        channel.setMethodCallHandler(this)
        EventBus.getDefault().register(this);
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        when (call.method) {
            "init" -> {
                var licenseID: String? = call.argument<String>("licenseID")
                // 初始化SDK
                FaceSDKManager.getInstance().initialize(activity, licenseID,
                    "idl-license.face-android", object : IInitCallback {
                        override fun initSuccess() {
                            Log.d("初始化","成功")
                        }

                        override fun initFailure(errCode: Int, errMsg: String) {
                            Log.e("初始化",errMsg)
                            result.success(false)
                        }
                    })
                result.success(true)
                // 随机动作
                Config.isLivenessRandom = true
                // 根据需求添加活体动作
                Config.livenessList = ArrayList()
                (Config.livenessList as ArrayList<LivenessTypeEnum>).add(LivenessTypeEnum.Eye)

                // 设置 FaceConfig
                val config: FaceConfig = FaceSDKManager.getInstance().faceConfig
                // 设置可检测的最小人脸阈值
                config.minFaceSize = FaceEnvironment.VALUE_MIN_FACE_SIZE

                // 设置可检测到人脸的阈值
                config.notFaceValue = FaceEnvironment.VALUE_NOT_FACE_THRESHOLD

                // 设置模糊度阈值
                config.blurnessValue = FaceEnvironment.VALUE_BLURNESS

                // 设置光照阈值（范围0-255）
                config.brightnessValue = FaceEnvironment.VALUE_BRIGHTNESS

                // 设置遮挡阈值
                config.occlusionValue = FaceEnvironment.VALUE_OCCLUSION

                // 设置人脸姿态角阈值
                config.headPitchValue = FaceEnvironment.VALUE_HEAD_PITCH
                config.headYawValue = FaceEnvironment.VALUE_HEAD_YAW

                // 设置闭眼阈值
                config.eyeClosedValue = FaceEnvironment.VALUE_CLOSE_EYES

                // 设置图片缓存数量
                config.cacheImageNum = FaceEnvironment.VALUE_CACHE_IMAGE_NUM
                config.livenessTypeList = Config.livenessList

                // 设置动作活体是否随机
                config.isLivenessRandom = Config.isLivenessRandom

                // 设置开启提示音
                config.isSound = true

                // 原图缩放系数
                config.scale = FaceEnvironment.VALUE_SCALE
                // 抠图高的设定，为了保证好的抠图效果，我们要求高宽比是4：3，所以会在内部进行计算，只需要传入高即可
                config.cropHeight = FaceEnvironment.VALUE_CROP_HEIGHT
                // 加密类型，0：Base64加密，上传时image_sec传false；1：百度加密文件加密，上传时image_sec传true
                config.secType = FaceEnvironment.VALUE_SEC_TYPE
                FaceSDKManager.getInstance().faceConfig = config
                // 初始化资源文件
                FaceSDKResSettings.initializeResId()
            }
            "likeness" -> {
                var language: String? = call.argument<String>("language")
                livenessCallback = LivenessCallback(result)
                val intent = Intent(activity, FaceLivenessExpActivity::class.java)
                val mBundle = Bundle()
                mBundle.putString(
                    "language",
                    if (language == null || "" == language) "zh" else language
                )
                intent.putExtras(mBundle)
                activity.startActivity(intent)
            }
            "detect" -> {
                var language: String? = call.argument<String>("language")
                detectCallback = DetectCallback(result)
                val intent = Intent(activity, FaceDetectExpActivity::class.java)
                val mBundle = Bundle()
                mBundle.putString(
                    "language",
                    if (language == null || "" == language) "zh" else language
                )
                intent.putExtras(mBundle)
                activity.startActivity(intent)
            }
            else -> {
                result.notImplemented()
            }
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
        EventBus.getDefault().unregister(this);
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        this.activity = binding.activity
        binding.addActivityResultListener(this)
    }

    override fun onDetachedFromActivityForConfigChanges() {

    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        onAttachedToActivity(binding)
    }

    override fun onDetachedFromActivity() {
        EventBus.getDefault().unregister(this);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        return false
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onGetMessage(message: MessageEvent) {
        if (message.type === "1") {
            livenessCallback!!.sucess(message.message)
        } else {
            detectCallback!!.sucess(message.message)
        }
    }

    internal class LivenessCallback(private val result: Result) {
        fun sucess(image: String) {
            val map: MutableMap<String, String> = HashMap()
            map["success"] = "true"
            map["srcimage"] = image
            result.success(map)
        }

        fun failed() {
            val map: MutableMap<String, String> = HashMap()
            map["success"] = "false"
            result.success(map)
        }
    }


    internal class DetectCallback(private val result: Result) {
        fun sucess(image: String) {
            val map: MutableMap<String, String> = HashMap()
            map["success"] = "true"
            map["srcimage"] = image
            result.success(map)
        }

        fun failed() {
            val map: MutableMap<String, String> = HashMap()
            map["success"] = "false"
            result.success(map)
        }
    }
}

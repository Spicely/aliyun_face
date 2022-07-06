package com.muka.aliyun_face

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.annotation.NonNull
import com.aliyun.aliyunface.api.ZIMCallback
import com.aliyun.aliyunface.api.ZIMFacade
import com.aliyun.aliyunface.api.ZIMFacadeBuilder

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry

/** AliyunFacePlugin */
class AliyunFacePlugin : FlutterPlugin, MethodCallHandler, ActivityAware,
    PluginRegistry.ActivityResultListener {
    private lateinit var activity: Activity
    private lateinit var channel: MethodChannel

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "plugins.muka.com/aliyun_face")
        channel.setMethodCallHandler(this)
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        when (call.method) {
            "init" -> {

                ZIMFacade.install(activity);

                result.success(true)
            }
            "getMetaInfos" -> {
                val metaInfos = ZIMFacade.getMetaInfos(activity)
                result.success(metaInfos)
            }
            "verify" -> {
                var zimID: String? = call.argument<String>("zimID")
                var zimFacade = ZIMFacadeBuilder.create(activity);
                zimFacade.verify(zimID, true) { response ->
                    if (null != response && 1000 == response.code) {
                        Log.d("认证----", "成功")
                        result.success(true)
                    } else {
                        Log.d("认证----", "失败")
                        result.success(false)
                    }
                    true
                }
            }
            else -> {
                result.notImplemented()
            }
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
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
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        return true
    }
}
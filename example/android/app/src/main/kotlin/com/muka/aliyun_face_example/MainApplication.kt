package com.muka.aliyun_face_example

import com.aliyun.aliyunface.api.ZIMFacade
import com.aliyun.aliyunface.api.ZIMFacadeBuilder
import io.flutter.Log
import io.flutter.app.FlutterApplication

class MainApplication : FlutterApplication() {
    override fun onCreate() {
        super.onCreate()
        initFacePlugin();

    }

    private fun initFacePlugin() {
        ZIMFacade.install(this);
//        var zimFacade = ZIMFacadeBuilder.create(applicationContext);
//        zimFacade.verify("1000002437", true) { response ->
//            if (null != response && 1000 == response.code) {
//                Log.d("认证----", "成功")
////                        result.success(true)
//            } else {
//                Log.d("认证----", "失败")
////                        result.success(false)
//            }
//            true
////                }
//        }
    }
}
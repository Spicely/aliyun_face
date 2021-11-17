package com.muka.baidu_face

import android.os.Bundle;
import android.view.View;
import com.muka.baidu_face.ui.CollectionSuccessActivity
import com.muka.baidu_face.ui.utils.IntentUtils


import org.greenrobot.eventbus.EventBus;

/**
 * 采集成功页面
 * Created by v_liujialu01 on 2020/4/1.
 */
class CollectionSuccessExpActivity : CollectionSuccessActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // 回到首页
    override fun onReturnHome(v: View?) {
        super.onReturnHome(v)
        val bmpStr = IntentUtils.getInstance().bitmap
        if ("FaceLivenessExpActivity" == mDestroyType) {
            //ExampleApplication.destroyActivity("FaceLivenessExpActivity");
            EventBus.getDefault().post(MessageEvent.getInstance("1", bmpStr))
        }
        if ("FaceDetectExpActivity" == mDestroyType) {
            // ExampleApplication.destroyActivity("FaceDetectExpActivity");
            EventBus.getDefault().post(MessageEvent.getInstance("2", bmpStr))
        }
        finish()
    }
}

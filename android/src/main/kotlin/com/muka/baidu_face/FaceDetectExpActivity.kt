package com.muka.baidu_face

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.baidu.idl.face.platform.FaceStatusNewEnum;
import com.baidu.idl.face.platform.model.ImageInfo;
import com.muka.baidu_face.FaceApplication.addDestroyActivity
import com.muka.baidu_face.ui.FaceDetectActivity
import com.muka.baidu_face.ui.utils.IntentUtils
import com.muka.baidu_face.ui.widget.TimeoutDialog


import java.util.HashMap;

class FaceDetectExpActivity : FaceDetectActivity(), TimeoutDialog.OnTimeoutDialogClickListener {
    private var mTimeoutDialog: TimeoutDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 添加至销毁列表
        addDestroyActivity(
            this@FaceDetectExpActivity,
            "FaceDetectExpActivity"
        )
    }

    override fun onDetectCompletion(
        status: FaceStatusNewEnum, message: String?,
        base64ImageCropMap: HashMap<String?, ImageInfo?>?,
        base64ImageSrcMap: HashMap<String?, ImageInfo?>?
    ) {
        super.onDetectCompletion(status, message, base64ImageCropMap, base64ImageSrcMap)
        if (status == FaceStatusNewEnum.OK && mIsCompletion) {
            Log.i("人脸图像采集", "人脸图像采集,采集成功")
            IntentUtils.getInstance().bitmap = mBmpStr
            val intent = Intent(
                this@FaceDetectExpActivity,
                CollectionSuccessExpActivity::class.java
            )
            intent.putExtra("destroyType", "FaceDetectExpActivity")
            finish()
            startActivity(intent)
            //            Intent intent = new Intent();
//            intent.putExtra("image", mBmpStr);
//            intent.putExtra("cropimage", "test");
//            intent.putExtra("success", true);
//            this.setResult(10013, intent);
//            finish();
        } else if (status == FaceStatusNewEnum.DetectRemindCodeTimeout) {
            if (mViewBg != null) {
                mViewBg.visibility = View.VISIBLE
            }
            showMessageDialog()
            //            this.getIntent().putExtra("success", false);
//            this.setResult(10013, this.getIntent());
//            finish();
        }
    }

    private fun showMessageDialog() {
        mTimeoutDialog = TimeoutDialog(this)
        mTimeoutDialog!!.setDialogListener(this)
        mTimeoutDialog!!.setCanceledOnTouchOutside(false)
        mTimeoutDialog!!.setCancelable(false)
        mTimeoutDialog!!.show()
        onPause()
    }

    override fun finish() {
        super.finish()
    }

    override fun onRecollect() {
        if (mTimeoutDialog != null) {
            mTimeoutDialog!!.dismiss()
        }
        if (mViewBg != null) {
            mViewBg.visibility = View.GONE
        }
        onResume()
    }

    override fun onReturn() {
        if (mTimeoutDialog != null) {
            mTimeoutDialog!!.dismiss()
        }
        finish()
    }
}

package com.muka.baidu_face

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.baidu.idl.face.platform.FaceStatusNewEnum;
import com.baidu.idl.face.platform.model.ImageInfo;
import com.muka.baidu_face.FaceApplication.addDestroyActivity
import com.muka.baidu_face.ui.FaceLivenessActivity
import com.muka.baidu_face.ui.utils.IntentUtils
import com.muka.baidu_face.ui.widget.TimeoutDialog

import java.util.HashMap;

class FaceLivenessExpActivity : FaceLivenessActivity(),
    TimeoutDialog.OnTimeoutDialogClickListener {
    private var mTimeoutDialog: TimeoutDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 添加至销毁列表
        addDestroyActivity(
            this@FaceLivenessExpActivity,
            "FaceLivenessExpActivity"
        )
    }

    override fun onLivenessCompletion(
        status: FaceStatusNewEnum, message: String?,
        base64ImageCropMap: HashMap<String?, ImageInfo?>?,
        base64ImageSrcMap: HashMap<String?, ImageInfo?>?, currentLivenessCount: Int
    ) {
        super.onLivenessCompletion(
            status,
            message,
            base64ImageCropMap,
            base64ImageSrcMap,
            currentLivenessCount
        )
        if (status == FaceStatusNewEnum.OK && mIsCompletion) {
            // showMessageDialog("活体检测", "检测成功");
            IntentUtils.getInstance().bitmap = mBmpStr
            val intent = Intent(
                this@FaceLivenessExpActivity,
                CollectionSuccessExpActivity::class.java
            )
            intent.putExtra("destroyType", "FaceLivenessExpActivity")
            finish()
            startActivity(intent)
        } else if (status == FaceStatusNewEnum.DetectRemindCodeTimeout) {
            if (mViewBg != null) {
                mViewBg.visibility = View.VISIBLE
            }
            showMessageDialog()
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

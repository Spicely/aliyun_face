package com.muka.baidu_face

import android.app.Activity

import com.baidu.idl.face.platform.LivenessTypeEnum

import android.app.Application


object FaceApplication : Application() {
    // 动作活体条目集合
    var livenessList: List<LivenessTypeEnum> = ArrayList()

    // 活体随机开关
    var isLivenessRandom = false

    // 语音播报开关
    var isOpenSound = true

    // 活体检测开关
    var isActionLive = true
    private val destroyMap: MutableMap<String, Activity> = HashMap()

    /**
     * 添加到销毁队列
     * @param activity 要销毁的activity
     */
    fun addDestroyActivity(activity: Activity, activityName: String) {
        destroyMap[activityName] = activity
    }

    /**
     * 销毁指定Activity
     */
    fun destroyActivity(activityName: String?) {
        val keySet: Set<String> = destroyMap.keys
        for (key in keySet) {
            destroyMap[key]!!.finish()
        }
    }
}

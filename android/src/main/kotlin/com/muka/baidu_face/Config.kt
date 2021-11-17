package com.muka.baidu_face

import com.baidu.idl.face.platform.LivenessTypeEnum


class Config {
    companion object {
        var livenessList: List<LivenessTypeEnum> = ArrayList()
        var isLivenessRandom = false
    }

}
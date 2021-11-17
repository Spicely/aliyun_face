package com.muka.baidu_face

/**
 * Author: 苗
 * Time: $(Date)
 * Description:
 */
class MessageEvent private constructor(val type: String, val message: String) {
    companion object {
        fun getInstance(type: String, message: String): MessageEvent {
            return MessageEvent(type, message)
        }
    }
}

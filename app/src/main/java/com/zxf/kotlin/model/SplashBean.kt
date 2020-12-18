package com.zxf.kotlin.model

/**
 * 启动页
 */
class SplashBean(s: String) {
    fun SplashBean(message: String?) {
        this.message = message
    }

    var message: String? = null
}
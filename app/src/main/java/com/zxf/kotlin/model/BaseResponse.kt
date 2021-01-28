package com.zxf.kotlin.model

class BaseResponse<T> {
    val errorCode:Int = 0
    val errorMsg: String? = null
    val data: T? = null
}
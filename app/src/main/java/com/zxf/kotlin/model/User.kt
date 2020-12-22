package com.zxf.kotlin.model

import io.realm.RealmObject

open class User : RealmObject() {
    var userName: String? = null
    var password: String? = null
    var isLogin: Boolean? = null
}
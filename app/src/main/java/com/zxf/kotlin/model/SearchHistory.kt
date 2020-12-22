package com.zxf.kotlin.model

import io.realm.RealmObject

/**
 * 搜索历史数据类
 */
open class SearchHistory : RealmObject() {
     var keyword:String?=null
}
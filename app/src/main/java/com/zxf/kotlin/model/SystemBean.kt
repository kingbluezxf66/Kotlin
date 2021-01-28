package com.zxf.kotlin.model

import com.chad.library.adapter.base.entity.MultiItemEntity

class SystemBean {
    /* children : [{"children":[],"courseId":13,"id":60,"name":"Android Studio相关","order":1000,"parentChapterId":150,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":169,"name":"gradle","order":1001,"parentChapterId":150,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":269,"name":"官方发布","order":1002,"parentChapterId":150,"userControlSetTop":false,"visible":1}]
         * courseId : 13
         * id : 150
         * name : 开发环境
         * order : 1
         * parentChapterId : 0
         * userControlSetTop : false
         * visible : 1
         */
    private val courseId = 0
    private val id: String? = null
    private val name: String? = null
    private val order = 0
    private val parentChapterId = 0
    private val userControlSetTop = false
    private val visible = 0
    private val children: List<ChildrenBean>? = null

    class ChildrenBean : MultiItemEntity {
        /**
         * children : []
         * courseId : 13
         * id : 60
         * name : Android Studio相关
         * order : 1000
         * parentChapterId : 150
         * userControlSetTop : false
         * visible : 1
         */
        var courseId = 0
        var id: String? = null
        var name: String? = null
        var order = 0
        var parentChapterId = 0
        var isUserControlSetTop = false
        var visible = 0
        var children: List<*>? = null
        override fun getItemType(): Int {
            TODO("Not yet implemented")
        }

//        val itemType: Int
//            get() = SystemMultiAdapter.TYPE_LEVEL_1
    }
}
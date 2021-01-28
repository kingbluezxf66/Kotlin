package com.zxf.kotlin.base

open class BasePresenter<v : BaseView> {
    var mIView: BaseView? = null

    /**
     * 绑定View的引用
     * @param v view
     */
    fun attachView(v: BaseView) {
        mIView = v
    }

    /**
     * 解绑View
     */
    fun detachView() {
        mIView = null
    }
}
package com.zxf.kotlin.base

import android.os.Bundle
import com.blankj.utilcode.util.ToastUtils

abstract class BaseMVPFragment<V : BaseView, P : BasePresenter<*>> : BaseFragment(), BaseView {
    lateinit var mPresenter: P
    override fun initData() {
        super.initData()
        mPresenter = initPresenter() as P
        if (mPresenter != null) {
            mPresenter!!.attachView(this as V)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mPresenter != null) {
            mPresenter.detachView()
        }
    }

    override fun showToast(msg: String) {
        ToastUtils.showShort(msg)
    }

    override fun showWaitDialog(waitMsg: String) {
        showWaitDialog(waitMsg)
    }

    override fun hideWaitDialog() {
        hideWaitDialog()
    }

    override fun hideKeybord() {
        hideKeybord()
    }

    override fun startNewActivity(clz: Class<*>) {
        startActivity(clz)
    }

    override fun startNewActivity(clz: Class<*>, bundle: Bundle) {
        startActivity(clz, bundle)
    }

    override fun startNewActivityForResult(clz: Class<*>, bundle: Bundle, requestCode: Int) {
        startActivityForResult(clz, bundle, requestCode)
    }

}

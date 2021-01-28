package com.zxf.kotlin.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ToastUtils

abstract class BaseMVPActivity<V : BaseView, P : BasePresenter<*>> : BaseActivity(), BaseView {
    lateinit var mPresenter: P
    override fun initData() {
        super.initData()
        mPresenter = initPresenter() as P
        mPresenter!!.attachView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    override fun showToast(msg: String) {
        ToastUtils.showShort(msg)
    }

    override fun showWaitDialog(waitMsg: String) {
        showProgressDialog(waitMsg)
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

    override fun startFragment(containerId: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction().add(containerId, fragment).commit()
    }
}
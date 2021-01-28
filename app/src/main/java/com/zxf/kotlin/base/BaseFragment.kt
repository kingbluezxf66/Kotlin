package com.zxf.kotlin.base

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.zxf.kotlin.MyApplication

abstract class BaseFragment : Fragment() {
    lateinit var mContext: Context
    lateinit var mActivity: Activity
    lateinit var mWaitProgressDialog: ProgressDialog
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context;
        mActivity = context as Activity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (getLayoutView() != null) {
            return getLayoutView()
        } else {
            return inflater.inflate(getLayout(), container, false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        getBundle(arguments)
        initData()
        initUI(view, savedInstanceState)
    }

    fun getLayoutView(): View? {
        return null
    }

    open fun initData() {
        mContext = MyApplication.getContext()
    }

    /**
     * 显示提示框
     */
    open fun showProgressDialog(msg: String) {
        if (mWaitProgressDialog == null) {
            mWaitProgressDialog = ProgressDialog(activity)
        }
        mWaitProgressDialog.setMessage(msg)
        mWaitProgressDialog.show()
    }

    /**
     * 隐藏提示框
     */
    open fun hideProgressDialog() {
        if (mWaitProgressDialog != null) {
            mWaitProgressDialog.dismiss()
        }
    }

    /**
     * 页面跳转
     */
    fun startActivity(clz: Class<*>) {
        startActivity(Intent(activity, clz))
    }

    /**
     * 携带数据页面跳转
     */
    fun startActivity(clz: Class<*>, bundle: Bundle?) {
        val intent: Intent = Intent()
        intent.setClass(activity, clz)
        if (bundle != null) intent.putExtras(bundle)
        startActivity(intent)
    }

    fun startActivityForResult(clz: Class<*>?, bundle: Bundle?, requestCode: Int) {
        val intent: Intent = Intent()
        intent.setClass(activity, clz)
        if (bundle != null) intent.putExtras(bundle)
        startActivityForResult(intent, requestCode)
    }

    /**
     * 跳转fragment
     */
    fun startFragment(containerId: Int, fragment: Fragment) {
        activity?.supportFragmentManager?.beginTransaction()?.replace(containerId, fragment)
            ?.commit()
    }

    /**
     * 点击空白位置，隐藏软键盘
     */
    fun hideKeyboard(): Boolean {
        var mInputManager: InputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return mInputManager.hideSoftInputFromWindow(activity!!.currentFocus.windowToken, 0)
    }

    open fun getBundle(bundle: Bundle?) {}
    protected abstract fun initUI(view: View, savedInstanceState: Bundle?)
    protected abstract fun getLayout(): Int
}
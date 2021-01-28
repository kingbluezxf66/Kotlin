package com.zxf.kotlin.base

import android.os.Bundle
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment

/**
 * M
 * V view
 * P presenter
 */
interface BaseView {
    /**
     * 初始化presenter
     *
     *
     * 此方法返回的presenter对象不可为空
     */
    @NonNull
    fun initPresenter(): BasePresenter<*>

    /**
     * 显示toast消息
     *
     * @param msg 要显示的toast消息字符串
     */
    fun showToast(msg: String)

    /**
     * 显示等待dialog
     *
     * @param waitMsg 等待消息字符串
     */
    fun showWaitDialog(waitMsg: String)

    /**
     * 隐藏等待dialog
     */
    fun hideWaitDialog()

    /**
     * 隐藏键盘
     */
    fun hideKeybord()

    /**
     * 跳往新的Activity
     *
     * @param clz 要跳往的Activity
     */
    fun startNewActivity(@NonNull clz: Class<*>)

    /**
     * 跳往新的Activity
     *
     * @param clz    要跳往的Activity
     * @param bundle 携带的bundle数据
     */
    fun startNewActivity(@NonNull clz: Class<*>, bundle: Bundle)

    /**
     * 跳往新的Activity
     *
     * @param clz         要跳转的Activity
     * @param bundle      bundel数据
     * @param requestCode requestCode
     */
    fun startNewActivityForResult(
        @NonNull clz: Class<*>,
        bundle: Bundle,
        requestCode: Int
    )

    fun startFragment(containerId: Int, @NonNull fragment: Fragment)
}
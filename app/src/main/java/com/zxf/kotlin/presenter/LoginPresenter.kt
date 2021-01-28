package com.zxf.kotlin.presenter

import android.content.Context
import android.text.TextUtils
import com.blankj.utilcode.util.RegexUtils
import com.zxf.kotlin.contract.LoginContract
import com.zxf.kotlin.model.RegistResponse
import com.zxf.kotlin.network.HttpObservable
import com.zxf.kotlin.network.HttpObserver
import com.zxf.kotlin.network.RetrofitHelper
import io.reactivex.Observable
import java.util.*
import javax.annotation.Nonnull

/**
 * 登陆数据请求
 */
class LoginPresenter : LoginContract.Presenter() {
    lateinit var mContext: Context
    val REGISTCODE: Int = 1000

    @Nonnull
    fun LoginPresenter(context: Context) {
        mContext = context
    }

    override fun goLogin(userName: String, password: String) {
        if (mIView == null)
            return
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
            mIView!!.showToast("用户名或者密码不能为空")
        }
        if (RegexUtils.isMobileExact(userName)) {
            mIView!!.showToast("请输入正确手机号")
        }
        val map: MutableMap<String, String> = HashMap()
        map["username"] = userName
        map["password"] = password


    }

    override fun goRegist() {
        TODO("Not yet implemented")
    }
}



package com.zxf.kotlin.contract

import com.zxf.kotlin.base.BasePresenter
import com.zxf.kotlin.base.BaseView

open interface LoginContract {
    interface LoginView : BaseView {
        fun getUserName(): String
        fun getPassword(): String
        fun loginSuccess()
    }

    abstract class Presenter : BasePresenter<LoginView>() {
        abstract fun goLogin(userName: String, password: String)
        abstract fun goRegist()
    }
}
package com.zxf.kotlin.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zxf.kotlin.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
//        var basicBinding: ActivitySplashBinding =
//            DataBindingUtil.setContentView(this, R.layout.activity_splash)
//        val splashBean = SplashBean("跳转")
//        basicBinding.splash = splashBean
    }
}
package com.zxf.kotlin.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.zxf.kotlin.MyApplication
import com.zxf.kotlin.R
import com.zxf.kotlin.databinding.ActivitySplashBinding
import com.zxf.kotlin.help.DataBaseHelper
import com.zxf.kotlin.model.SplashBean
import com.zxf.kotlin.model.User
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {
    private lateinit var basicBinding: ActivitySplashBinding
    private var isClick = false
    private lateinit var disposable: Disposable

    @Inject
    lateinit var dataBaseHelper: DataBaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        basicBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        MyApplication.getDataBseHelper().inject(this)
        val splashBean = SplashBean()
        splashBean.message = "跳转"
        basicBinding.splash = splashBean
        countTime()
    }

    /**
     * 使用rxjava实现倒计时功能
     */
    fun countTime() {
        disposable = Flowable.intervalRange(0, 3, 0, 1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { aLong -> basicBinding.circleProgress.setText((3 - aLong).toString() + "s") }
            .doOnComplete {
                //跳转
                if (!isClick) {
                    turnAnotherActivity()
                }
            }
            .subscribe()

    }

    fun turnAnother() {
        isClick = true
        turnAnotherActivity()
    }

    fun turnAnotherActivity() {
        var user: User? = dataBaseHelper.getUserInfo()
        if (user == null) {
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
        } else {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        }
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (disposable != null) {
            disposable.dispose()
        }
    }
}
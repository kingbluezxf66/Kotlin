package com.zxf.kotlin.dagger

import com.zxf.kotlin.ui.activity.SplashActivity
import dagger.Component

/**
 * 注解注入器
 */
@ApplicationScope
@Component(modules = [DataBaseHelperModule::class])
interface DataBaseHelperComponent {
    fun inject(splashActivity: SplashActivity)
}
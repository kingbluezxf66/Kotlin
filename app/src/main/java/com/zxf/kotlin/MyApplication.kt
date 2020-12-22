package com.zxf.kotlin

import android.app.Application
import com.zxf.kotlin.dagger.DaggerDataBaseHelperComponent
import com.zxf.kotlin.dagger.DataBaseHelperComponent
import io.realm.Realm

class MyApplication : Application() {
    lateinit var dataBaseHelperComponent: DataBaseHelperComponent

    /**
     * 获取单一实例对象
     *
     * kotlin中的object 与companion object的区别
     * object:静态对象
     * companion object:伴生对象，一个类中只能有一个，类似java中的静态方法
     */
    companion object {
        private var instance: Application? = null
        private var dataBaseHelperComponent: DataBaseHelperComponent? = null
        fun getContext() = instance!!
        fun getDataBseHelper() = DaggerDataBaseHelperComponent.builder().build()
    }


    override fun onCreate() {
        super.onCreate()
        instance = this
        Realm.init(this)
    }
}
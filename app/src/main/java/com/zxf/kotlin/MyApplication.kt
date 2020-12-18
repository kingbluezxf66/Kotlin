package com.zxf.kotlin

import android.app.Application
import android.content.Context

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }

     fun getApplicationContext(context: Context): MyApplication? {
        return context.applicationContext as MyApplication
    }
}
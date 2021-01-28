package com.zxf.kotlin.network

import android.app.Application
import android.content.Context
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

class AddCookiedsInterceptor(context: Application) : Interceptor {
    lateinit var context: Context

    fun AddCookiesInterceptor(context: Context) {
        this.context = context
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val preferences: HashSet<String> = context.getSharedPreferences(
            "config",
            Context.MODE_PRIVATE
        ).getStringSet("cookie", null) as HashSet<String>
        if (preferences != null) {
            for (cookie in preferences) {
                builder.addHeader("Cookie", cookie)
                Log.v(
                    "OkHttp",
                    "Adding Header: $cookie"
                ) // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
            }
        }
        return chain.proceed(builder.build())
    }
}
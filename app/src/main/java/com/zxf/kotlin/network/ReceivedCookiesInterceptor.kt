package com.zxf.kotlin.network

import android.app.Application
import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

class ReceivedCookiesInterceptor(context: Application) : Interceptor {
    lateinit var context: Context

    fun AddCookiesInterceptor(context: Context) {
        this.context = context
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            val cookies = HashSet<String>()
            for (header in originalResponse.headers("Set-Cookie")) {
                cookies.add(header)
            }
            val config =
                context.getSharedPreferences("config", Context.MODE_PRIVATE)
                    .edit()
            config.putStringSet("cookie", cookies)
            config.commit()
        }

        return originalResponse

    }
}
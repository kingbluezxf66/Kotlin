package com.zxf.kotlin.network

import android.util.Log
import com.google.gson.Gson
import com.zxf.kotlin.BuildConfig
import com.zxf.kotlin.MyApplication
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory.create
import java.util.concurrent.TimeUnit

open class RetrofitHelper {
    companion object {
        //使用 const修饰的变量
        val dataManager: DataManager = DataManager(getRequestInterface())


        fun getRequestInterface(): RequestInterface? {

            /*
             **打印retrofit信息部分
             */
            val loggingInterceptor =
                HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> //打印retrofit日志
                    Log.e("RetrofitLog", "retrofitBack = $message")
                })

            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            //okhttp设置部分，此处还可再设置网络参数
            val client = OkHttpClient.Builder() //okhttp设置部分，此处还可再设置网络参数
                .connectTimeout(30, TimeUnit.SECONDS)
                .callTimeout(30, TimeUnit.SECONDS)
                .addNetworkInterceptor(loggingInterceptor)
                .addInterceptor(AddCookiedsInterceptor(MyApplication.getContext())) //持久化cookie
                .addInterceptor(ReceivedCookiesInterceptor(MyApplication.getContext()))
                .build()
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.HostUrl)
                .addConverterFactory(create(Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
            return retrofit.create(RequestInterface::class.java)
        }
    }
}
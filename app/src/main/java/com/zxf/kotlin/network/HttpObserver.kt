package com.zxf.kotlin.network

import android.content.Context
import android.content.Intent
import android.net.ParseException
import android.text.TextUtils
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.ToastUtils
import com.google.gson.JsonIOException
import com.google.gson.JsonParseException
import com.zxf.kotlin.model.BaseResponse
import com.zxf.kotlin.ui.activity.LoginActivity
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class HttpObserver<T>() : Observer<BaseResponse<T>> {
    lateinit var mContext:Context
    lateinit var mSwipeRefreshLayout:SwipeRefreshLayout
    constructor(context: Context, swipeRefreshLayout: SwipeRefreshLayout?) : this() {
        mContext = context
        if (swipeRefreshLayout != null) {
            mSwipeRefreshLayout = swipeRefreshLayout
        }
    }

    override fun onSubscribe(d: Disposable) {
        if (!NetworkUtils.isConnected()) {
            ToastUtils.showShort("暂无网络，请检查网络连接")
            d.dispose()
            onHttpEnd()
            if (mSwipeRefreshLayout != null) {
                mSwipeRefreshLayout.isRefreshing = false
            }
        }
    }

    override fun onNext(t: BaseResponse<T>?) {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.isRefreshing = false
        }
        if (t == null) {
            onFail(t)
        } else {
            val errorCode: Int = t.errorCode
            //未登录-1001，其他错误码为-1，成功为0，
            if (errorCode == 0) {
                t.data?.let { onSuccess(it) }
            } else if (errorCode == -1001) {
                onUnlogin()
            } else {
                onFail(t)
            }
        }
    }

    fun onUnlogin() {
        mContext.startActivity(Intent(mContext, LoginActivity::class.java))
    }

    override fun onError(e: Throwable) {
        onHttpError(e)
        onHttpEnd()
    }

    override fun onComplete() {
        onHttpComplete()
        onHttpEnd()
    }

    /**
     * 正常访问完成后调用(除非回调onHttpError方法)
     */
    fun onHttpComplete() {}

    /**
     * 无论错误或者成功都回调
     */
    fun onHttpEnd() {}

    /**
     * 数据解析失败 或者status返回的是0
     *
     * @param t
     */
    fun onFail(t: BaseResponse<*>?) {
        if (t == null) {
            ToastUtils.showShort("数据异常")
        } else {
            val msg: String? = t.errorMsg
            if (!TextUtils.isEmpty(msg)) {
                ToastUtils.showShort(msg)
            } else {
                ToastUtils.showShort(t.errorCode)
            }
        }
    }

    /**
     * 访问 和解析数据成功 且返回status是1
     *
     * @param t baseBean中的info解析数据 可能为null
     */
    abstract fun onSuccess(t: T)

    /**
     * 访问错误
     *
     * @param t 错误信息
     */
    fun onHttpError(t: Throwable?) {
        var msg = "未知错误"
        if (t is UnknownHostException) {
            msg = "网络不可用"
        } else if (t is ConnectException) {
            msg = "服务器连接失败"
        } else if (t is SocketTimeoutException) {
            msg = "请求网络超时"
        } else if (t is HttpException) {
            msg = convertStatusCode(t)
        } else if (t is JsonParseException || t is ParseException || t is JSONException || t is JsonIOException) {
            msg = "数据解析错误"
        }
        if (msg.length != 0) ToastUtils.showShort(msg)
    }

    private fun convertStatusCode(httpException: HttpException): String {
        val msg: String
        msg = if (httpException.code() == 500) {
            "服务器发生错误"
        } else if (httpException.code() == 404) {
            "请求地址不存在"
        } else if (httpException.code() == 403) {
            "请求被服务器拒绝"
        } else if (httpException.code() == 307) {
            "请求被重定向到其他页面"
        } else {
            httpException.message()
        }
        return msg
    }

}

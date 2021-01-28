package com.zxf.kotlin.network

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class HttpObservable<T> {
    companion object {
        fun <T> getObservable(observable: Observable<T>): Observable<T>? {
            return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .debounce(2, TimeUnit.SECONDS)
                .unsubscribeOn(Schedulers.io())
        }
    }
}
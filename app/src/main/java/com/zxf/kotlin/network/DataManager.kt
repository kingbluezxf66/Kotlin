package com.zxf.kotlin.network

import com.zxf.kotlin.model.*
import io.reactivex.Observable

class DataManager(requestInterface: RequestInterface?) {
    private var requestInterface: RequestInterface? = null

    fun DataManager(requestInterface: RequestInterface?) {
        this.requestInterface = requestInterface
    }

    fun getRegistMessage(map: Map<String, String>): Observable<BaseResponse<RegistResponse>> {
        return requestInterface!!.goRegist(map)
    }

    fun goLoginMessage(map: Map<String, String>): Observable<BaseResponse<RegistResponse>> {
        return requestInterface!!.goLogin(map)
    }

    fun getBannerData(): Observable<BaseResponse<List<BannerBean>>> {
        return requestInterface!!.getBannerData()
    }

    fun getArticleList(page: Int): Observable<BaseResponse<HomeAticleBean>> {
        return requestInterface!!.getArticleList(page)
    }

    fun collectArticle(id: String): Observable<BaseResponse<*>> {
        return requestInterface!!.collectArticle(id)
    }

    fun getHotList(): Observable<BaseResponse<List<HotKey>>> {
        return requestInterface!!.getHotSearchList()
    }

    fun getSearchResult(
        page: Int,
        keyword: String?
    ): Observable<BaseResponse<HomeAticleBean>> {
        return requestInterface!!.getSearchResult(page, keyword)
    }

    fun getProjectTitle(): Observable<BaseResponse<List<ProJectTilteBean>>> {
        return requestInterface!!.getProtectList()
    }

    fun getProjectList(
        page: Int,
        cid: String?
    ): Observable<BaseResponse<HomeAticleBean>> {
        return requestInterface!!.getProjectList(page, cid)
    }

    fun getSystemList(): Observable<BaseResponse<List<SystemBean>>> {
        return requestInterface!!.getSystemList()
    }

    fun getSystemSecondList(
        page: Int,
        id: String?
    ): Observable<BaseResponse<HomeAticleBean>> {
        return requestInterface!!.getSystemSecondList(page, id)
    }
//
//    fun getNavigationList(): Observable<BaseResponse<List<NavigationBean?>?>?>? {
//        return requestInterface.getNavigationList()
//    }

    fun exitLogin(): Observable<BaseResponse<*>> {
        return requestInterface!!.exitLogin()
    }
}
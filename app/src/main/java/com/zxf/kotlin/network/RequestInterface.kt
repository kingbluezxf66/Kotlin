package com.zxf.kotlin.network

import com.zxf.kotlin.model.*
import io.reactivex.Observable
import retrofit2.http.*

interface RequestInterface {
    /**
     * 注册方法
     * http://www.wanandroid.com/user/register
     * 方法：POST
     * 参数
     * username,password,repassword
     */
    @FormUrlEncoded
    @POST("/user/register")
    fun goRegist(@FieldMap map: Map<String, String?>): Observable<BaseResponse<RegistResponse>>

    /**
     * 登录方法
     * http://www.wanandroid.com/user/login
     * 方法：POST
     * 参数：
     * username，password
     */
    @FormUrlEncoded
    @POST("/user/login")
    fun goLogin(@FieldMap map: Map<String, String>): Observable<BaseResponse<RegistResponse>>

    /**
     * 退出
     * http://www.wanandroid.com/user/logout/json
     * 方法：GET
     */
    @GET("user/logout/json")
    fun exitLogin(): Observable<BaseResponse<*>>

    /**
     * 首页轮播图
     * http://www.wanandroid.com/banner/json
     * 方法：GET
     * 参数：无
     */
    @GET("banner/json")
    fun getBannerData(): Observable<BaseResponse<List<BannerBean>>>

    /**
     * 首页文章列表
     * http://www.wanandroid.com/article/list/0/json
     * 方法：GET
     * 参数：页码，拼接在连接中，从0开始。
     * 注意：页码从0开始，拼接在链接上。
     *
     * @return
     */
    @GET("article/list/{page}/json")
    fun getArticleList(@Path("page") page: Int): Observable<BaseResponse<HomeAticleBean>>

    /**
     * 收藏站内文章
     * http://www.wanandroid.com/lg/collect/1165/json
     * 方法：POST
     * 参数： 文章id，拼接在链接中。
     */
    @POST("lg/collect/{id}/json")
    fun collectArticle(@Path("id") id: String?): Observable<BaseResponse<*>>

    /**
     * 搜索热词
     * 即目前搜索最多的关键词。
     * http://www.wanandroid.com//hotkey/json
     * 方法：GET
     * 参数：无
     */
    @GET("/hotkey/json")
    fun getHotSearchList(): Observable<BaseResponse<List<HotKey>>>

    /**
     * 搜索
     * http://www.wanandroid.com/article/query/0/json
     * 方法：POST
     * 参数：
     * 页码：拼接在链接上，从0开始。
     * k ： 搜索关键词
     * 注意：支持多个关键词，用空格隔开
     */
    @FormUrlEncoded
    @POST("article/query/{page}/json")
    fun getSearchResult(
        @Path("page") page: Int,
        @Field("k") keywords: String?
    ): Observable<BaseResponse<HomeAticleBean>>

    /**
     * 项目分类
     * http://www.wanandroid.com/project/tree/json
     * 方法： GET
     * 参数： 无
     */
    @GET("project/tree/json")
    fun getProtectList(): Observable<BaseResponse<List<ProJectTilteBean>>>

    /**
     * 项目列表数据
     * 某一个分类下项目列表数据，分页展示
     * http://www.wanandroid.com/project/list/1/json?cid=294
     * 方法：GET
     * 参数：
     * cid 分类的id，上面项目分类接口
     * 页码：拼接在链接中，从1开始。
     */
    @GET("project/list/{page}/json")
    fun getProjectList(
        @Path("page") page: Int,
        @Query("cid") id: String?
    ): Observable<BaseResponse<HomeAticleBean>>

    /**
     * 体系数据
     * http://www.wanandroid.com/tree/json
     * 方法：GET
     * 参数：无
     */
    @GET("tree/json")
    fun getSystemList(): Observable<BaseResponse<List<SystemBean>>>

    /**
     * 知识体系下的文章
     * 方法：GET
     * 参数：
     * cid 分类的id，上述二级目录的id
     * 页码：拼接在链接上，从0开始。
     * 例如查看类别：Android Studio下所有的文章：http://www.wanandroid.com/article/list/0/json?cid=60
     */
    @GET("article/list/{page}/json")
    fun getSystemSecondList(
        @Path("page") page: Int,
        @Query("cid") id: String?
    ): Observable<BaseResponse<HomeAticleBean>>

    /**
     * 导航数据
     * http://www.wanandroid.com/navi/json
     * 方法：GET
     * 参数：无
     * 可直接点击查看示例：http://www.wanandroid.com/navi/json
     */
//    @GET("navi/json")
//    fun getNavigationList(): Observable<BaseResponse<List<NavigationBean>>>

}
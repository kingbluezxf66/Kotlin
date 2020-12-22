package com.zxf.kotlin.help

import android.util.Log
import com.zxf.kotlin.model.SearchHistory
import com.zxf.kotlin.model.User
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults

/**
 * realm数据库help类
 *
 * kotlin 中？和！！
 * ？加在变量名后，系统在任何情况下不会报它的空指针异常
 * ！！加在变量名后，若对象为null，系统一定会报异常
 *
 * kotlin中?.和!!.
 * ?.加在变量后，表示这个参数可以为空，程序可以继续运行下去
 * !!.加在变量后，表示这个参数若为空，就抛出异常
 *
 * kotlin中::class 和::class.java
 * ::class 得到的是KClass
 * ::class.java 得到的是Class对象
 */
class DataBaseHelper {
    private lateinit var realm: Realm

    /**
     * 这里注意kotlin构造函数的使用
     */
    init {
        //默认配置 default.realm
        val config: RealmConfiguration = RealmConfiguration.Builder()
            .name("kotlin.realm")
            .schemaVersion(0)
            //.migration(new MigrationOne()) //当发现新旧版本号不一致时，会自动调用迁移类完成迁移操作
            .build()
        realm = Realm.getInstance(config)
        Log.i("kingblue", "DataBaseHelper: " + realm.getPath())
    }

//    //注入依赖
//    @Inject
//    fun DataBaseHelper() {
//        //默认配置 default.realm
//        val config: RealmConfiguration = RealmConfiguration.Builder()
//            .name("kotlin.realm")
//            .schemaVersion(0)
//            //.migration(new MigrationOne()) //当发现新旧版本号不一致时，会自动调用迁移类完成迁移操作
//            .build()
//        realm = Realm.getInstance(config)
//        Log.i("kingblue", "DataBaseHelper: " + realm.getPath())
//    }

    /**
     * 增
     * 保存搜索记录，若记录中已有，删除后，重新保存
     * ps:在UI线程中插入过多数据，可能会导致主线程阻塞
     */
    open fun saveKeyWords(keyWords: String) {

        val searchHistory: SearchHistory? =
            realm.where(SearchHistory::class.java).equalTo("keyword", keyWords).findFirst()
        // 方法一：注意：在UI和后台线程同时开启创建write的事务，可能会导致ANR错误。为了避免该问题，可以使用executeTransactionAsync来实现。
//        realm.beginTransaction()
//        searchHistory?.deleteFromRealm()
//        val history: SearchHistory? = realm.createObject(SearchHistory::class.java)
//        history?.keyword = keyWords
//        realm.commitTransaction()
        //方法二
        realm.executeTransaction(Realm.Transaction { realm ->
            searchHistory?.deleteFromRealm()
            val history: SearchHistory? = realm.createObject(SearchHistory::class.java)
            history?.keyword = keyWords
        })
    }

    /**
     * 删
     * 删除所有搜索记录
     */
    open fun deleteAllSearchHistory() {
        realm.beginTransaction()
        realm.where(SearchHistory::class.java).findAll().deleteAllFromRealm()
        realm.commitTransaction()
    }

    /**
     * 删
     * 删除某条搜索记录
     */
    open fun deleteSingleSearchHistory(keyWords: String) {
        realm.beginTransaction()
        realm.where(SearchHistory::class.java).equalTo("keyword", keyWords).findFirst()
            ?.deleteFromRealm()
        realm.commitTransaction()
    }

    /**
     * 改
     * 更新某条数据
     */
    open fun updateSearchHistory(keyWords: String, updateWords: String) {
        realm.executeTransaction(Realm.Transaction { realm ->
            val searchHistory: SearchHistory? =
                realm.where(SearchHistory::class.java).equalTo("keyword", keyWords).findFirst()
            searchHistory?.keyword = updateWords
        })
    }

    /**
     * 查
     * 查询搜索记录
     */
    open fun querySearchHistory(): List<SearchHistory> {
        val realmResult: RealmResults<SearchHistory> =
            realm.where(SearchHistory::class.java).findAll()
        return realm.copyFromRealm(realmResult)
    }
/*-------------------------------个人信息--------------------------------*/
    /**
     * 保存个人信息
     */
    open fun saveUserInfo(userName: String, password: String, isLogin: Boolean) {
        realm.executeTransaction(Realm.Transaction { realm ->
            val user: User? = realm.createObject(User::class.java)
            user?.password = password
            user?.userName = userName
            user?.isLogin = isLogin
        })
    }

    /**
     * 获取用户信息
     */
    open fun getUserInfo(): User? {
        return realm.where(User::class.java).equalTo("isLogin", true).findFirst()
    }

    /**
     * 删除用户信息
     */
    open fun deleteUserInfo() {
        realm.executeTransaction(Realm.Transaction { realm ->
            getUserInfo()?.deleteFromRealm()
        })
    }

    open fun closeRealm() {
        if (realm != null)
            realm.close()
    }
}
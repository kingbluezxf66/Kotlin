package com.zxf.kotlin

import android.app.ActivityManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.system.exitProcess

/**
 * activity管理
 * 使用object实现单例模式
 */
object AppActivityManager {
    private lateinit var activityStack: Stack<AppCompatActivity>

    /**
     * 添加activity
     * 经济基础决定法，法反作用经济基础
     */
    fun addActivity(activity: AppCompatActivity) {
        if (activityStack == null) {
            activityStack = Stack()
        }
        activityStack.add(activity)
    }

    /**
     * 获取当前activity(堆栈中最后一个入栈的)
     */
    fun currentActivity(): AppCompatActivity {
        if (activityStack == null) {
            activityStack = Stack()
        }
        return activityStack.lastElement()
    }


    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    fun finishActivity() {
        val activity: AppCompatActivity =
            activityStack.lastElement()
        finishActivity(activity)
    }

    /**
     * 结束指定的Activity
     */
    fun finishActivity(activity: AppCompatActivity?) {
        var activity = activity
        if (activity != null) {
            activityStack.remove(activity)
            activity.finish()
            activity = null
        }
    }

    /**
     * 结束指定类名的Activity
     * Kotlin 中的 == 等同于调用 equals() 函数，比较两个对象引用是否相等要用 === 操作符。
     */
    fun finishActivity(cls: Class<*>?) {
        for (activity in activityStack) {
            if (activity::class == cls) {
                finishActivity(activity)
            }
        }
    }

    /**
     * 结束所有Activity
     */
    fun finishAllActivity() {
        var i = 0
        val size: Int = activityStack.size
        while (i < size) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish()
            }
            i++
        }
        activityStack.clear()
    }
    /**
     * 退出应用程序
     */
    fun AppExit(context: Context){
        finishAllActivity()
       var activityManager:ActivityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.killBackgroundProcesses(context.packageName)
        exitProcess(0)
    }
}
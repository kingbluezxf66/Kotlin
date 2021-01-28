package com.zxf.kotlin.base

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.zxf.kotlin.AppActivityManager
import com.zxf.kotlin.R
import kotlinx.android.synthetic.main.erroeview.*

/**
 * activity基类
 */
abstract class BaseActivity : AppCompatActivity() {
    lateinit var mContext: Context
    var isTransAnim: Boolean = false
    lateinit var mWaitProgressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        //设置模式主题
        val spUtils = SPUtils.getInstance("theme")
        val currentTheme = spUtils.getInt("theme", 0)
        if (currentTheme == 0) {
            setTheme(R.style.day_theme)
        } else
            setTheme(R.style.night_theme)
        //初始化数据
        initData()
        //检查网络
        if (NetworkUtils.isConnected()) {
            setContentView(getLayout())
            initView()
        } else {
            setErrorLayout()
            ToastUtils.showShort("网络连接错误，请检查网络")
        }
        //设置竖屏
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        //将activity入栈
        AppActivityManager.addActivity(this)
    }

    open fun initData() {
        mContext = this
        isTransAnim = true
    }

    fun setErrorLayout() {
        setContentView(R.layout.erroeview)
        button.setOnClickListener {
            if (isConnectNet()) {
                setContentView(getLayout())
                initView()
            } else {
                setErrorLayout()
                ToastUtils.showShort("网络连接错误，请检查网络")
            }
        }
    }

    fun isConnectNet(): Boolean {
        return NetworkUtils.isConnected()
    }

    /**
     * 点击空白位置，隐藏软键盘
     */
    fun hideKeyboard(): Boolean {
        var mInputManager: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return mInputManager.hideSoftInputFromWindow(this.currentFocus.windowToken, 0)
    }

    /**
     * 设置title
     */
    open fun initTitleBar(toolbar: Toolbar, title: String, isLeftShow: Boolean) {
        toolbar.title = title
        setSupportActionBar(toolbar)
        if (isLeftShow) {
            //添加左上角图标
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            //显示左上角图标
            supportActionBar?.setDisplayShowHomeEnabled(true)
            toolbar.setNavigationIcon(R.mipmap.ic_back)
            toolbar.setNavigationOnClickListener(View.OnClickListener {
                finish()
            })
        }
    }

    /**
     * 页面跳转
     */
    fun startActivity(clz: Class<*>) {
        startActivity(Intent(this, clz))
        if (isTransAnim)
            overridePendingTransition(R.anim.activity_start_zoom_in, R.anim.activity_start_zoom_out)
    }

    /**
     * 携带数据页面跳转
     */
    fun startActivity(clz: Class<*>, bundle: Bundle?) {
        val intent: Intent = Intent()
        intent.setClass(this, clz)
        if (bundle != null) intent.putExtras(bundle)
        startActivity(intent)
        if (isTransAnim)
            overridePendingTransition(R.anim.activity_start_zoom_in, R.anim.activity_start_zoom_out)
    }

    fun startActivityForResult(clz: Class<*>, bundle: Bundle, requestCode: Int) {
        val intent: Intent = Intent()
        intent.setClass(this, clz)
        if (bundle != null) intent.putExtras(bundle)
        startActivityForResult(intent, requestCode)
        if (isTransAnim)
            overridePendingTransition(R.anim.activity_start_zoom_in, R.anim.activity_start_zoom_out)
    }

    /**
     * 显示提示框
     */
    open fun showProgressDialog(msg: String) {
        if (mWaitProgressDialog == null) {
            mWaitProgressDialog = ProgressDialog(this)
        }
        mWaitProgressDialog.setMessage(msg)
        mWaitProgressDialog.show()
    }

    /**
     * 隐藏提示框
     */
    open fun hideProgressDialog() {
        if (mWaitProgressDialog != null) {
            mWaitProgressDialog.dismiss()
        }
    }

    override fun finish() {
        if (isTransAnim) overridePendingTransition(
            R.anim.activity_finish_trans_in,
            R.anim.activity_finish_trans_out
        )
    }

    open fun setIsTransAnim(b: Boolean) {
        isTransAnim = b
    }

    //抽象方法
    protected abstract fun getLayout(): Int
    protected abstract fun initView()
}

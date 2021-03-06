package com.example.common.base

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.pm.ActivityInfo
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.common.R
import com.example.common.myview.CustomDialog
import com.example.common.view.IView
import com.jaeger.library.StatusBarUtil
import com.liqi.nohttputils.interfa.OnDialogGetListener
import me.jessyan.autosize.AutoSizeCompat

abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding> : AppCompatActivity(), IView,
    OnDialogGetListener {

    lateinit var viewModel: VM
    lateinit var dataBinding: DB

    private var mDialog: Dialog? = null
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;//竖屏
        hideTopUIMenu()
        if (initLayout() != 0) {
            dataBinding = DataBindingUtil.setContentView(this, initLayout())
            initView()
            initData()
        }
    }

    abstract fun initLayout(): Int
    abstract fun initView()
    abstract fun initData()

    /**
     * 沉浸式导航栏
     */
    open fun hideTopUIMenu() {
        //透明沉浸
        StatusBarUtil.setTranslucent(this)
    }

    /**
     * 隐藏虚拟按键，并且全屏
     */
    open fun hideBottomUIMenu() {
        //全屏 | View.SYSTEM_UI_FLAG_FULLSCREEN
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT < 19) {
            val v = this.window.decorView
            v.systemUiVisibility = View.GONE
        } else if (Build.VERSION.SDK_INT >= 19) {
            //可调起虚拟按键进行返回
//            View decorView = getWindow().getDecorView();
//            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
//            decorView.setSystemUiVisibility(uiOptions);

            //调起虚拟按键时会立刻隐藏
            val _window = window
            val params = _window.attributes
            params.systemUiVisibility =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE
            _window.attributes = params
        }
    }

    override fun toData(flag: String?, `object`: String?) {
    }

    override fun fail(isNetWork: Boolean, flag: String?, t: Throwable?) {
    }

    override fun getDialog(): Dialog {
        if (null == mDialog) {
            mDialog = CustomDialog(
                this,
                R.style.CustomDialog
            )
        }
        return mDialog as Dialog
    }

    /**
     * 在任何情况下本来适配正常的布局突然出现适配失效，适配异常等问题，只要重写 Activity 的 getResources() 方法
     */
    override fun getResources(): Resources {
        AutoSizeCompat.autoConvertDensityOfGlobal((super.getResources()))
        return super.getResources()
    }

}
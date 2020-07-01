package com.example.smartagriculture.viewmodel

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.example.common.*
import com.example.common.base.BaseViewModel
import com.example.common.data.BaseField
import com.example.common.data.BaseUrl
import com.example.common.data.CommitParam
import com.example.common.model.NoHttpRx
import com.example.smartagriculture.R
import com.example.common.data.Identification.Companion.DEFAULT
import com.example.common.data.Identification.Companion.SCREEN
import com.example.common.data.Identification.Companion.STOCK
import com.example.smartagriculture.util.nav
import kotlinx.android.synthetic.main.park_dialog.view.*


class MainViewModel(application: Application) : BaseViewModel(application) {
    var position: Int = 1
    lateinit var rootView: View
    var flag: Int = DEFAULT

    fun showDialog(activity: Activity, flag: Int) {

        rootView =
            LayoutInflater.from(activity).inflate(R.layout.park_dialog, null)
        when (flag) {
            STOCK -> {
                rootView.radioButton.isChecked = true
                rootView.radioButton.text = activity.getString(R.string.stock_manage)
                rootView.radioButton2.text = activity.getString(R.string.product_manage)
                rootView.radioButton3.visibility = View.GONE
            }
            else -> {
                if (position == 1) {
                    rootView.radioButton.isChecked = true
                }
                if (position == 2) {
                    rootView.radioButton2.isChecked = true
                }
                if (position == 3) {
                    rootView.radioButton3.isChecked = true
                }
            }
        }

        rootView.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            LogUtil("走了吗", "setOnCheckedChangeListener")
            when (checkedId) {
                R.id.radioButton -> {
                    LogUtil("走了吗", "zoulema")
                    position = 1
                }
                R.id.radioButton2 -> {
                    position = 2
                }
                R.id.radioButton3 -> {
                    position = 3
                }
                else -> {
                }
            }
        }
        rootView.cancel_button.setOnClickListener {
            dismissDialog()
        }
        rootView.sure_button.setOnClickListener {
            dismissDialog()
            sureBtnClick()
            when (flag) {
                SCREEN -> {
                    query = position.toString()
                    getParks(parks, standId, query, noHttpRx)
                }
                STOCK -> {
                    if (rootView.radioGroup.checkedRadioButtonId == R.id.radioButton) {
                        nav(
                            activity,
                            R.id.stock_constraint
                        ).navigate(R.id.action_mainFragment_to_stockFragment)
                    } else {
                        nav(
                            activity,
                            R.id.stock_constraint
                        ).navigate(R.id.action_mainFragment_to_productFragment)
                    }
                }
                else -> {
                }
            }
        }
        dialogCircle =
            getPop(activity, rootView, 1, 3, Gravity.BOTTOM, R.style.BottomDialog_Animation, false)
    }


    fun sureBtnClick(): Unit {
        when (flag) {
            STOCK -> {
                if (rootView.radioGroup.checkedRadioButtonId == R.id.radioButton) {
                    nav(
                        rootView
                    ).navigate(R.id.action_mainFragment_to_stockFragment)
                } else {
                    nav(
                        rootView
                    ).navigate(R.id.action_mainFragment_to_productFragment)
                }
            }
            SCREEN -> {

            }
            else -> {
            }
        }
    }

    fun dismissDialog(): Unit {
        if (dialogCircle.isShowing) {
            dialogCircle.dismiss()
        }

    }

    fun toWarningMessage(view: View, parkId: String) {
        val bundle = Bundle()
        bundle.putString("parkId", parkId)
        nav(view).navigate(R.id.action_mainFragment_to_warningMessageFragment, bundle)
    }

    fun toMonitor(view: View, parkId: String) {
        ARouter.getInstance().build(BaseField.MONITOR_PATH).navigation()
    }

    fun toNotice(view: View) {
        nav(view).navigate(R.id.action_mainFragment_to_noticeFragment)
    }

    fun toSearch(view: View) {
        nav(view).navigate(R.id.action_mainFragment_to_searchFragment)
    }

    fun toWeather(view: View) {
        nav(view).navigate(R.id.action_mainFragment_to_weatherFragment)
    }

    fun getNotice(noHttpRx: NoHttpRx) {
        noHttpRx.getHttp("token", "系统通知", BaseUrl.NOTICE_NUM, onDialogGetListener)
    }

    fun getParkType(noHttpRx: NoHttpRx) {
        var commitParam = CommitParam()
        commitParam.companyId = "1"
        var map = hashMapOf<String, String>()
        noHttpRx.postHttpJson(
            map,
            "园区类型",
            BaseUrl.BASE_URL + BaseUrl.PARK_TYPE_URL,
            commitParam.toJson(commitParam),
            onDialogGetListener
        )
    }
}
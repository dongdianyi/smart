package com.example.smartagriculture.fragment

import android.view.View
import com.androidkun.xtablayout.XTabLayout
import com.example.common.BaseFragment
import com.example.common.hideSoftKeyboard
import com.example.smartagriculture.R
import com.example.smartagriculture.adapter.LabVpAdapter
import com.example.smartagriculture.databinding.FragmentMailListBinding
import com.example.smartagriculture.util.Identification
import com.example.smartagriculture.util.nav
import com.example.smartagriculture.viewmodel.ChatViewModel
import kotlinx.android.synthetic.main.fragment_mail_list.*

class MailListFragment : BaseFragment<ChatViewModel,FragmentMailListBinding>() {


    override fun initLayout(): Int {
        return R.layout.fragment_mail_list
    }

    override fun initView(view: View) {
        mTitles = listOf<String>("个人", "群聊")
        val mLabVpAdapter = LabVpAdapter(childFragmentManager, mTitles, Identification.CHAT)
        view_pager.adapter = mLabVpAdapter
        task_tab.setupWithViewPager(view_pager)
        for (index in mTitles.indices) {
            task_tab.getTabAt(index)?.customView = getTabViewChat(index)
        }
    }

    override fun initData() {
    }
    override fun setListener() {
        task_tab.run {
            setOnTabSelectedListener(object : XTabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: XTabLayout.Tab) {
                    if (0==tab.position) {
                        search_tv.setHint(R.string.search_maillist)
                    }else{
                        search_tv.setHint(R.string.search_chat)
                    }
                    changeTabSelect(tab,view_pager)
                }

                override fun onTabUnselected(tab: XTabLayout.Tab) {
                    changeTabNormal(tab)
                }

                override fun onTabReselected(tab: XTabLayout.Tab) {}
            })
            back.setOnClickListener {
                hideSoftKeyboard(requireActivity())
                nav().navigateUp()

            }
        }
    }

}
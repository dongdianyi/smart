package com.example.smartagriculture.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.common.AppUtils
import com.example.common.base.BaseApplication
import com.example.common.base.BaseFragment
import com.example.common.clickNoRepeat
import com.example.smartagriculture.R
import com.example.smartagriculture.databinding.FragmentSettingBinding
import com.example.smartagriculture.util.nav
import com.example.smartagriculture.viewmodel.MineViewModel
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.title_item.*

/**
 * A simple [Fragment] subclass.
 */
class SettingFragment : BaseFragment<MineViewModel, FragmentSettingBinding>() {

    override fun initLayout(): Int {
        return R.layout.fragment_setting
    }

    override fun initView(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity()).get(MineViewModel::class.java)
        dataBinding.data = viewModel
        dataBinding.lifecycleOwner = this
    }

    @SuppressLint("SetTextI18n")
    override fun lazyLoadData() {
        textView123.text =
            "版本：" + AppUtils.getAppName(BaseApplication.context) + "  " + AppUtils.getVersionName(
                BaseApplication.context
            )
    }

    override fun setListener() {
        textView125.clickNoRepeat {
            viewModel.showDialogBase(
                requireActivity(),
                getString(R.string.clear_cache),
                getString(R.string.sure_clear_cache),
                R.layout.dialog_layout
            )
        }
        textView128.clickNoRepeat {
            viewModel.showDialogBase(
                requireActivity(),
                getString(R.string.exit),
                getString(R.string.sure_exit),
                R.layout.dialog_layout
            )
        }
        back.clickNoRepeat {
            nav().navigateUp()
        }
    }

}

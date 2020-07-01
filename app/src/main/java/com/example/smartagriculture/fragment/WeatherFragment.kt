package com.example.smartagriculture.fragment

import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.base.BaseFragment
import com.example.smartagriculture.R
import com.example.smartagriculture.adapter.WeatherAdapter
import com.example.smartagriculture.bean.MyWeatherData
import com.example.smartagriculture.databinding.FragmentWeatherBinding
import com.example.smartagriculture.util.nav
import com.example.smartagriculture.viewmodel.DataViewModel
import kotlinx.android.synthetic.main.fragment_weather.*
import kotlinx.android.synthetic.main.title_item.*

/**
 * A simple [Fragment] subclass.
 */
class WeatherFragment : BaseFragment<DataViewModel, FragmentWeatherBinding>() {


    var height:Int=0
    var width:Int=0
    override fun initLayout(): Int {
        return R.layout.fragment_weather
    }

    override fun initView(view: View) {
        textView.text=getString(R.string.weather_description)
    }

    override fun initData() {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        weather_recycler.layoutManager = linearLayoutManager
        var datas = mutableListOf<MyWeatherData>()
        datas.add(MyWeatherData(0, -8, "昨天"))
        datas.add(MyWeatherData(3, -6, "今天"))
        datas.add(MyWeatherData(4, -6, "星期一"))
        weather_recycler.post {
            height=weather_recycler.height
            width=weather_recycler.width
            var weatherAdapter = WeatherAdapter(requireContext(),R.layout.beta_weather,datas,height/2-100,width/3,8,-8)
            weather_recycler.adapter =weatherAdapter
        }

    }

    override fun setListener() {
        back.setOnClickListener {
            nav().navigateUp()
        }
    }

}

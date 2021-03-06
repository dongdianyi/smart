package com.example.smartagriculture.adapter

import android.content.Context
import android.view.View
import android.widget.TextView
import com.amap.api.mapcore.util.it
import com.example.common.clickNoRepeat
import com.example.smartagriculture.R
import com.example.common.data.Identification

/**
 * Created by ddy
 */
class AttendanceAdapter(context: Context?, layoutId: Int, id: Int) :
    ListBaseAdapter<Any?>(context) {

    private var onAttendanceListener: ((view: View, position: Int,flag:Int) -> Unit)? = null

    fun setOnADListener(onAttendanceListener: ((view: View, position: Int,flag:Int) -> Unit)) {
        this.onAttendanceListener = onAttendanceListener

    }
    private var layoutId = 0
    private var id = 0

    init {
        this.layoutId = layoutId
        this.id = id
    }

    override fun getLayoutId(): Int {
        return layoutId
    }

    override fun onBindItemHolder(holder: SuperViewHolder, position: Int) {

        when (id) {
            Identification.ATTENDANCE_PEASANT_CLOCK -> {
                val textView74 = holder.getView<TextView>(R.id.textView74)
                textView74.text = mDataList[position].toString()
            }
            Identification.ATTENDANCE_PEASANT_LEAVE -> {
                val textView84 = holder.getView<TextView>(R.id.textView84)
                textView84.text = mDataList[position].toString()
            }
            Identification.ATTENDANCE_MANAGER ->{
                val textView88 = holder.getView<TextView>(R.id.textView88)
                val textView65 = holder.getView<TextView>(R.id.textView65)
                textView88.text = mDataList[position].toString()
                textView65.clickNoRepeat {
                    onAttendanceListener?.invoke(
                        it, position,
                        Identification.ATTENDANCE_MANAGER_SELECT)
                }
            }
            Identification.ATTENDANCE_MANAGER_APPROVAL ->{}
            Identification.ATTENDANCE_MANAGER_APPROVED ->{}
            Identification.ATTENDANCE_MANAGER_SELECT ->{
                val name = holder.getView<TextView>(R.id.name)
                name.text = mDataList[position].toString()
            }
            else -> {

            }
        }


    }

}
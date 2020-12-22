package com.example.yanghuiwen.habittodoist.view.week_viewpager

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.yanghuiwen.habittodoist.R
import com.example.yanghuiwen.habittodoist.R.color.colorAccent
import java.time.LocalDateTime
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class WeekPageView (context: Context, startDate :LocalDateTime) : RelativeLayout(context){

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.week_viewpage, null)
        val weeks =intArrayOf(R.id.week7,R.id.week1,R.id.week2,R.id.week3,R.id.week4,R.id.week5,R.id.week6)
        var startWeek = startDate.dayOfWeek.getValue()
        val weekSeven = startDate.minusDays(startWeek.toLong())


        for(i in 0..6){
            var weekDay = weekSeven.plusDays(i.toLong())
            val textView = view.findViewById(weeks[i]) as TextView
            textView.text = ( weekDay.dayOfMonth).toString()
            if(startWeek == i){
                textView.setBackgroundColor(Color.parseColor("#03DAC5"))
            }else if(startWeek == 7 && i== 0){
                textView.setBackgroundColor(Color.parseColor("#03DAC5"))
            }
        }

        addView(view)
    }

     fun refreshView() {

    }


}
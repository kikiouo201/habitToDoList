package com.example.yanghuiwen.habittodoist.view.week_viewpager

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.yanghuiwen.habittodoist.R
import com.example.yanghuiwen.habittodoist.R.color.colorAccent

class WeekPageView (context: Context,startDate :Int,startWeek :Int) : RelativeLayout(context){

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.week_viewpage, null)
        val weeks =intArrayOf(R.id.week7,R.id.week1,R.id.week2,R.id.week3,R.id.week4,R.id.week5,R.id.week6)

        val weekSeven = startDate-1-startWeek

        for(i in 0..6){
            val textView = view.findViewById(weeks[i]) as TextView
            textView.text = (weekSeven+i).toString()
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

    // 完成 滾輪
}
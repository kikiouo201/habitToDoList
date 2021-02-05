package com.example.yanghuiwen.habittodoist.view.week_viewpager

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.yanghuiwen.habittodoist.AllItemData
import com.example.yanghuiwen.habittodoist.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


// 當使用者按下 其他日期，WeekPageView要全部跳到那一個星期

@RequiresApi(Build.VERSION_CODES.O)
class WeekPageView(context: Context, startDate: LocalDateTime,onDateChange: (currentDate:String) -> Unit) : RelativeLayout(context){
    val view = LayoutInflater.from(context).inflate(R.layout.week_viewpage, null)
    val weeks =intArrayOf(R.id.week7,R.id.week1,R.id.week2,R.id.week3,R.id.week4,R.id.week5,R.id.week6)
    var startWeek = startDate.dayOfWeek.getValue()
    val weekSeven = startDate.minusDays(startWeek.toLong())
    var currentDateIndex = startWeek
    var currentOnDateChange = onDateChange

    init {

        for(i in 0..6){
            var weekDay = weekSeven.plusDays(i.toLong())
            val textView = view.findViewById(weeks[i]) as TextView
            textView.text = ( weekDay.dayOfMonth).toString()

            textView.setOnClickListener {
                val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val current = weekDay.format(formatter)
                Log.i("kiki","current"+current)
                onDateChange(current)
                val currentTextView = view.findViewById(weeks[currentDateIndex]) as TextView
                currentTextView.setBackgroundColor(Color.TRANSPARENT)
                textView.setTypeface(Typeface.DEFAULT_BOLD)
                textView.setBackgroundResource(R.drawable.week_radius)
                textView.setTextColor(Color.parseColor("#004B97"))

                AllItemData.currentWeekIndex = i
                currentDateIndex = i
            }
            if(startWeek == i){
                val current = "${weekDay.month.value}/${weekDay.dayOfMonth}"
                onDateChange(current)
                textView.setTypeface(Typeface.DEFAULT_BOLD)
                textView.setBackgroundResource(R.drawable.week_radius)
                textView.setTextColor(Color.parseColor("#004B97"))

            }else if(startWeek == 7 && i== 0){
                val current = "${weekDay.month.value}/${weekDay.dayOfMonth}"
                onDateChange(current)
                textView.setTypeface(Typeface.DEFAULT_BOLD)
                textView.setBackgroundResource(R.drawable.week_radius)
                textView.setTextColor(Color.parseColor("#004B97"))
                //textView.setBackgroundColor(Color.parseColor("#84C1FF"))
            }
        }
        fun choose(){
            for(i in 0..6){
                val textView = view.findViewById(weeks[i]) as TextView
                Log.i("WeekPageView","currentTextColor"+textView.currentTextColor)
                textView.setTextColor(Color.parseColor("#84C1FF"))
                textView.setTypeface(Typeface.SANS_SERIF);
                textView.setBackgroundColor(Color.TRANSPARENT)

            }
            val currentTextView = view.findViewById(weeks[AllItemData.currentWeekIndex]) as TextView
            currentTextView.setTypeface(Typeface.DEFAULT_BOLD);
            currentTextView.setBackgroundResource(R.drawable.week_radius)
           // currentTextView.setBackgroundColor(Color.parseColor("#004B97"))
            currentDateIndex =  AllItemData.currentWeekIndex
            onDateChange("current")
        }

        addView(view)
    }

    fun chooseThisPage(){
        for(i in 0..6){
            val textView = view.findViewById(weeks[i]) as TextView
            val current_color: Int = textView.getCurrentTextColor()
            val strColor = String.format("#%06X", 0xFFFFFF and current_color)
            Log.e("debug", "print $strColor")
            Log.i("WeekPageView","currentTextColor"+strColor)
           // textView.setTextColor(Color.parseColor("#000000"))
            textView.setTypeface(Typeface.SANS_SERIF);
            textView.setBackgroundColor(Color.TRANSPARENT)

        }
        Log.i("WeekPageView","currentWeekIndex"+AllItemData.currentWeekIndex)
        val currentTextView = view.findViewById(weeks[AllItemData.currentWeekIndex]) as TextView
        currentTextView.setTypeface(Typeface.DEFAULT_BOLD);
        currentTextView.setBackgroundResource(R.drawable.week_radius)
       // currentTextView.setBackgroundColor(Color.parseColor("#004B97"))
        currentDateIndex =  AllItemData.currentWeekIndex
        Log.i("kiki","currentTextView.text.toString()"+currentTextView.text.toString())
        val weekDay = weekSeven.plusDays(AllItemData.currentWeekIndex.toLong())
        val current = "${weekDay.month.value}/${weekDay.dayOfMonth}"
        currentOnDateChange(current)
    }
     fun refreshView() {

    }







}
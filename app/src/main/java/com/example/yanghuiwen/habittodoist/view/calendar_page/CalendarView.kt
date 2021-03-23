package com.example.yanghuiwen.habittodoist.view.calendar_page


import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import com.example.yanghuiwen.habittodoist.R
import kotlinx.android.synthetic.main.calendar.view.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


// 當使用者按下 其他日期，WeekPageView要全部跳到那一個星期

@RequiresApi(Build.VERSION_CODES.O)
class CalendarView(context: Context, month: Int) : RelativeLayout(context){
    val view = LayoutInflater.from(context).inflate(R.layout.calendar, null)
    val weekOfMonth =intArrayOf(R.id.firstWeek,R.id.secondWeek,R.id.thirdWeek,R.id.fourthWeek,R.id.fifthWeek)
//    var startWeek = startDate.dayOfWeek.getValue()
//    val weekSeven = startDate.minusDays(startWeek.toLong())
//    var currentDateIndex = startWeek
//    var currentOnDateChange = onDateChange

    init {

        view.month.text ="${month}月"
        val cal = Calendar.getInstance()

        cal.set(Calendar.MONTH, month-1)
        cal.set(Calendar.DAY_OF_MONTH, 1) //這個月第一天
        val firstDateWeek =cal.get(Calendar.DAY_OF_WEEK)-1
        Log.i("CalendarView","${month}月")
        Log.i("CalendarView","cal.time${ cal.time}")
        if(firstDateWeek != 7){
            cal.add(Calendar.DATE, - firstDateWeek) // 這個月 第一週的禮拜日
            Log.i("CalendarView","第一週的禮拜日cal.time${ cal.time}")
        }
        var currentWeekOfMonth = cal.time
        for (i in 0..4){

            val week = view.findViewById<GeneralCalendar>(weekOfMonth[i])
            week.setMondayDate(currentWeekOfMonth.month+1,currentWeekOfMonth.date)
            cal.add(Calendar.DATE, +7)
            currentWeekOfMonth = cal.time
//            Log.i("CalendarView","cal.time${cal.time}")
//            Log.i("CalendarView","Month${cal.time.month+1}")
//            Log.i("CalendarView","currentWeekOfMonth${currentWeekOfMonth.month}")

        }


       // firstWeek.setMondayDate(Calendar.MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH))
       // val firstDateOfMonth = cal.set(Calendar.MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH))

        fun choose(){

        }


        addView(view)
    }

    fun chooseThisPage(){
//        for(i in 0..6){
//            val textView = view.findViewById(weeks[i]) as TextView
//            val current_color: Int = textView.getCurrentTextColor()
//            val strColor = String.format("#%06X", 0xFFFFFF and current_color)
//            Log.e("debug", "print $strColor")
//            Log.i("WeekPageView","currentTextColor"+strColor)
//            // textView.setTextColor(Color.parseColor("#000000"))
//            textView.setTypeface(Typeface.SANS_SERIF);
//            textView.setBackgroundColor(Color.TRANSPARENT)
//
//        }
//        Log.i("WeekPageView","currentWeekIndex"+AllItemData.currentWeekIndex)
//        val currentTextView = view.findViewById(weeks[AllItemData.currentWeekIndex]) as TextView
//        currentTextView.setTypeface(Typeface.DEFAULT_BOLD);
//        currentTextView.setBackgroundResource(R.drawable.week_radius)
//        // currentTextView.setBackgroundColor(Color.parseColor("#004B97"))
//        currentDateIndex =  AllItemData.currentWeekIndex
//        Log.i("kiki","currentTextView.text.toString()"+currentTextView.text.toString())
//        val weekDay = weekSeven.plusDays(AllItemData.currentWeekIndex.toLong())
//        val current = "${weekDay.month.value}/${weekDay.dayOfMonth}"
//        currentOnDateChange(current)
    }
    fun refreshView() {

    }







}
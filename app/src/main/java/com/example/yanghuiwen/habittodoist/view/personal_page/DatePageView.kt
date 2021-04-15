package com.example.yanghuiwen.habittodoist.view.week_viewpager

import android.content.Context
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.*
import androidx.annotation.RequiresApi
import com.example.yanghuiwen.habittodoist.AllItemData
import com.example.yanghuiwen.habittodoist.R
import com.example.yanghuiwen.habittodoist.view.tools.TimeFormat
import java.time.LocalDateTime


// 當使用者按下 其他日期，WeekPageView要全部跳到那一個星期

@RequiresApi(Build.VERSION_CODES.O)
class DatePageView(context: Context, startDate: LocalDateTime,dateTypes:String) : RelativeLayout(context){
    val view = LayoutInflater.from(context).inflate(R.layout.date_viewpage, null)

    var nowDate = startDate


    init {

        val date = view.findViewById<TextView>(R.id.date)

        val editText = view.findViewById<EditText>(R.id.editText)

        chooseThisDate(dateTypes,date,editText,nowDate)
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                AllItemData.setDiary(date.text as String,editText.text.toString())
            }
        })
        val previous = view.findViewById<ImageView>(R.id.previous)
        previous.setOnClickListener {

            nowDate = minusDays(dateTypes,date,editText,nowDate)
        }
        val next = view.findViewById<ImageView>(R.id.next)
        next.setOnClickListener {

            nowDate =  plusDay(dateTypes,date,editText,nowDate)
        }


        addView(view)
    }
    fun chooseThisDate(dateTypes:String, date:TextView, editText:EditText, nowDate: LocalDateTime){

        when(dateTypes){
            "year" -> {
                date.text = nowDate.year.toString()
                val diary=AllItemData.getDiary(date.text.toString())
                editText.setText(diary)
            }
            "month" -> {
                date.text = TimeFormat().formatYearMonth(nowDate)
                val diary=AllItemData.getDiary(date.text.toString())
                editText.setText(diary)
            }
//            "week" -> {
//                var weekNum =nowDate.dayOfYear/7+1
//                if (nowDate.dayOfYear%7>0){
//                    weekNum++
//                }
//                date.text = "第${weekNum}週"
//                editText.setText((nowDate.dayOfYear%7).toString())
//            }
//            "diary" -> {
//                date.text =TimeFormat().formatYearMonthWeek(nowDate)
//                editText.setText(TimeFormat().formatYearMonthWeek(nowDate))
//            }
        }
    }
    fun plusDay(dateTypes:String, date:TextView, editText:EditText,  startDate: LocalDateTime):LocalDateTime{
        var nowDate = startDate
        when(dateTypes){
            "year" -> {
                nowDate = nowDate.plusYears(1)
                date.text = nowDate.year.toString()
                val diary=AllItemData.getDiary(date.text.toString())
                editText.setText(diary)
            }
            "month" -> {
                nowDate = nowDate.plusMonths(1)
                date.text = TimeFormat().formatYearMonth(nowDate)
                val diary=AllItemData.getDiary(date.text.toString())
                editText.setText(diary)
            }
//            "week" -> {
//                nowDate = nowDate.plusWeeks(1)
//                var weekNum =nowDate.dayOfYear/7+1
//                if (nowDate.dayOfYear%7>0){
//                    weekNum++
//                }
//                date.text = "第${weekNum}週"
//                editText.setText((nowDate.dayOfYear%7).toString())
//            }
//            "diary" -> {
//                nowDate = nowDate.plusDays(1)
//                date.text =TimeFormat().formatYearMonthWeek(nowDate)
//                editText.setText(TimeFormat().formatYearMonthWeek(nowDate))
//            }
        }

        return nowDate
    }

    fun minusDays(dateTypes:String, date:TextView, editText:EditText,  startDate: LocalDateTime):LocalDateTime{
        var nowDate = startDate
        when(dateTypes){
            "year" -> {
                nowDate = nowDate.minusYears(1)
                date.text = nowDate.year.toString()
                val diary=AllItemData.getDiary(date.text.toString())
                editText.setText(diary)
            }
            "month" -> {
                nowDate = nowDate.minusMonths(1)
                date.text = TimeFormat().formatYearMonth(nowDate)
                val diary=AllItemData.getDiary(date.text.toString())
                editText.setText(diary)
            }
//            "week" -> {
//                nowDate = nowDate.minusWeeks(1)
//                var weekNum =nowDate.dayOfYear/7+1
//                if (nowDate.dayOfYear%7>0){
//                    weekNum++
//                }
//                date.text =  "第${weekNum}週"
//                editText.setText((nowDate.dayOfYear%7).toString())
//            }
//            "diary" -> {
//                nowDate = nowDate.plusDays(1)
//                date.text =TimeFormat().formatYearMonthWeek(nowDate)
//                editText.setText(TimeFormat().formatYearMonthWeek(nowDate))
//            }
        }
        return nowDate
    }

    fun refreshView() {

    }







}
package com.example.yanghuiwen.habittodoist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager

import java.util.ArrayList

object AllItemData {
    val todayToDo = ArrayList<ItemDate>()
    val habitToDo = ArrayList<ItemDate>()
    val scheduleToDo = ArrayList<ItemDate>()
    var currentDate ="12/26"
    var currentWeekIndex = 1
//    fun setHabitToDo(str: String){
//        habitToDo.add(str)
//
//    }

    fun getDateToDayToDo():ArrayList<ItemDate>{
        var DateTodayToDo = ArrayList<ItemDate>()
        for(i in 0..todayToDo.size-1){
            if(currentDate.equals(todayToDo[i].startDate)){
                DateTodayToDo.add(todayToDo[i])
            }
        }
        return DateTodayToDo
    }

    fun getDateHabitToDo():ArrayList<ItemDate>{
        var DateHabitToDo = ArrayList<ItemDate>()
        for(i in 0..habitToDo.size-1){
            if(currentDate.equals(habitToDo[i].startDate)){
                DateHabitToDo.add(habitToDo[i])
            }
        }
        return DateHabitToDo
    }


    fun getDateScheduleToDo():ArrayList<ItemDate>{
        var DateScheduleToDo = ArrayList<ItemDate>()
        for(i in 0..scheduleToDo.size-1){
            if(currentDate.equals(scheduleToDo[i].startDate)){
                DateScheduleToDo.add(scheduleToDo[i])
            }
        }
        return DateScheduleToDo
    }


}
package com.example.yanghuiwen.habittodoist.view.tools

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.YearMonth

class TimeFormat{
    @RequiresApi(Build.VERSION_CODES.O)
    fun formatYearMonthWeek(currentTime: LocalDateTime):String{
        var chooseTime ="${currentTime.year}"
        if(currentTime.month.value+1<10){
            chooseTime +="-0${currentTime.month.value+1}"
        }else{
            chooseTime +="-${currentTime.month.value+1}"
        }
        if (currentTime.dayOfMonth<10){
            chooseTime +="-0${currentTime.dayOfMonth}"
        }else{
            chooseTime +="-${currentTime.dayOfMonth}"
        }
        return chooseTime
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatYearMonth(currentTime: LocalDateTime):String{
        var chooseTime ="${currentTime.year}"
        if(currentTime.month.value<10){
            chooseTime +="-0${currentTime.month.value}"
        }else{
            chooseTime +="-${currentTime.month.value}"
        }
        return chooseTime
    }


    fun formatYearMonth(currentTime:String):String{
        val currentTimes =currentTime.split("-")
        val chooseTime ="${currentTimes[0]}-${currentTimes[1]}"
        return chooseTime
    }

    fun formatYear(currentTime:String):String{
        val currentTimes =currentTime.split("-")
        val chooseTime ="${currentTimes[0]}"
        return chooseTime
    }

}
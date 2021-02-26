package com.example.yanghuiwen.habittodoist

import com.google.firebase.database.IgnoreExtraProperties
import java.util.ArrayList

@IgnoreExtraProperties
data class HabitDate(



        var name:String = "",
        var important:Int = 0,
        var urgent:Int = 0,
        var project:String = "無",
        var repeat:Boolean = false,
        var timeType:String = "日",
        var repeatCycle :ArrayList<String> = arrayListOf(),
        var allDate:ArrayList<String> = arrayListOf(),
        var notEndItem:ArrayList<String> = arrayListOf(),
        var endItem:ArrayList<String> = arrayListOf(),
        var startDate:String = "",
        var endDate:String = "",
        var startTime:String = "",
        var endTime:String = "",
        var IsEndItem:Boolean = false

)

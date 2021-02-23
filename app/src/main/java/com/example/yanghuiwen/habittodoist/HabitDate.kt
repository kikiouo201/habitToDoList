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
        var allDate:ArrayList<String> = arrayListOf("2020/02/22", "2020/02/23", "2020/02/24"),
        var notEndItem:ArrayList<String> = arrayListOf("2", "3", "4"),
        var endItem:ArrayList<String> = arrayListOf(),
        var startDate:String = "",
        var endDate:String = "",
        var startTime:String = "",
        var endTime:String = "",
        var IsEndItem:Boolean = false

)

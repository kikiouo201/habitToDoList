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
        var progressRate :String ="無",
        var progressRateFrequency:ArrayList<String> = arrayListOf(),
        var allDate:ArrayList<String> = arrayListOf(),
        var notEndItemList:ArrayList<String> = arrayListOf(),
        var endItemList:ArrayList<String> = arrayListOf(),
        var IsProhibitItemList:ArrayList<String> = arrayListOf(),//過期 被禁止
        var startDate:String = "",
        var endDate:String = "",
        var startTime:String = "",
        var endTime:String = "",
        var IsEndItem:Boolean = false,//完成項目
        var IsProhibitItem:Boolean = false//過期 被禁止

)

package com.example.yanghuiwen.habittodoist

import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
data class ItemDate(


    //時間 無
    //完成 事項和計畫的格式
        var name:String = "",
        var important:Int = 0,
        var urgent:Int = 0,
        var week:Int = 0,
        var project:String = "無",
        var repeat:Boolean = false,
        var timeType:Int = 1,
        var startDate:String = "",
        var endDate:String = "",
        var startTime:String = "",
        var endTime:String = "",
        var isHabit:Boolean = false,
        var IsEndItem:Boolean = false


)

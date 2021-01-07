package com.example.yanghuiwen.habittodoist



import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

import java.time.LocalDateTime
import java.util.*

@IgnoreExtraProperties
data class ItemDate(


    //時間 無
    //完成 事項和計畫的格式
        var name:String = "",
        var important:Int = 0,
        var urgent:Int = 0,
        var week:Int = 0,
        var project:String = "",
        var repeat:Boolean = false,
        var timeType:Int = 1,
        var startDate:String = "",
        var endDate:String = "",
        var startTime:String = "",
        var endTime:String = "",
        var IsEndItem:Boolean = false
    //@RequiresApi(Build.VERSION_CODES.O)
   // var Date = LocalDateTime.now()


)
//{
//
//    @Exclude
//    fun toMap(): Map<String, Any?> {
//        return mapOf(
//                "name" to name,
//                "important" to important,
//                "urgent" to urgent,
//                "week" to week,
//               "project" to project,
//                "repeat" to  repeat,
//                "startDate" to startDate,
//                "startTime" to startTime,
//                "endDate" to endDate,
//                "endTime" to endTime,
//                "IsEndItem" to IsEndItem
//        )
//    }
//}
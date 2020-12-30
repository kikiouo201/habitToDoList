package com.example.yanghuiwen.habittodoist



import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.util.*

class ItemDate {


    //時間 無
    //完成 事項和計畫的格式
    var name = ""
    var important = 0
    var urgent = 0
    var week = 0
    var project = ""
    var repeat = false
    var startDate = ""
    var endDate = ""
    var startTime = ""
    var endTime = ""
    var IsEndItem = false
    @RequiresApi(Build.VERSION_CODES.O)
    var Date = LocalDateTime.now()


}
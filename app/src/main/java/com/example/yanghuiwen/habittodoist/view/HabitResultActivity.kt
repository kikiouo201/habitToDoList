package com.example.yanghuiwen.habittodoist.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.yanghuiwen.habittodoist.AllItemData
import com.example.yanghuiwen.habittodoist.R

class HabitResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit_result)

        val habitIndex = intent.getBundleExtra("bundle")?.getInt("habitIndex")


        val habitItem =AllItemData.allHabitToDoMap[habitIndex]
        if (habitItem != null){
            val projectName = findViewById<TextView>(R.id.projectName)
            projectName.text = habitItem.name
            // 總達成天數
            val totalDaysReached = findViewById<TextView>(R.id.totalDaysReached)
            totalDaysReached.text = habitItem.endItemList.size.toString()

            // 開始時間
            findViewById<TextView>(R.id.startDate).text = habitItem.startDate

            //重要程度
            val importantCategory = arrayOf("重要","還好","普通","不重要","可不做")
            findViewById<TextView>(R.id.important).text = importantCategory[habitItem.important]

            //專案
            findViewById<TextView>(R.id.project).text = habitItem.project

            val  repeatCycle = findViewById<TextView>(R.id.repeatCycle)
            when(habitItem.timeType){
                "日" ->{
                    repeatCycle.text ="日 ： ${habitItem.repeatCycle}  天／次"
                }
                "週" ->{
                    repeatCycle.text ="週 ： 星期${habitItem.repeatCycle}"
                }
                "月" ->{
                    repeatCycle.text ="月 ： ${habitItem.repeatCycle}  號"
                }
                "年" ->{
                    repeatCycle.text ="年 ： ${habitItem.repeatCycle}  日"
                }
            }

        }


    }
}

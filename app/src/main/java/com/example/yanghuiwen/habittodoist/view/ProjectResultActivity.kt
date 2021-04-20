package com.example.yanghuiwen.habittodoist.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yanghuiwen.habittodoist.AllItemData
import com.example.yanghuiwen.habittodoist.ItemDate
import com.example.yanghuiwen.habittodoist.R
import com.example.yanghuiwen.habittodoist.view.item_sample.HabitItem
import com.example.yanghuiwen.habittodoist.view.item_sample.SingleItem

class ProjectResultActivity : AppCompatActivity() {

    var todayList: SingleItem<String>?  = null
    var habitList: HabitItem<String>?  = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_result)
        val projectName = intent.getBundleExtra("bundle")?.getString("name")


        if (projectName != null){
            initAllToDoList(projectName)
        }

    }

    fun initAllToDoList(projectName:String) {

        findViewById<TextView>(R.id.name).text = projectName

        val allProjectItemDate =AllItemData.getProjectItemDate(projectName)
        val allProjectHabitDate =AllItemData.getProjectHabitDate(projectName)

        var notEndItemAmount = 0 //未完成
        var endItemAmount = 0 // 已完成
        for ((key,item) in allProjectItemDate){
            if(item.IsEndItem){
                //完成
                endItemAmount++
            }else{
                notEndItemAmount++
            }

        }
        findViewById<TextView>(R.id.notEndItemAmount).text = notEndItemAmount.toString()

        findViewById<TextView>(R.id.endItemAmount).text = endItemAmount.toString()


         todayList = SingleItem(this@ProjectResultActivity,allProjectItemDate,"todayToDo")
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        val mRecyclerView = findViewById<RecyclerView>(R.id.todayList)
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.adapter = todayList

        habitList = HabitItem(this@ProjectResultActivity,allProjectHabitDate,"habitToDo")
        val habitLayoutManager = LinearLayoutManager(this@ProjectResultActivity)
        habitLayoutManager.orientation = LinearLayoutManager.VERTICAL
        val habit_RecyclerView = findViewById<RecyclerView>(R.id.habitList)
        habit_RecyclerView?.layoutManager = habitLayoutManager
        habit_RecyclerView?.adapter = habitList



    }
}

package com.example.yanghuiwen.habittodoist.view.not_date_todolist_page

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yanghuiwen.habittodoist.AllItemData
import com.example.yanghuiwen.habittodoist.ItemDate
import com.example.yanghuiwen.habittodoist.R
import com.example.yanghuiwen.habittodoist.view.item_sample.SingleItem


@RequiresApi(Build.VERSION_CODES.O)
class NotDateToDoListView(context: Context) : RelativeLayout(context){
    val view = LayoutInflater.from(context).inflate(R.layout.to_do_list, null)
    val layoutManager = LinearLayoutManager(context)
    val mRecyclerView = view.findViewById<View>(R.id.toDoList) as RecyclerView
    init {
        var habitDate= ItemDate()
        habitDate.name = "猶疑"
        habitDate.startDate ="2020-12-26"
        habitDate.endDate ="2020-12-26"
        AllItemData.habitToDo.add(habitDate)
        var habitDate2= ItemDate()
        habitDate2.name = "亥亥"
        habitDate2.startDate ="2020-12-27"
        habitDate2.endDate ="2020-12-27"
        AllItemData.habitToDo.add(habitDate2)
        var habitDate3= ItemDate()
        habitDate3.name = "呵"
        habitDate3.startDate ="2020-12-18"
        habitDate3.endDate ="2020-12-18"
        AllItemData.habitToDo.add(habitDate3)
       // AllItemData.getFirebaseDate()

        Log.i("NotDateToDoListView","im create yoooo")
        //設定 RecyclerView
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        mRecyclerView.layoutManager = layoutManager

//        var todayList: SingleItem<String>?  = null
//        todayList = SingleItem(AllItemData.getNotDateToDo(),"notDateToDo")
//        val layoutManager = LinearLayoutManager(context)
//        layoutManager.orientation = LinearLayoutManager.VERTICAL
//        val mRecyclerView = view.findViewById<View>(R.id.toDoList) as RecyclerView
//        mRecyclerView.layoutManager = layoutManager
//        mRecyclerView.adapter = todayList
        chooseThisPage()
        addView(view)
    }


    fun chooseThisPage(){
        var habitDate3= ItemDate()
        habitDate3.name = "呵"
        habitDate3.startDate ="2020-12-18"
        habitDate3.endDate ="2020-12-18"
        AllItemData.habitToDo.add(habitDate3)
        var todayList: SingleItem<String>?  = null
        todayList = SingleItem(AllItemData.getNotTimeToDo(),"notDateToDo")
        mRecyclerView.adapter = todayList
    }
    fun refreshView() {

    }







}
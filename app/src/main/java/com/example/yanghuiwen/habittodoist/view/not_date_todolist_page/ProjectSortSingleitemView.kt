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
import com.example.yanghuiwen.habittodoist.R
import com.example.yanghuiwen.habittodoist.view.item_sample.ProjectSortSingleItem


@RequiresApi(Build.VERSION_CODES.O)
class ProjectSortSingleitemView(context: Context) : RelativeLayout(context){
    val view = LayoutInflater.from(context).inflate(R.layout.to_do_list, null)
    val layoutManager = LinearLayoutManager(context)
    val mRecyclerView = view.findViewById<View>(R.id.toDoList) as RecyclerView


    init {

        Log.i("NotDateToDoListView","im create yoooo")
        //設定 RecyclerView
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        mRecyclerView.layoutManager = layoutManager

        chooseThisPage()
        addView(view)
    }


    fun chooseThisPage(){
        var todayList: ProjectSortSingleItem<String>?  = null
        todayList = ProjectSortSingleItem(context,AllItemData.getProjectSingleItem(),"singleItemToDo")
        mRecyclerView.adapter = todayList
    }
    fun refreshView() {

    }







}
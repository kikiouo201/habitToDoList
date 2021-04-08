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
import com.example.yanghuiwen.habittodoist.view.item_sample.SortSingleItem


@RequiresApi(Build.VERSION_CODES.O)
class ProjectSortSingleitemView(context: Context) : RelativeLayout(context){
    val view = LayoutInflater.from(context).inflate(R.layout.to_do_list, null)
    val layoutManager = LinearLayoutManager(context)
    val mRecyclerView = view.findViewById<View>(R.id.toDoList) as RecyclerView
    var todayList: ProjectSortSingleItem<String>?  = null

    init {

        //Log.i("projectSortSingleitem","im create yoooo")
        //設定 RecyclerView
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        mRecyclerView.layoutManager = layoutManager

        chooseThisPage()
        addView(view)
    }

    fun chooseSortPage(chooseSort:String){
        var singleItemData= AllItemData.getProjectSingleItem()

        Log.i("ProjectSortSingleitem","chooseSort${chooseSort}")
//        when(chooseSort){
//            "專案" ->{
//                singleItemData= AllItemData.getProjectSingleItem()
//            }
//            "時間" ->{
//                singleItemData= AllItemData.getTimeSingleItem()
//            }
//            "重要性" ->{
//                singleItemData= AllItemData.getImportantSingleItem()
//            }
//        }

        todayList = ProjectSortSingleItem(context,singleItemData,"singleItemToDo")
        mRecyclerView.adapter = todayList
    }

    fun chooseThisPage(){


        todayList = ProjectSortSingleItem(context,AllItemData.getProjectSingleItem(),"singleItemToDo")
        mRecyclerView.adapter = todayList
    }

    fun isDisplayOn(){

        todayList?.isDisplayOn()

    }
    fun isDisplayOff(){
        todayList?.isDisplayOff()
    }
    fun refreshView() {

    }







}
package com.example.yanghuiwen.habittodoist.view.not_date_todolist_page



import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.GridView
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yanghuiwen.habittodoist.AllItemData
import com.example.yanghuiwen.habittodoist.R
import com.example.yanghuiwen.habittodoist.view.item_sample.TableHabitItem
import com.example.yanghuiwen.habittodoist.view.item_sample.TableProjectItem


@RequiresApi(Build.VERSION_CODES.O)
class TableItemView(context: Context,category :String) : RelativeLayout(context){
    val view = LayoutInflater.from(context).inflate(R.layout.project_list, null)
    val layoutManager = LinearLayoutManager(context)
    val mGridView = view.findViewById<View>(R.id.projectList) as GridView
    var tableProjectList: TableProjectItem?  = null
    var tableHabitList: TableHabitItem?  = null
    val mCategory =category
    init {

        //Log.i("projectSortSingleitem","im create yoooo")
        //設定 RecyclerView
//        layoutManager.orientation = LinearLayoutManager.VERTICAL
//        mGridView.layoutManager = layoutManager

        chooseThisPage()
        addView(view)
    }

    fun chooseSortPage(chooseSort:String){
        var singleItemData= AllItemData.getProjectSingleItem()

        Log.i("ProjectSortSingleitem","chooseSort${chooseSort}")
        when(chooseSort){
            "專案" ->{
                singleItemData= AllItemData.getProjectSingleItem()
            }
            "時間" ->{
                singleItemData= AllItemData.getTimeSingleItem()
            }
            "重要性" ->{
                singleItemData= AllItemData.getImportantSingleItem()
            }
        }
        when(mCategory){
            "專案" ->{
                tableProjectList = TableProjectItem(context,singleItemData,"singleItemToDo")
                mGridView.adapter = tableProjectList
            }
            "習慣" ->{
                tableHabitList = TableHabitItem(context,AllItemData.getAllHabitToDo(),"singleItemToDo")
                mGridView.adapter = tableHabitList
            }
        }


    }

    fun chooseThisPage(){

        when(mCategory){
            "專案" ->{
                tableProjectList = TableProjectItem(context,AllItemData.getProjectSingleItem(),"singleItemToDo")
                mGridView.adapter = tableProjectList
            }
            "習慣" ->{
                tableHabitList = TableHabitItem(context,AllItemData.getAllHabitToDo(),"singleItemToDo")
                mGridView.adapter = tableHabitList
            }
        }
    }
    fun isDisplayOn(){

      //  todayList?.isDisplayOn()

    }
    fun isDisplayOff(){
        //todayList?.isDisplayOff()
    }
    fun refreshView() {

    }







}
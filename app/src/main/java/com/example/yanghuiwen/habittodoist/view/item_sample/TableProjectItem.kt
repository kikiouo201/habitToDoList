package com.example.yanghuiwen.habittodoist.view.item_sample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.yanghuiwen.habittodoist.AllItemData
import com.example.yanghuiwen.habittodoist.ItemDate
import com.example.yanghuiwen.habittodoist.R
import com.example.yanghuiwen.habittodoist.view.HabitResultActivity
import com.example.yanghuiwen.habittodoist.view.ProjectResultActivity
import java.util.ArrayList
class TableProjectItem(context: Context, data: Map<Int, String>, toDoName :String) :  BaseAdapter() {
        private var datas =data
        private var context =context
        private var projectName = data
        private var dataKeys = ArrayList<Int>()
        private var dataValue = ArrayList<String>()
        init {
            projectName = data
            val sortedData = mutableMapOf<Int, String>()
            val noEndProjects = mutableMapOf<Int, String>()

            this.context =context

            //資料排序
            for ((key,value) in projectName){
            val allProjectItemDate = AllItemData.getProjectItemDate(value)
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
                if (notEndItemAmount==0){
                    noEndProjects.put(key,value)
                }else{
                    sortedData.put(key,value)
                }
            }

            for ((key,value) in noEndProjects){
                sortedData.put(key,value)
            }

            // 將排序完的資料 放入 負責儲存顯示資料的datas 中
            datas = sortedData
            for ((key,value) in datas){
                dataKeys.add(key)
                dataValue.add(value)
            }
        }

        inner class MyHolder(){
            lateinit var infoText : TextView
            lateinit var completionRate : TextView
            lateinit var mTableItem : RelativeLayout
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//        var view:View ?= null
//        var myHolder:MyHolder ?= null

            var view:View ?= null
            var myHolder:MyHolder ?= null

            if(convertView == null){
                myHolder = MyHolder()
                if (parent != null) {
                    view = LayoutInflater.from(context).inflate(R.layout.project_item,null)
                }
                if (view != null) {
                    myHolder.infoText = view.findViewById(R.id.info_text)
                    myHolder.completionRate = view.findViewById(R.id.completion_rate)
                    myHolder.mTableItem = view.findViewById(R.id.tableItem)
                    view.tag = myHolder
                }


            }else{
                view = convertView
                myHolder = view.tag as MyHolder
            }


                myHolder.infoText.text = dataValue[position]
                val allProjectItemDate = AllItemData.getProjectItemDate(dataValue[position])
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
                myHolder.completionRate.text = "未完成：${notEndItemAmount}個事項"

            myHolder.mTableItem.setOnClickListener {
                var bundle= Bundle()
                bundle.putString("name",dataValue[position])
               // bundle.putString("projectIndex", )


                var intent = Intent(context, ProjectResultActivity::class.java)
                intent.putExtra("bundle",bundle)
                context.startActivity(intent)
            }

            return view!!
        }

        override fun getItem(position: Int): Any? {
            return null
        }
        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getCount(): Int {
            //返回一个整数,就是要在listview中现实的数据的条数
            return datas.size
        }

    }
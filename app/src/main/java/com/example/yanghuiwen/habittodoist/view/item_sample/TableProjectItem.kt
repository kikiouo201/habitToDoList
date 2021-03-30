package com.example.yanghuiwen.habittodoist.view.item_sample

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.yanghuiwen.habittodoist.ItemDate
import com.example.yanghuiwen.habittodoist.R
import java.util.ArrayList
class TableProjectItem(context: Context, data: Map<String, ArrayList<ItemDate>>, toDoName :String) :  BaseAdapter() {
        private var datas =data
        private var context =context

        init {
            datas =data
            this.context =context
        }

        inner class MyHolder(){
            lateinit var infoText : TextView
            lateinit var completionRate : TextView
//            lateinit var mRelative : RelativeLayout
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
//                    myHolder.mRelative = view.findViewById(R.id.Realtive)
                    view.tag = myHolder
                }


            }else{
                view = convertView
                myHolder = view.tag as MyHolder
            }


            myHolder.infoText.text =  "read"
            myHolder.completionRate.text = "87%"
//            myHolder.mRelative.setBackgroundColor(datas.get(position).colorId)

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
            return 10
        }

    }
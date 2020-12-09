package com.example.yanghuiwen.habittodoist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainActivity : AppCompatActivity() {
    var todayList:SingleItem<String>?  = null
    var habitList:SingleItem<String>?  = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setContentView(R.layout.activity_main)
        val todayToDo = ArrayList<String>()
        todayToDo.add("早安")
        todayToDo.add("呵呵")

        val habitToDo = ArrayList<String>()
        habitToDo.add("habit")
        habitToDo.add("呵")


        todayList = SingleItem(todayToDo)
        val layoutManager = LinearLayoutManager(this);
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        val mRecyclerView = findViewById<View>(R.id.todayList) as RecyclerView
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.adapter = todayList

        habitList = SingleItem(habitToDo)
        val habitLayoutManager = LinearLayoutManager(this);
        habitLayoutManager.orientation = LinearLayoutManager.VERTICAL
        val habit_RecyclerView = findViewById<View>(R.id.habitList) as RecyclerView
        habit_RecyclerView.layoutManager = habitLayoutManager
        habit_RecyclerView.adapter = habitList
    }



    inner class SingleItem<T>(data: List<T>) : RecyclerView.Adapter<SingleItem<T>.ViewHolder>() {
        var mData: List<T> = ArrayList()

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var mTextView: TextView

            init {
                mTextView = itemView.findViewById<View>(R.id.info_text) as TextView
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.to_do_item, parent, false)
            return ViewHolder(v)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.mTextView.text = mData[position].toString()
            holder.itemView.setOnClickListener {
                val textView = findViewById<TextView>(R.id.info_text)
                textView.text = mData.toString() + ""
            }
            holder.itemView.setOnLongClickListener { false }
        }

        override fun getItemCount(): Int {
            return mData.size
        }

        init {
            mData = data
        }
    }
}


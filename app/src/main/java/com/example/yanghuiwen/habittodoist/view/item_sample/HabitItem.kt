package com.example.yanghuiwen.habittodoist.view.item_sample

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.yanghuiwen.habittodoist.*
import com.example.yanghuiwen.habittodoist.view.AddHabitActivity
import com.example.yanghuiwen.habittodoist.view.AddItemActivity
import java.util.ArrayList

class HabitItem<T>(context: Context, data: Map<String, HabitDate>, toDoName :String) : RecyclerView.Adapter<HabitItem<T>.ViewHolder>() {
    var mData: Map<String, HabitDate> = sortedMapOf()
    val mDateKey = ArrayList<String>()
    val mDateValue = ArrayList<HabitDate>()
    var toDoName = toDoName
    val context = context
    val important = arrayOf(R.color.important0,R.color.important1,R.color.important2,R.color.important3,R.color.important4)
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mTextView: TextView
        var checkBox: CheckBox
        var item: RelativeLayout
        init {
            mTextView = itemView.findViewById<View>(R.id.info_text) as TextView
            checkBox = itemView.findViewById<View>(R.id.checkBox) as CheckBox
            item = itemView.findViewById(R.id.item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.to_do_item, parent, false)
        return ViewHolder(v)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.item.setBackgroundResource(important[mDateValue[position].important])
        holder.mTextView.text = mDateValue[position].name
        holder.checkBox.isChecked = mDateValue[position].IsEndItem
        holder.mTextView.setOnClickListener {
            holder.checkBox.isChecked = !holder.checkBox.isChecked
            mDateValue[position].IsEndItem = holder.checkBox.isChecked
            AllItemData.modifyDateHabitToDo(mDateKey[position].toInt(),mDateValue[position])

        }
        holder.checkBox.setOnCheckedChangeListener{ buttonView, isChecked->
            mDateValue[position].IsEndItem = isChecked
            AllItemData.modifyDateHabitToDo(mDateKey[position].toInt(),mDateValue[position])
        }
        holder.mTextView.setOnLongClickListener {
            startAddItemActivity(context,mDateValue[position] , toDoName)
            return@setOnLongClickListener true
        }

    }

    override fun getItemCount(): Int {
        return mData.size
    }

    init {
        mData = data
        data.forEach{(key,value)->
            mDateKey.add(key)
            mDateValue.add(value)
        }
    }
    fun startAddItemActivity(context: Context,currentItemDate: HabitDate, toDoName :String){

        var bundle= Bundle()
        bundle.putString("name",currentItemDate.name)
        bundle.putString("toDoName",toDoName)


        var intent = Intent(context, AddHabitActivity::class.java)
        intent.putExtra("bundle",bundle)
        context.startActivity(intent)

    }
}


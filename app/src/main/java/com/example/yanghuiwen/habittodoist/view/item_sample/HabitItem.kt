package com.example.yanghuiwen.habittodoist.view.item_sample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.yanghuiwen.habittodoist.HabitDate
import com.example.yanghuiwen.habittodoist.ItemDate
import com.example.yanghuiwen.habittodoist.MainActivity
import com.example.yanghuiwen.habittodoist.R
import com.example.yanghuiwen.habittodoist.view.AddHabitActivity
import com.example.yanghuiwen.habittodoist.view.AddItemActivity
import java.util.ArrayList

class HabitItem<T>(context: Context, data: ArrayList<HabitDate>, toDoName :String) : RecyclerView.Adapter<HabitItem<T>.ViewHolder>() {
    var mData: ArrayList<HabitDate> = ArrayList()
    var toDoName = toDoName
    val context = context
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mTextView: TextView
        var checkBox: CheckBox

        init {
            mTextView = itemView.findViewById<View>(R.id.info_text) as TextView
            checkBox = itemView.findViewById<View>(R.id.checkBox) as CheckBox
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.to_do_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.mTextView.text = mData[position].name
        holder.mTextView.setOnClickListener {
            startAddItemActivity(context,mData[position] , toDoName)
        }
        holder.checkBox.setOnCheckedChangeListener{ buttonView, isChecked->
            mData[position].IsEndItem = isChecked
        }

        holder.mTextView.setOnLongClickListener { false }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    init {
        mData = data
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

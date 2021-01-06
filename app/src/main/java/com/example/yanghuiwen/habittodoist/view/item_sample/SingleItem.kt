package com.example.yanghuiwen.habittodoist.view.item_sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yanghuiwen.habittodoist.ItemDate
import com.example.yanghuiwen.habittodoist.R
import java.util.ArrayList

class SingleItem<T>(data: ArrayList<ItemDate>, toDoName :String) : RecyclerView.Adapter<SingleItem<T>.ViewHolder>() {
    var mData: ArrayList<ItemDate> = ArrayList()
    var toDoName = toDoName
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
            startAddItemActivity(mData[position] , toDoName)
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

    fun startAddItemActivity(currentItemDate: ItemDate, toDoName :String){

        var bundle= Bundle()
        bundle.putString("name",currentItemDate.name)
        bundle.putString("toDoName",toDoName)


//        var intent = Intent(context, AddItemActivity::class.java)
//        intent.putExtra("bundle",bundle)
//        startActivity(intent)
    }
}


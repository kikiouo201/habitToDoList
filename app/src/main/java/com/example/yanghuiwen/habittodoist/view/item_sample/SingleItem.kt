package com.example.yanghuiwen.habittodoist.view.item_sample

import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.yanghuiwen.habittodoist.AllItemData
import com.example.yanghuiwen.habittodoist.ItemDate
import com.example.yanghuiwen.habittodoist.MainActivity
import com.example.yanghuiwen.habittodoist.R
import com.example.yanghuiwen.habittodoist.view.AddHabitActivity
import com.example.yanghuiwen.habittodoist.view.AddItemActivity
import java.util.ArrayList

class SingleItem<T>(context: Context, data: Map<String, ItemDate>, toDoName :String) : RecyclerView.Adapter<SingleItem<T>.ViewHolder>() {
    var mData: Map<String, ItemDate> = sortedMapOf()
    val mDateKey = ArrayList<String>()
    val mDateValue = ArrayList<ItemDate>()
    var toDoName = toDoName
    val context = context
    val important = arrayOf(R.color.important0,R.color.important1,R.color.important2,R.color.important3,R.color.important4)
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mTextView: TextView
        var checkBox: CheckBox
        var item: RelativeLayout
        init {
            mTextView = itemView.findViewById(R.id.info_text)
            checkBox = itemView.findViewById(R.id.checkBox)
            item = itemView.findViewById(R.id.item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.to_do_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.item.setBackgroundResource(important[mDateValue[position].important])
        holder.mTextView.text = mDateValue[position].name
        holder.mTextView.setOnClickListener {
            holder.checkBox.isChecked = !holder.checkBox.isChecked
            mDateValue[position].IsEndItem = holder.checkBox.isChecked
            AllItemData.modifyAllItem(mDateKey[position].toInt(),mDateValue[position])
        }
        holder.checkBox.setOnCheckedChangeListener{ buttonView, isChecked->
            mDateValue[position].IsEndItem = isChecked
            AllItemData.modifyAllItem(mDateKey[position].toInt(),mDateValue[position])
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
    fun startAddItemActivity(context: Context,currentItemDate: ItemDate, toDoName :String){

        var bundle= Bundle()
        bundle.putString("name",currentItemDate.name)
        bundle.putString("toDoName",toDoName)


        when(toDoName){
            "habitToDo" ->{
                var intent = Intent(context, AddItemActivity::class.java)
                intent.putExtra("bundle",bundle)
                context.startActivity(intent)
            }
            "todayToDo" ->{
                var intent = Intent(context, AddItemActivity::class.java)
                intent.putExtra("bundle",bundle)
                context.startActivity(intent)
            }
            "activity" ->{
                var eventID: Long = 1
                for ((id,itemDate) in AllItemData.allActivityMap){
                    if (itemDate != null) {
                        if(currentItemDate.name.equals(itemDate.name)){
                            eventID = id.toLong()
                            val uri: Uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID)
                            val intent = Intent(Intent.ACTION_EDIT)
                                    .setData(uri)
                                    .putExtra(CalendarContract.Events.TITLE, currentItemDate.name)
                            startActivity(context,intent,null)
                        }

                    }
                }
            }
        }

    }
}




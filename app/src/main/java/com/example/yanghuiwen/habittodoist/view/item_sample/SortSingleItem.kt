package com.example.yanghuiwen.habittodoist.view.item_sample


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yanghuiwen.habittodoist.ItemDate
import com.example.yanghuiwen.habittodoist.R
import com.example.yanghuiwen.habittodoist.view.AddItemActivity
import java.util.ArrayList

class SortSingleItem<T>(context: Context, data: Map<String, ArrayList<ItemDate>>, toDoName :String) : RecyclerView.Adapter<SortSingleItem<T>.ViewHolder>() {
    var mData:Map<String, ArrayList<ItemDate>> = sortedMapOf<String, ArrayList<ItemDate>>()
    var projectNames:MutableList<String> =mData.keys.toMutableList()
    var toDoName = toDoName
    val context = context
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var projectName: TextView
        var isDisplay: ImageView
        var mRecyclerView: RecyclerView
        val layoutManager = LinearLayoutManager(context)
        init {
            projectName = itemView.findViewById<View>(R.id.projectName) as TextView
            isDisplay = itemView.findViewById<View>(R.id.isDisplay) as ImageView
            mRecyclerView = itemView.findViewById<View>(R.id.projectToDoList) as RecyclerView

            layoutManager.orientation = LinearLayoutManager.VERTICAL
            mRecyclerView.layoutManager = layoutManager
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.project_to_do_list, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       // Log.i("ProjectSortSingleItem","projectNames.size"+projectNames.size)
       // Log.i("ProjectSortSingleItem","projectNames[position]"+projectNames[position])
        val  projectName=  projectNames[position]
        holder.projectName.text = projectName

        var todayList: SingleItem<String>?  = null
        todayList = mData.get(projectName)?.let { SingleItem(context, it,toDoName ) }
        holder.mRecyclerView.adapter = todayList
        holder.isDisplay.setOnClickListener{

            if(holder.mRecyclerView.visibility == View.GONE){
                holder.isDisplay.setImageResource(R.drawable.down_arrow)
                holder.mRecyclerView.visibility =View.VISIBLE

            }else{
                holder.isDisplay.setImageResource(R.drawable.up_arrow)
                holder.mRecyclerView.visibility = View.GONE
            }

        }
//        holder.projectName.setOnClickListener {
//            startAddItemActivity(context,mData[position] , toDoName)
//        }
//        holder.isDisplay.setOnCheckedChangeListener{ buttonView, isChecked->
//            mData[position].IsEndItem = isChecked
//        }

        holder.projectName.setOnLongClickListener { false }
    }

    override fun getItemCount(): Int {
        Log.i("ProjectSortSingleItem","mData.size"+mData.size)
        return mData.size
    }

    init {
        Log.i("ProjectSortSingleItem","data"+data)
        mData = data
        projectNames =mData.keys.toMutableList()
    }
    fun startAddItemActivity(context: Context,currentItemDate: ItemDate, toDoName :String){

        var bundle= Bundle()
        bundle.putString("name",currentItemDate.name)
        bundle.putString("toDoName",toDoName)


        var intent = Intent(context, AddItemActivity::class.java)
        intent.putExtra("bundle",bundle)
        context.startActivity(intent)

    }
}


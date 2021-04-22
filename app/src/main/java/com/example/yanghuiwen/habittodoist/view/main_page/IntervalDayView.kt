package com.example.yanghuiwen.habittodoist.view.main_page


import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.yanghuiwen.habittodoist.AllItemData
import com.example.yanghuiwen.habittodoist.ItemDate
import com.example.yanghuiwen.habittodoist.R
import com.example.yanghuiwen.habittodoist.view.item_sample.HabitItem
import com.example.yanghuiwen.habittodoist.view.item_sample.SingleItem
import com.example.yanghuiwen.habittodoist.view.tools.TimeFormat
import com.example.yanghuiwen.habittodoist.view.week_viewpager.WeekPageView
import com.example.yanghuiwen.habittodoist.view.week_viewpager.WeekPagerAdapter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
class IntervalDayView(context: Context) : RelativeLayout(context){
    private val TAG = "IntervalDayView"
    val view = LayoutInflater.from(context).inflate(R.layout.month_page, null)
    var todayList: SingleItem<String>?  = null
    private var category ="月"
    var currentDate="2020-03-12"

    init {

        var nowDate = LocalDateTime.now()
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        currentDate = nowDate.format(formatter)
        //Log.i("MainPagerView","currentDate="+currentDate)
        initAllToDoList(currentDate)

        addView(view)
    }

    fun setCategory(category:String){
        this.category=category
        initAllToDoList(currentDate)
    }

    fun chooseThisPage(){

    }
    fun refreshView() {

    }

    fun initAllToDoList(currentDate :String) {
        AllItemData.currentDate = currentDate

        view.findViewById<LinearLayout>(R.id.goalGroup).visibility = View.GONE
        when(category){
            "年" ->{
                val currentDateDiary=AllItemData.getDiary(TimeFormat().formatYear(currentDate))
                if(!currentDateDiary.equals("")){
                    view.findViewById<LinearLayout>(R.id.goalGroup).visibility = View.VISIBLE
                    view.findViewById<TextView>(R.id.goal).text = currentDateDiary
                }
            }
            "月" ->{
                val currentDateDiary=AllItemData.getDiary(TimeFormat().formatYearMonth(currentDate))
                if(!currentDateDiary.equals("")){
                    view.findViewById<LinearLayout>(R.id.goalGroup).visibility = View.VISIBLE
                    view.findViewById<TextView>(R.id.goal).text = currentDateDiary
                }
            }
            "週" ->{

            }
        }

        val intervalDateToDoList =AllItemData.getIntervalDateToDo(category)
       // Log.i(TAG,"intervalDateToDoList.size"+intervalDateToDoList.size)
        if(intervalDateToDoList.isNotEmpty()){
            view.findViewById<LinearLayout>(R.id.todayListGroup).visibility =View.VISIBLE
            view.findViewById<TextView>(R.id.notToDoText).visibility =View.GONE
            todayList = SingleItem(context,intervalDateToDoList,"todayToDo")
            val layoutManager = LinearLayoutManager(context)
            val mRecyclerView = view.findViewById<View>(R.id.todayList) as RecyclerView
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            mRecyclerView.layoutManager = layoutManager
            mRecyclerView.adapter = todayList

        }else{
            view.findViewById<LinearLayout>(R.id.todayListGroup).visibility =View.GONE
            view.findViewById<TextView>(R.id.notToDoText).visibility =View.VISIBLE
        }
//
//        habitList = HabitItem(context,AllItemData.getDateHabitToDo(),"habitToDo")
//        val habitLayoutManager = LinearLayoutManager(context)
//        habitLayoutManager.orientation = LinearLayoutManager.VERTICAL
//        val habit_RecyclerView = view.findViewById<View>(R.id.habitList) as RecyclerView
//        habit_RecyclerView.layoutManager = habitLayoutManager
//        habit_RecyclerView.adapter = habitList
    }







    inner class ScheduleItem<T>(data: ArrayList<ItemDate>) : RecyclerView.Adapter<ScheduleItem<T>.ViewHolder>() {
        var todoData: ArrayList<ItemDate> = ArrayList()

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var startTimeTextView: TextView
            var endTimeTextView: TextView
            var infoText:TextView
            init {
                startTimeTextView = itemView.findViewById<View>(R.id.startTime) as TextView
                endTimeTextView = itemView.findViewById<View>(R.id.endTime) as TextView
                infoText = itemView.findViewById<View>(R.id.info_text) as TextView
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.schedule, parent, false)
            return ViewHolder(v)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val startTime = if(position<10) "0${position}:00" else "${position}:00"
            val endTime = if(position+1<10) "0${position+1}:00" else "${position+1}:00"

            holder.infoText.setText("")
            for (i in 0..todoData.size-1){
                val (hour,min)  = todoData[i].startTime.split(":")
                if(hour.toInt()==position){
                    if(position<10){
                        holder.infoText.setText(todoData[i].name)
                    }else{
                        holder.infoText.setText(todoData[i].name)
                    }
                }
            }


            holder.startTimeTextView.text = startTime
            holder.endTimeTextView.text = endTime
            holder.startTimeTextView.setOnLongClickListener { false }
            holder.endTimeTextView.setOnLongClickListener { false }
        }



        override fun getItemCount(): Int {
            return 24
        }

        init {
            todoData = data
        }
    }





}
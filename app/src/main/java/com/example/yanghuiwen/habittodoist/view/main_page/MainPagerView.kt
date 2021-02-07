package com.example.yanghuiwen.habittodoist.view.main_page

import android.content.Context
import android.os.Build
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.yanghuiwen.habittodoist.AllItemData
import com.example.yanghuiwen.habittodoist.ItemDate
import com.example.yanghuiwen.habittodoist.R
import com.example.yanghuiwen.habittodoist.view.item_sample.SingleItem
import com.example.yanghuiwen.habittodoist.view.week_viewpager.WeekPageView
import com.example.yanghuiwen.habittodoist.view.week_viewpager.WeekPagerAdapter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
class MainPageView(context: Context) : RelativeLayout(context){
    val view = LayoutInflater.from(context).inflate(R.layout.main_page, null)
//    val weeks =intArrayOf(R.id.week7, R.id.week1, R.id.week2, R.id.week3, R.id.week4, R.id.week5, R.id.week6)
//    var startWeek = startDate.dayOfWeek.getValue()
//    val weekSeven = startDate.minusDays(startWeek.toLong())
//    var currentDateIndex = startWeek
//    var currentOnDateChange = onDateChange

    var todayList: SingleItem<String>?  = null
    var habitList: SingleItem<String>?  = null
    var scheduleList: ScheduleItem<String>? =null
    private lateinit var pageList: MutableList<WeekPageView>
    var habit_RecyclerView:RecyclerView? = null
    init {



        var nowDate = LocalDateTime.now()
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val currentDate = nowDate.format(formatter)
        //Log.i("MainPagerView","currentDate="+currentDate)
        initAllToDoList(currentDate)

        //set now date
        val year = view.findViewById<TextView>(R.id.year)
        year.setText(nowDate.year.toString())
        val month = view.findViewById<TextView>(R.id.month)
        month.setText((nowDate.month.value).toString())
        val day = view.findViewById<TextView>(R.id.day)
        day.setText(nowDate.dayOfMonth.toString())


        initWeekViewpage()

        addView(view)
    }

    fun chooseThisPage(){

    }
    fun refreshView() {

    }

    fun initAllToDoList(currentDate :String) {
        AllItemData.currentDate = currentDate
        scheduleList = ScheduleItem(AllItemData.getDateScheduleToDo())
        val scheduleLayoutManager = LinearLayoutManager(context);
        scheduleLayoutManager.orientation = LinearLayoutManager.VERTICAL
        val schedule_RecyclerView = view.findViewById<View>(R.id.timeList) as RecyclerView
        schedule_RecyclerView.layoutManager = scheduleLayoutManager
        schedule_RecyclerView.adapter = scheduleList

        todayList = SingleItem(context,AllItemData.getDateToDayToDo(),"todayToDo")
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        val mRecyclerView = view.findViewById<View>(R.id.todayList) as RecyclerView
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.adapter = todayList

        habitList = SingleItem(context,AllItemData.getDateHabitToDo(),"habitToDo")
        val habitLayoutManager = LinearLayoutManager(context)
        habitLayoutManager.orientation = LinearLayoutManager.VERTICAL
        habit_RecyclerView = view.findViewById<View>(R.id.habitList) as RecyclerView
        habit_RecyclerView?.layoutManager = habitLayoutManager
        habit_RecyclerView?.adapter = habitList
    }



    @RequiresApi(Build.VERSION_CODES.O)
    private fun initWeekViewpage() {

        val current = LocalDateTime.now()

        AllItemData.currentWeekIndex = current.dayOfWeek.getValue()%7
        pageList = ArrayList()

        pageList.add(WeekPageView(context, current.minusDays(21),this::initAllToDoList))
        pageList.add(WeekPageView(context, current.minusDays(14),this::initAllToDoList))
        pageList.add(WeekPageView(context, current.minusDays(7),this::initAllToDoList))
        pageList.add(WeekPageView(context, current,this::initAllToDoList))
        pageList.add(WeekPageView(context, current.plusDays(7),this::initAllToDoList))
        pageList.add(WeekPageView(context, current.plusDays(14),this::initAllToDoList))
        pageList.add(WeekPageView(context, current.plusDays(21),this::initAllToDoList))

        val page = view.findViewById<ViewPager>(R.id.pager)
        val weekPagerAdapter = WeekPagerAdapter(pageList)


        val listener = object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(p0: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                val pageNow = position + 1
                //textView.setText("$pageNow / ${list.size}")
            }
            override fun onPageSelected(p0: Int) {
                Log.i("kiki","p0=${p0}")
                pageList[p0].chooseThisPage()
            }
        }

        page.adapter = weekPagerAdapter
        page.addOnPageChangeListener(listener)

        page.setCurrentItem(3 , false);
    }





//     fun onArticleSelected(position: AllItemData) {
//        Log.i("kiki","habitToDo="+position.habitToDo)
//        habitList = SingleItem(position.habitToDo,"habitToDo")
//
//        habit_RecyclerView?.adapter = habitList
//    }







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
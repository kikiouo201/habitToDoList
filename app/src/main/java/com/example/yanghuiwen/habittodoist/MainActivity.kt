package com.example.yanghuiwen.habittodoist

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.yanghuiwen.habittodoist.view.AddHabitToDoDialogFragment
import com.example.yanghuiwen.habittodoist.view.AddItemActivity
import com.example.yanghuiwen.habittodoist.view.week_viewpager.WeekPageView
import com.example.yanghuiwen.habittodoist.view.week_viewpager.WeekPagerAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.LocalDateTime
import java.util.*

// 加 事項到 行事曆和todolist上
// 加 年 月曆


class MainActivity : AppCompatActivity(), AddHabitToDoDialogFragment.OnHeadlineSelectedListener  {
    var todayList:SingleItem<String>?  = null
    var habitList:SingleItem<String>?  = null
    var scheduleList:ScheduleItem<String>? =null
    private var isFabOpen = false
    private lateinit var pageList: MutableList<WeekPageView>
    var habit_RecyclerView:RecyclerView? = null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try{
            var addItemDate = ItemDate()
            addItemDate.name = intent.getBundleExtra("bundle").getString("name").toString()

            addItemDate.startDate= intent.getBundleExtra("bundle").getString("startDate").toString()
            addItemDate.endDate= intent.getBundleExtra("bundle").getString("endDate").toString()
            addItemDate.startTime= intent.getBundleExtra("bundle").getString("startTime").toString()
            addItemDate.endTime= intent.getBundleExtra("bundle").getString("endTime").toString()
            addItemDate.important = intent.getBundleExtra("bundle").getInt("important")
            addItemDate.urgent =intent.getBundleExtra("bundle").getInt("urgent")
            addItemDate.repeat = intent.getBundleExtra("bundle").getBoolean("repeat")
            if(addItemDate.repeat){
                addItemDate.week = intent.getBundleExtra("bundle").getInt("week")
            }
            addItemDate.project =intent.getBundleExtra("bundle").getString("project").toString()
            AllItemData.todayToDo.add(addItemDate)

        }catch (e :Exception){
            e.printStackTrace()
        }


        initWeekViewpage()

        var scheduleItemDate = ItemDate()
        AllItemData.scheduleToDo.add(scheduleItemDate)
        var scheduleItemDate2 = ItemDate()
        AllItemData.scheduleToDo.add(scheduleItemDate2)




        var itemDate=ItemDate()
        itemDate.name = "猶疑你離12/26"
        itemDate.startDate = "12/26"
        AllItemData.todayToDo.add(itemDate)
        var itemDate2=ItemDate()
        itemDate2.name = "呵呵12/27"
        itemDate2.startDate = "12/27"
        AllItemData.todayToDo.add(itemDate2)
        var itemDate3=ItemDate()
        itemDate3.name = "呵呵12/19"
        itemDate3.startDate = "12/19"
        AllItemData.todayToDo.add(itemDate3)

        var habitDate=ItemDate()
        habitDate.name = "猶疑"
        habitDate.startDate ="12/26"
        AllItemData.habitToDo.add(habitDate)
        var habitDate2=ItemDate()
        habitDate2.name = "亥亥"
        habitDate2.startDate ="12/27"
        AllItemData.habitToDo.add(habitDate2)
        var habitDate3=ItemDate()
        habitDate3.name = "呵"
        habitDate3.startDate ="12/18"
        AllItemData.habitToDo.add(habitDate3)




        initAllToDoList("12/26")


        val addItemFab: FloatingActionButton = findViewById(R.id.addItem)
        addItemFab.setOnClickListener { view ->
        var habitToDoDialogFragment = AddHabitToDoDialogFragment()
            habitToDoDialogFragment.setOnHeadlineSelectedListener(this)
            supportFragmentManager.let {
                habitToDoDialogFragment.show(it, "")
            }
        }
        addItemFab.hide()
        val addHabitFab: FloatingActionButton = findViewById(R.id.addHabit)
        addHabitFab.setOnClickListener { view ->
            startActivity(Intent(MainActivity@this, AddItemActivity::class.java))
        }
        addHabitFab.hide()
        val addFab: FloatingActionButton = findViewById(R.id.add)
        addFab.setOnClickListener { view ->
           if(!isFabOpen){
               addItemFab.show()
               addHabitFab.show()
           }else{
               addItemFab.hide()
               addHabitFab.hide()

           }
            isFabOpen = !isFabOpen
        }




    }


      fun initAllToDoList(currentDate :String) {
          AllItemData.currentDate = currentDate
        scheduleList = ScheduleItem(AllItemData.getDateScheduleToDo())
        val scheduleLayoutManager = LinearLayoutManager(this);
        scheduleLayoutManager.orientation = LinearLayoutManager.VERTICAL
        val schedule_RecyclerView = findViewById<View>(R.id.timeList) as RecyclerView
        schedule_RecyclerView.layoutManager = scheduleLayoutManager
        schedule_RecyclerView.adapter = scheduleList

        todayList = SingleItem(AllItemData.getDateToDayToDo())
          val layoutManager = LinearLayoutManager(this)
          layoutManager.orientation = LinearLayoutManager.VERTICAL
          val mRecyclerView = findViewById<View>(R.id.todayList) as RecyclerView
          mRecyclerView.layoutManager = layoutManager
          mRecyclerView.adapter = todayList

        habitList = SingleItem(AllItemData.getDateHabitToDo())
        val habitLayoutManager = LinearLayoutManager(this)
        habitLayoutManager.orientation = LinearLayoutManager.VERTICAL
        habit_RecyclerView = findViewById<View>(R.id.habitList) as RecyclerView
        habit_RecyclerView?.layoutManager = habitLayoutManager
        habit_RecyclerView?.adapter = habitList
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun initWeekViewpage() {

        var current = LocalDateTime.now()

        AllItemData.currentWeekIndex = current.dayOfWeek.getValue()
        pageList = ArrayList()

        pageList.add(WeekPageView(this@MainActivity, current.minusDays(21),this::initAllToDoList))
        pageList.add(WeekPageView(this@MainActivity, current.minusDays(14),this::initAllToDoList))
        pageList.add(WeekPageView(this@MainActivity, current.minusDays(7),this::initAllToDoList))
        pageList.add(WeekPageView(this@MainActivity, current,this::initAllToDoList))
        pageList.add(WeekPageView(this@MainActivity, current.plusDays(7),this::initAllToDoList))
        pageList.add(WeekPageView(this@MainActivity, current.plusDays(14),this::initAllToDoList))
        pageList.add(WeekPageView(this@MainActivity, current.plusDays(21),this::initAllToDoList))

        val page = findViewById<ViewPager>(R.id.pager)
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





    override fun onArticleSelected(position: AllItemData) {
        Log.i("kiki","habitToDo="+position.habitToDo)
        habitList = SingleItem(position.habitToDo)

        habit_RecyclerView?.adapter = habitList
    }

    inner class SingleItem<T>(data: ArrayList<ItemDate>) : RecyclerView.Adapter<SingleItem<T>.ViewHolder>() {
        var mData: ArrayList<ItemDate> = ArrayList()

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
            Log.i("kiki"," position:${position}")
            holder.mTextView.text = mData[position].name
            holder.itemView.setOnClickListener {
                val textView = findViewById<TextView>(R.id.info_text)
                textView.text =  mData[position].name
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




    inner class ScheduleItem<T>(data: ArrayList<ItemDate>) : RecyclerView.Adapter<ScheduleItem<T>.ViewHolder>() {
        var todoData: ArrayList<ItemDate> = ArrayList()

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var startTimeTextView: TextView
            var endTimeTextView: TextView
            init {
                startTimeTextView = itemView.findViewById<View>(R.id.startTime) as TextView
                endTimeTextView = itemView.findViewById<View>(R.id.endTime) as TextView
            }
            // ScheduleItem end
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.schedule, parent, false)
            return ViewHolder(v)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val startTime = if(position<10) "0${position}:00" else "${position}:00"
            val endTime = if(position+1<10) "0${position+1}:00" else "${position+1}:00"



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

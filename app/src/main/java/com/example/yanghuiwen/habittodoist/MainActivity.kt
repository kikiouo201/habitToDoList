package com.example.yanghuiwen.habittodoist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.yanghuiwen.habittodoist.view.AddHabitToDoDialogFragment
import com.example.yanghuiwen.habittodoist.view.AddItemActivity
import com.example.yanghuiwen.habittodoist.view.week_viewpager.WeekPageView
import com.example.yanghuiwen.habittodoist.view.week_viewpager.WeekPagerAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.util.*


class MainActivity : AppCompatActivity(), AddHabitToDoDialogFragment.OnHeadlineSelectedListener  {
    var todayList:SingleItem<String>?  = null
    var habitList:SingleItem<String>?  = null
    var scheduleList:ScheduleItem<String>? =null
    private var isFabOpen = false
    private lateinit var pageList: MutableList<WeekPageView>
    var habit_RecyclerView:RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try{
            val name = intent.getBundleExtra("bundle").getString("name")
            AllItemData.todayToDo.add(name.toString())
            val date= intent.getBundleExtra("bundle").getString("date")
            val time= intent.getBundleExtra("bundle").getString("time")
            val important = intent.getBundleExtra("bundle").getString("important")
            val urgent =intent.getBundleExtra("bundle").getString("urgent")
            val week = intent.getBundleExtra("bundle").getString("week")
            val project =intent.getBundleExtra("bundle").getString("project")


        }catch (e :Exception){
            e.printStackTrace()
        }

        initData()
        initView()

        AllItemData.scheduleToDo.add("habit")
        AllItemData.scheduleToDo.add("呵")
        scheduleList = ScheduleItem(AllItemData.scheduleToDo)
        val scheduleLayoutManager = LinearLayoutManager(this);
        scheduleLayoutManager.orientation = LinearLayoutManager.VERTICAL
        val schedule_RecyclerView = findViewById<View>(R.id.timeList) as RecyclerView
        schedule_RecyclerView.layoutManager = scheduleLayoutManager
        schedule_RecyclerView.adapter = scheduleList


        AllItemData.todayToDo.add("早安")
        AllItemData.todayToDo.add("呵呵")


        AllItemData.habitToDo.add("habit")
        AllItemData.habitToDo.add("呵")


        todayList = SingleItem(AllItemData.todayToDo)
        val layoutManager = LinearLayoutManager(this);
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        val mRecyclerView = findViewById<View>(R.id.todayList) as RecyclerView
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.adapter = todayList

        habitList = SingleItem(AllItemData.habitToDo)
        val habitLayoutManager = LinearLayoutManager(this);
        habitLayoutManager.orientation = LinearLayoutManager.VERTICAL
        habit_RecyclerView = findViewById<View>(R.id.habitList) as RecyclerView
        habit_RecyclerView?.layoutManager = habitLayoutManager
        habit_RecyclerView?.adapter = habitList


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


    private fun initView() {
        val page = findViewById<ViewPager>(R.id.pager)
        page.adapter = WeekPagerAdapter(pageList)
    }

    private fun initData() {
        var date =21;
        pageList = ArrayList()
        pageList.add(WeekPageView(this@MainActivity,date-7,3))
        pageList.add(WeekPageView(this@MainActivity,date,2))
        pageList.add(WeekPageView(this@MainActivity,date+7,7))
    }


    override fun onArticleSelected(position: AllItemData) {
        Log.i("kiki","habitToDo="+position.habitToDo)
        habitList = SingleItem(position.habitToDo)

        habit_RecyclerView?.adapter = habitList
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
                textView.text = mData.toString()
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




    inner class ScheduleItem<T>(data: List<T>) : RecyclerView.Adapter<ScheduleItem<T>.ViewHolder>() {
        var todoData: List<T> = ArrayList()

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var startTimeTextView: TextView
            var endTimeTextView: TextView
            init {
                startTimeTextView = itemView.findViewById<View>(R.id.startTime) as TextView
                endTimeTextView = itemView.findViewById<View>(R.id.endTime) as TextView
            }
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

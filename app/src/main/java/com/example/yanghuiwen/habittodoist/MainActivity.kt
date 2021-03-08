package com.example.yanghuiwen.habittodoist

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.yanghuiwen.habittodoist.view.AddHabitActivity
import com.example.yanghuiwen.habittodoist.view.AddHabitToDoDialogFragment
import com.example.yanghuiwen.habittodoist.view.AddItemActivity
import com.example.yanghuiwen.habittodoist.view.main_page.MainPageView
import com.example.yanghuiwen.habittodoist.view.not_date_todolist_page.OtherToDoListPagerView
import com.example.yanghuiwen.habittodoist.view.main_page.PagerAdapter
import com.example.yanghuiwen.habittodoist.view.main_page.PersonalPageView
import com.example.yanghuiwen.habittodoist.view.week_viewpager.WeekPageView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import java.time.LocalDateTime
import java.util.*


// 加 事項到 行事曆和todolist上
// 加 年 月曆
// 事項的 完成 結束 刪除 儲存


class MainActivity : AppCompatActivity(), AddHabitToDoDialogFragment.OnHeadlineSelectedListener {
//    var todayList:SingleItem<String>?  = null
//    var habitList:SingleItem<String>?  = null
//    var scheduleList:ScheduleItem<String>? =null

    private var isFabOpen = false
    private lateinit var mainPageList: MutableList<RelativeLayout>
    private lateinit var pageList: MutableList<WeekPageView>
//    var habit_RecyclerView:RecyclerView? = null
    // Write a message to the database

//    private var dbRef: DatabaseReference = fireDB.getReference("Users")
//    private var list: MutableList<User> = mutableListOf()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AllItemData.getFirebaseDate()
        AllItemData.todayToDo.forEachIndexed { index, todayToDo ->
                Log.i("MainActivity","Main="+todayToDo.name)
                Log.i("MainActivity",todayToDo.startDate)
            }

        initMainViewpage()


        val scheduleItemDate=ItemDate()
        scheduleItemDate.name = "猶疑你離12/26"
        scheduleItemDate.startDate = "2020-12-26"
        scheduleItemDate.startTime = "13:00"
        AllItemData.scheduleToDo.add(scheduleItemDate)
        var scheduleItemDate2 = ItemDate()
        scheduleItemDate2.name = "猶疑你離12/27"
        scheduleItemDate2.startDate = "2020-12-27"
        scheduleItemDate2.startTime = "1:00"
        AllItemData.scheduleToDo.add(scheduleItemDate2)


        val addItemFab: FloatingActionButton = findViewById(R.id.addItem)
        addItemFab.setOnClickListener { view ->
            supportFragmentManager.let {

                startActivity(Intent(this, AddItemActivity::class.java))
            }
        }
        addItemFab.hide()
        val addHabitFab: FloatingActionButton = findViewById(R.id.addHabit)
        addHabitFab.setOnClickListener { view ->
            startActivity(Intent(this, AddHabitActivity::class.java))
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

    override fun onArticleSelected(position: AllItemData) {
//        Log.i("kiki","habitToDo="+position.habitToDo)
//        habitList = SingleItem(position.habitToDo,"habitToDo")
//
//        habit_RecyclerView?.adapter = habitList
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initMainViewpage() {

        val current = LocalDateTime.now()

        AllItemData.currentWeekIndex = current.dayOfWeek.getValue()%7
                mainPageList = ArrayList()

        mainPageList.add(MainPageView(this@MainActivity))
        mainPageList.add(OtherToDoListPagerView(this@MainActivity))
        mainPageList.add(PersonalPageView(this@MainActivity))

        var tabs= findViewById<TabLayout>(R.id.tabLayout)
        val page = findViewById<ViewPager>(R.id.main_pager)
        val mainPagerAdapter = PagerAdapter(mainPageList)



        val listener = object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(p0: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                val pageNow = position + 1
                //textView.setText("$pageNow / ${list.size}")
            }
            override fun onPageSelected(p0: Int) {
                Log.i("kiki","p0=${p0}")
                when(p0){
                    1 ->{
                        val notDateListPagerView= mainPageList[p0] as OtherToDoListPagerView
                        notDateListPagerView.chooseThisPage()
                    }
                }
            }
        }

        page.adapter = mainPagerAdapter
        page.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
       tabs.setOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(page))
        page.addOnPageChangeListener(listener)

       // page.setCurrentItem(0, false);
    }
    fun initAllToDoList(currentDate :String) {

    }


}

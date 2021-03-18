package com.example.yanghuiwen.habittodoist

import android.Manifest
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.example.yanghuiwen.habittodoist.view.AddHabitActivity
import com.example.yanghuiwen.habittodoist.view.AddHabitToDoDialogFragment
import com.example.yanghuiwen.habittodoist.view.AddItemActivity
import com.example.yanghuiwen.habittodoist.view.calendar_page.CalendarPageView
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


    private  val PROJECTION_ID_INDEX: Int = 0
    private  val PROJECTION_ACCOUNT_NAME_INDEX: Int = 1
    private  val PROJECTION_DISPLAY_NAME_INDEX: Int = 2
    private  val PROJECTION_OWNER_ACCOUNT_INDEX: Int = 3

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val EVENT_PROJECTION: Array<String> = arrayOf(
                CalendarContract.Calendars._ID,                     // 0
                CalendarContract.Calendars.ACCOUNT_NAME,            // 1
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,   // 2
                CalendarContract.Calendars.OWNER_ACCOUNT            // 3
        )

        // 權限檢查
         val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 100
        if (ContextCompat.checkSelfPermission(this@MainActivity,  Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(Manifest.permission.READ_CALENDAR, Manifest.permission.READ_EXTERNAL_STORAGE),
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS)
        }



        val uri: Uri = CalendarContract.Calendars.CONTENT_URI
        val selection: String = "((${CalendarContract.Calendars.ACCOUNT_NAME} = ?) AND (" +
                "${CalendarContract.Calendars.ACCOUNT_TYPE} = ?) AND (" +
                "${CalendarContract.Calendars.OWNER_ACCOUNT} = ?))"
        val selectionArgs: Array<String> = arrayOf("testorsalmon@gmail.com", "com.google", "testorsalmon@gmail.com")

        val cur = contentResolver.query(uri, EVENT_PROJECTION, selection, selectionArgs, null)

        if (cur != null) {
            while (cur.moveToNext()) {
                // Get the field values
                val calID: Long = cur.getLong(PROJECTION_ID_INDEX)
                val displayName: String = cur.getString(PROJECTION_DISPLAY_NAME_INDEX)
                val accountName: String = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX)
                val ownerName: String = cur.getString(PROJECTION_OWNER_ACCOUNT_INDEX)
                // Do something with the values...

            }
        }else{
            Log.i("MainActivity", "cur== null")
        }

       // addEvents()


        AllItemData.getFirebaseDate()
        AllItemData.todayToDo.forEachIndexed { index, todayToDo ->
                Log.i("MainActivity","Main="+todayToDo.name)
                Log.i("MainActivity",todayToDo.startDate)
            }

        initMainViewpage()




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

        val addActivityFab: FloatingActionButton = findViewById(R.id.addActivity)
        addActivityFab.setOnClickListener { view ->
            addIntentEvents()
        }
        addActivityFab.hide()
        val addFab: FloatingActionButton = findViewById(R.id.add)
        addFab.setOnClickListener { view ->
           if(!isFabOpen){
               addItemFab.show()
               addHabitFab.show()
               addActivityFab.show()
           }else{
               addItemFab.hide()
               addHabitFab.hide()
               addActivityFab.hide()
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

        mainPageList.add(MainPageView(this@MainActivity)) // 行程表
        mainPageList.add(OtherToDoListPagerView(this@MainActivity)) // 待辦事項
        mainPageList.add(CalendarPageView(this@MainActivity)) //行事曆
        mainPageList.add(PersonalPageView(this@MainActivity)) // 個人

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
                Log.i("MainActivity","p0=${p0}")
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

    fun addEvents(){
        // 權限檢查
        val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 100
        if (ContextCompat.checkSelfPermission(this@MainActivity,  Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(Manifest.permission.WRITE_CALENDAR, Manifest.permission.READ_EXTERNAL_STORAGE),
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS)
        }


        val calID: Long = 1
        val startMillis: Long = Calendar.getInstance().run {
            set(2021, 2, 14, 7, 30)
            timeInMillis
        }
        val endMillis: Long = Calendar.getInstance().run {
            set(2021, 2, 14, 8, 45)
            timeInMillis
        }

        val values = ContentValues().apply {
            put(CalendarContract.Events.DTSTART, startMillis)
            put(CalendarContract.Events.DTEND, endMillis)
            put(CalendarContract.Events.TITLE, "Jazzercise")
            put(CalendarContract.Events.DESCRIPTION, "Group workout")
            put(CalendarContract.Events.CALENDAR_ID, calID)
            put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles")
        }

        val uri: Uri? = contentResolver.insert(CalendarContract.Events.CONTENT_URI, values)
       // Log.i("MainActivity", "uri$uri")
        val eventID: Long = uri?.lastPathSegment!!.toLong()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addIntentEvents(){

        val nowDate = LocalDateTime.now()

        val startMillis: Long = Calendar.getInstance().run {
            set(nowDate.year, nowDate.monthValue-1, nowDate.dayOfMonth, nowDate.hour,  nowDate.minute)
            timeInMillis
        }
        val endMillis: Long = Calendar.getInstance().run {
            set(nowDate.year, nowDate.monthValue-1, nowDate.dayOfMonth,  nowDate.hour+1, nowDate.minute)
            timeInMillis
        }
        val intent = Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endMillis)
                .putExtra(CalendarContract.Events.TITLE, "")
                .putExtra(CalendarContract.Events.DESCRIPTION, "")
                .putExtra(CalendarContract.Events.EVENT_LOCATION, "")
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                .putExtra(Intent.EXTRA_EMAIL, "")
        startActivity(intent)
    }
}

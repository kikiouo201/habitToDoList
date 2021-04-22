package com.example.yanghuiwen.habittodoist

import android.Manifest
import android.content.ContentUris
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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.example.yanghuiwen.habittodoist.view.AddHabitActivity
import com.example.yanghuiwen.habittodoist.view.AddHabitToDoDialogFragment
import com.example.yanghuiwen.habittodoist.view.AddItemActivity
import com.example.yanghuiwen.habittodoist.view.calendar_page.CalendarPageView
import com.example.yanghuiwen.habittodoist.view.main_page.MainPageView
import com.example.yanghuiwen.habittodoist.view.main_page.PagerAdapter
import com.example.yanghuiwen.habittodoist.view.main_page.PersonalPageView
import com.example.yanghuiwen.habittodoist.view.not_date_todolist_page.OtherToDoListPagerView
import com.example.yanghuiwen.habittodoist.view.week_viewpager.WeekPageView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*




class MainActivity : AppCompatActivity(), AddHabitToDoDialogFragment.OnHeadlineSelectedListener {
//    var todayList:SingleItem<String>?  = null
//    var habitList:SingleItem<String>?  = null
//    var scheduleList:ScheduleItem<String>? =null

    private var isFabOpen = false
    private lateinit var mainPageList: MutableList<RelativeLayout>
   // private lateinit var pageList: MutableList<WeekPageView>




    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        AllItemData.auth = Firebase.auth
       // Log.i("MainActivity",AllItemData.auth.currentUser.toString())



        AllItemData.getFirebaseDate()
//        AllItemData.todayToDo.forEachIndexed { index, todayToDo ->
//                Log.i("MainActivity","Main="+todayToDo.name)
//                Log.i("MainActivity",todayToDo.startDate)
//            }

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


        queryCalendar()
        queryEvent()

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
                        val notDateListPagerView= mainPageList[1] as OtherToDoListPagerView
                        notDateListPagerView.chooseThisPage()
                    }
                    3 ->{
                        val personalPageView= mainPageList[3] as PersonalPageView
                        personalPageView.chooseThisPage()
                    }
                }
            }
        }

        page.adapter = mainPagerAdapter
        page.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
       tabs.setOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(page))
        page.addOnPageChangeListener(listener)
        val pageNum= intent.getBundleExtra("bundle")?.getInt("pageNum")
        if (pageNum != null) {
            page.setCurrentItem(pageNum, false)
        }
        Log.i("MainActivity","pageNum=$pageNum}")
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



    fun queryEvent() {

        val INSTANCE_PROJECTION = arrayOf(
                CalendarContract.Instances.EVENT_ID,
                CalendarContract.Instances.BEGIN,
                CalendarContract.Instances.END,
                CalendarContract.Instances.TITLE)

        val PROJECTION_ID_INDEX = 0
        val PROJECTION_BEGIN_INDEX = 1
        val PROJECTION_END_INDEX = 2
        val PROJECTION_TITLE_INDEX = 3

        val targetCalendar: String =   AllItemData.auth.currentUser?.email.toString()
        // 指定一個時間段，查詢以下時間內的所有活動
        // 月份是從0開始，0-11
        val beginTime = Calendar.getInstance()
        beginTime[2021, 0, 1, 8] = 0
        val startMillis = beginTime.timeInMillis
        val endTime = Calendar.getInstance()
        endTime[2021, 4, 1, 8] = 0
        val endMillis = endTime.timeInMillis
        // 查詢活動
        var cur: Cursor? = null
        val cr = contentResolver
        val builder = CalendarContract.Instances.CONTENT_URI.buildUpon()
        // 定義查詢條件，找出上面日歷中指定時間段的所有活動
        val selection = CalendarContract.Events.CALENDAR_ID + " = ?"
        val selectionArgs = arrayOf("1")
        ContentUris.appendId(builder, startMillis)
        ContentUris.appendId(builder, endMillis)
        // 因為targetSDK=25，所以要在Apps運行時檢查權限
        val permissionCheck = ContextCompat.checkSelfPermission(this@MainActivity,
                Manifest.permission.READ_CALENDAR)
        // 建立List來暫存查詢的結果
        val eventIdList: List<Int> = ArrayList()
        val beginList: List<Long> = ArrayList()
        val titleList: List<String> = ArrayList()
        // 如果使用者給了權限便開始查詢日歷
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {

            cur = cr.query(builder.build(),
                    INSTANCE_PROJECTION,
                    selection,
                    selectionArgs,
                    null)
            Log.i("query_event", String.format("cur =%s", cur))
            if (cur != null) {
                while (cur.moveToNext()) {
                    var eventID: Long = 0

                    var beginVal: Long = 0
                    var endVal: Long = 0
                    var title: String? = null

                    // 取得所需的資料
                    eventID = cur.getLong(PROJECTION_ID_INDEX)
                    beginVal = cur.getLong(PROJECTION_BEGIN_INDEX)
                    endVal = cur.getLong(PROJECTION_END_INDEX)
                    title = cur.getString(PROJECTION_TITLE_INDEX)

                    val calendar = Calendar.getInstance().apply {
                        timeInMillis = beginVal
                    }
                    val endCalendar = Calendar.getInstance().apply {
                        timeInMillis = endVal
                    }


                    val dateFormatter = SimpleDateFormat("yyyy-MM-dd")
                    val timeFormatter = SimpleDateFormat("HH:mm")
                    Log.i("query_event", "Date: ${dateFormatter.format(calendar.time)}")
                    Log.i("query_event", "Date: ${dateFormatter.format(endCalendar.time)}")

                    AllItemData.setActivity(eventID,dateFormatter.format(calendar.time),dateFormatter.format(endCalendar.time),timeFormatter.format(calendar.time),timeFormatter.format(endCalendar.time),title )
                    Log.i("query_event", String.format("eventID=%s", eventID))
                    Log.i("query_event", String.format("beginVal=%s", beginVal))
                    Log.i("query_event", String.format("endVal=%s", endVal))
                    Log.i("query_event", String.format("title=%s", title))
                    // 暫存資料讓使用者選擇
//                    eventIdList.add(eventID.toInt())
//                    beginList.add(beginVal)
//                    titleList.add(title)
                }
                cur.close()
            }
//            if (eventIdList.size() !== 0) {
//                // 建立一個Dialog讓使用者選擇活動
//                val adb = AlertDialog.Builder(this)
//                val items: Array<CharSequence> = titleList.toArray(arrayOfNulls<CharSequence>(titleList.size()))
//                adb.setSingleChoiceItems(items, 0, object : OnClickListener() {
//                    fun onClick(dialog: DialogInterface, which: Int) {
//                        val targetEventId: EditText = findViewById(R.id.event_id) as EditText
//                        val targetStartDateTime: EditText = findViewById(R.id.start_date_time) as EditText
//                        val targetTitle: EditText = findViewById(R.id.title) as EditText
//                        targetEventId.setText(java.lang.String.format("%s", eventIdList[which]))
//                        val startDateTime: String = DateFormat.getDateTimeInstance().format(beginList[which])
//                        targetStartDateTime.setText(startDateTime)
//                        targetTitle.setText(java.lang.String.format("%s", titleList[which]))
//                        dialog.dismiss()
//                    }
//                })
//                adb.setNegativeButton("CANCEL", null)
//                adb.show()
//            } else {
//                val toast: Toast = Toast.makeText(this, "找不到活動", Toast.LENGTH_LONG)
//                toast.show()
//            }
        } else {
//            val toast: Toast = Toast.makeText(this, "沒有所需的權限", Toast.LENGTH_LONG)
//            toast.show()
        }
    }

    fun queryCalendar(){


        val EVENT_PROJECTION: Array<String> = arrayOf(
                CalendarContract.Calendars._ID,                     // 0
                CalendarContract.Calendars.ACCOUNT_NAME,            // 1
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,   // 2
                CalendarContract.Calendars.OWNER_ACCOUNT            // 3
        )

        val PROJECTION_ID_INDEX: Int = 0
        val PROJECTION_ACCOUNT_NAME_INDEX: Int = 1
        val PROJECTION_DISPLAY_NAME_INDEX: Int = 2
        val PROJECTION_OWNER_ACCOUNT_INDEX: Int = 3

        // 權限檢查
        val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 100
        if (ContextCompat.checkSelfPermission(this@MainActivity,  Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(Manifest.permission.READ_CALENDAR, Manifest.permission.READ_EXTERNAL_STORAGE),
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS)
        }


        val userEmail =AllItemData.auth.currentUser?.email.toString()
        val uri: Uri = CalendarContract.Calendars.CONTENT_URI
        val selection: String = "((${CalendarContract.Calendars.ACCOUNT_NAME} = ?) AND (" +
                "${CalendarContract.Calendars.ACCOUNT_TYPE} = ?) AND (" +
                "${CalendarContract.Calendars.OWNER_ACCOUNT} = ?))"
        val selectionArgs: Array<String> = arrayOf(userEmail, "com.google", userEmail)

        val cur = contentResolver.query(uri, EVENT_PROJECTION, selection, selectionArgs, null)

        if (cur != null) {
            while (cur.moveToNext()) {
                // Get the field values
                val calID: Long = cur.getLong(PROJECTION_ID_INDEX)
                val displayName: String = cur.getString(PROJECTION_DISPLAY_NAME_INDEX)
                val accountName: String = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX)
                val ownerName: String = cur.getString(PROJECTION_OWNER_ACCOUNT_INDEX)
                // Do something with the values...
                Log.i("MainActivity","calID${calID} ")

            }
            cur.close()
        }else{
            Log.i("MainActivity", "cur== null")
        }
    }
}

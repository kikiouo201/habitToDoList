package com.example.yanghuiwen.habittodoist.view.calendar_page


import android.Manifest
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.CalendarContract
import android.provider.CalendarContract.Calendars
import android.util.Log
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.viewpager.widget.ViewPager
import com.example.yanghuiwen.habittodoist.AllItemData
import com.example.yanghuiwen.habittodoist.R
import com.example.yanghuiwen.habittodoist.view.main_page.PagerAdapter
import com.example.yanghuiwen.habittodoist.view.week_viewpager.DatePageView
import java.security.AccessController.checkPermission
import java.time.LocalDateTime
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
class CalendarPageView(context: Context) : RelativeLayout(context){
    val view = LayoutInflater.from(context).inflate(R.layout.calendar_page, null)

    private lateinit var pageList: MutableList<RelativeLayout>

    init {

        val current = LocalDateTime.now()

        AllItemData.currentWeekIndex = current.dayOfWeek.getValue()%7
        pageList = ArrayList()
        var currentMonth =Calendar.MONTH
        for (i in -2..4){
            currentMonth =Calendar.MONTH + i
            if(currentMonth<1){
               currentMonth += 12

            }else if(currentMonth>12){
                currentMonth -= 12
            }
            pageList.add(CalendarView(context, currentMonth))
        }


        val page = view.findViewById<ViewPager>(R.id.calendarViewPager)
        val weekPagerAdapter = PagerAdapter(pageList)


        val listener = object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(p0: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                val pageNow = position + 1
                //textView.setText("$pageNow / ${list.size}")
            }
            override fun onPageSelected(p0: Int) {
                Log.i("kiki","p0=${p0}")
              //  pageList[p0].chooseThisPage()
            }
        }

        page.adapter = weekPagerAdapter
        page.addOnPageChangeListener(listener)

        page.setCurrentItem(3 , false);


        addView(view)
    }



    fun chooseThisPage(){

    }
    fun refreshView() {

    }

//    override fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas)
//
//        val paint = Paint()
//        paint.color = Color.BLACK
//        canvas.drawRect(0f,0f,100f,100f,paint)
//    }




//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun initWeekViewpage(page:ViewPager,dateTypes:String) {
//
//        val current = LocalDateTime.now()
//
//        AllItemData.currentWeekIndex = current.dayOfWeek.getValue()%7
//        pageList = ArrayList()
//
//        pageList.add(DatePageView(context, current,dateTypes))
//
//
//        val weekPagerAdapter = PagerAdapter(pageList)
//
//
//        val listener = object: ViewPager.OnPageChangeListener{
//            override fun onPageScrollStateChanged(p0: Int) {}
//
//            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
//                val pageNow = position + 1
//                //textView.setText("$pageNow / ${list.size}")
//            }
//            override fun onPageSelected(p0: Int) {
//                Log.i("kiki","p0=${p0}")
//                //pageList[p0].chooseThisPage()
//            }
//        }
//
//        page.adapter = weekPagerAdapter
//        page.addOnPageChangeListener(listener)
//
//        page.setCurrentItem(3 , false);
//    }


}
package com.example.yanghuiwen.habittodoist.view.main_page

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.viewpager.widget.ViewPager
import com.example.yanghuiwen.habittodoist.R
import com.google.android.material.tabs.TabLayout
import java.util.ArrayList


@RequiresApi(Build.VERSION_CODES.O)
class MainPageView(context: Context) : RelativeLayout(context){
    val view = LayoutInflater.from(context).inflate(R.layout.main_page, null)
    private lateinit var mainPageList: MutableList<RelativeLayout>
    init {
        val dateItem =arrayOf("年","月","週")
        mainPageList = ArrayList()
        for (i in 0..2){
            val dayView = IntervalDayView(context)
            dayView.setCategory(dateItem[i])
            mainPageList.add(dayView)
        }


//        mainPageList.add(IntervalDayView(context))// 年
//        mainPageList.add(IntervalDayView(context))// 月
//        mainPageList.add(IntervalDayView(context))// 週x
        mainPageList.add(DayView(context))// 日

        var chooseSortItem = 0

//        val sort= view.findViewById<Button>(R.id.sort)

        val tabs= view.findViewById<TabLayout>(R.id.tabLayout)
        val page = view.findViewById<ViewPager>(R.id.pager)
        val mainPagerAdapter = PagerAdapter(mainPageList)
       // val isDisplays =view.findViewById<ImageButton>(R.id.isDisplay)

//        sort.setOnClickListener {
//            chooseSortItem++
//            if(chooseSortItem == 3){
//                chooseSortItem = 0
//            }
//
//            Log.i("OtherToDoListPagerView","chooseSortItem${chooseSortItem}")
//            sort.text = sortSingleItem[chooseSortItem]
//            notDate.chooseSortPage(sortSingleItem[chooseSortItem])
//        }


        // tab 的換頁
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Log.i("OtherToDoListPagerView","p0=${tab?.text}")
                if(tab != null){
                    val itemView = mainPageList[3]as DayView
                    when(tab.text){
                        "年" ->{
                            val itemView = mainPageList[0] as IntervalDayView
                            itemView.setCategory(tab.text as String)
                        }
                        "月" -> {
                            val itemView = mainPageList[1] as IntervalDayView
                            itemView.setCategory(tab.text as String)
                        }
                        "週" -> {
                            val itemView = mainPageList[2] as IntervalDayView
                            itemView.setCategory("週")

                        }
                        "日" -> {
                            val habitItemView = mainPageList[3] as DayView

                        }
                    }
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
        // viewpager 的換頁
        val listener = object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(p0: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                val pageNow = position + 1
                //textView.setText("$pageNow / ${list.size}")
            }
            override fun onPageSelected(p0: Int) {

            }
        }

        page.adapter = mainPagerAdapter
        page.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.setOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(page))
        page.addOnPageChangeListener(listener)


        page.setCurrentItem(3 , false)
        addView(view)
    }

    fun chooseThisPage(){
//        for (i in 0..2){
//            val notDate = mainPageList[i] as ProjectSortSingleitemView
//            notDate.chooseThisPage()
//        }
        val notDate0 = mainPageList[0] as IntervalDayView
       // notDate0.chooseThisPage()
        val notDate1 = mainPageList[1] as IntervalDayView
       // notDate1.chooseThisPage()
        val notDate2 = mainPageList[2] as IntervalDayView
       // notDate2.chooseThisPage()
        val notDate3 = mainPageList[3] as DayView
        //notDate3.chooseThisPage()

    }
    fun refreshView() {

    }







}
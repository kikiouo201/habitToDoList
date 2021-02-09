package com.example.yanghuiwen.habittodoist.view.not_date_todolist_page

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.viewpager.widget.ViewPager
import com.example.yanghuiwen.habittodoist.R
import com.example.yanghuiwen.habittodoist.view.main_page.PagerAdapter
import com.google.android.material.tabs.TabLayout
import java.util.ArrayList


@RequiresApi(Build.VERSION_CODES.O)
class OtherToDoListPagerView(context: Context) : RelativeLayout(context){
    val view = LayoutInflater.from(context).inflate(R.layout.other_todolist_page, null)
    private lateinit var mainPageList: MutableList<RelativeLayout>
    init {

        mainPageList = ArrayList()

        mainPageList.add(ProjectSortSingleitemView(context))// 單項
        mainPageList.add(ProjectSortSingleitemView(context))// 清單
        mainPageList.add(ProjectSortSingleitemView(context))// 習慣

        var chooseSortItem = 0
        val sortItem =arrayOf("專案","時間","重要性")
        val sort= view.findViewById<Button>(R.id.sort)

        val tabs= view.findViewById<TabLayout>(R.id.tabLayout)
        val page = view.findViewById<ViewPager>(R.id.pager)
        val mainPagerAdapter = PagerAdapter(mainPageList)

        val notDate = mainPageList[0] as ProjectSortSingleitemView
        sort.setOnClickListener {
            chooseSortItem++
            if(chooseSortItem == 3){
                chooseSortItem = 0
            }

            Log.i("OtherToDoListPagerView","chooseSortItem${chooseSortItem}")
            sort.text = sortItem[chooseSortItem]
            notDate.chooseSortPage(sortItem[chooseSortItem])
        }


        // tab 的換頁
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Log.i("OtherToDoListPagerView","p0=${tab?.text}")
                if(tab != null){
                    when(tab.text){
                        "單項" -> {
                            val singleitemView = mainPageList[0] as ProjectSortSingleitemView
                            sort.setOnClickListener {
                                chooseSortItem++
                                if(chooseSortItem == 3){
                                    chooseSortItem = 0
                                }

                               // Log.i("OtherToDoListPagerView","chooseSortItem${chooseSortItem}")
                                sort.text = sortItem[chooseSortItem]
                                singleitemView.chooseSortPage(sortItem[chooseSortItem])
                            }
                        }
                        "清單" -> {

                        }
                        "習慣" -> {

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



        addView(view)
    }

    fun chooseThisPage(){
        for (i in 0..2){
            val notDate = mainPageList[i] as ProjectSortSingleitemView
            notDate.chooseThisPage()
        }


    }
    fun refreshView() {

    }







}
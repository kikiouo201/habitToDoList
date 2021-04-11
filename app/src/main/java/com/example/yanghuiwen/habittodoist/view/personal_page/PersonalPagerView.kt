package com.example.yanghuiwen.habittodoist.view.main_page

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings.Global.getString
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat.startActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.yanghuiwen.habittodoist.AllItemData
import com.example.yanghuiwen.habittodoist.ItemDate
import com.example.yanghuiwen.habittodoist.MainActivity
import com.example.yanghuiwen.habittodoist.R
import com.example.yanghuiwen.habittodoist.view.GoogleSignInActivity
import com.example.yanghuiwen.habittodoist.view.item_sample.SingleItem
import com.example.yanghuiwen.habittodoist.view.week_viewpager.DatePageView
import com.example.yanghuiwen.habittodoist.view.week_viewpager.WeekPageView
import com.example.yanghuiwen.habittodoist.view.week_viewpager.WeekPagerAdapter
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.android.synthetic.main.personal_page.view.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
class PersonalPageView(context: Context) : RelativeLayout(context){
    val view = LayoutInflater.from(context).inflate(R.layout.personal_page, null)

    private lateinit var pageList: MutableList<RelativeLayout>

    init {
        if (AllItemData.auth.currentUser != null){
            view.account.text = AllItemData.auth.currentUser!!.email.toString()
            Log.i("PersonalPageView",AllItemData.auth.currentUser!!.email.toString())
        }
        Log.i("PersonalPageView",AllItemData.auth.currentUser.toString())
    view.signInButton.setOnClickListener {
        var intent = Intent(context,GoogleSignInActivity::class.java)

        context.startActivity(intent)
    }

    val  yearGoalPager = view.findViewById<ViewPager>(R.id.yearGoalPager)
    initWeekViewpage(yearGoalPager,"year")
    val  monthGoalPager = view.findViewById<ViewPager>(R.id.monthGoalPager)
    initWeekViewpage(monthGoalPager,"month")
//    val  weekGoalPager = view.findViewById<ViewPager>(R.id.weekGoalPager)
//    initWeekViewpage(weekGoalPager,"week")
//    val  diaryPager = view.findViewById<ViewPager>(R.id.diary)
//    initWeekViewpage(diaryPager,"diary")

        addView(view)
    }


    fun chooseThisPage(){

    }
    fun refreshView() {

    }




    @RequiresApi(Build.VERSION_CODES.O)
    private fun initWeekViewpage(page:ViewPager,dateTypes:String) {

        val current = LocalDateTime.now()

        AllItemData.currentWeekIndex = current.dayOfWeek.getValue()%7
        pageList = ArrayList()

        pageList.add(DatePageView(context, current,dateTypes))


        val weekPagerAdapter = PagerAdapter(pageList)


        val listener = object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(p0: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                val pageNow = position + 1
                //textView.setText("$pageNow / ${list.size}")
            }
            override fun onPageSelected(p0: Int) {
                Log.i("kiki","p0=${p0}")
                //pageList[p0].chooseThisPage()
            }
        }

        page.adapter = weekPagerAdapter
        page.addOnPageChangeListener(listener)

        page.setCurrentItem(3 , false);
    }


















}
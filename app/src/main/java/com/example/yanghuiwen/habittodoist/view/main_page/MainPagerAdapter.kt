package com.example.yanghuiwen.habittodoist.view.main_page

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import com.example.yanghuiwen.habittodoist.view.week_viewpager.WeekPageView
import java.util.*

class MainPagerAdapter(private val pageList : MutableList<MainPageView>) : PagerAdapter() {
    private var pageCount = 0
    override fun getCount(): Int {
        return pageList.size
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return o === view
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        Log.i("MainPagerAdapter","i`m come in ${position}")
        container.addView(pageList[position])
        Log.i("MainPagerAdapter","position${position}")
        return pageList[position]
    }

    override fun destroyItem(container: ViewGroup, position: Int, o: Any) {
        container.removeView(o as View)
    }
    override fun getItemPosition(`object`: Any): Int {
        // 待研究
        if (pageCount > 0) {
            pageCount--
            return POSITION_NONE
        }
        return super.getItemPosition(`object`)
    }

    override fun notifyDataSetChanged() {
        pageCount = count
        super.notifyDataSetChanged()
    }

}
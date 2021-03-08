package com.example.yanghuiwen.habittodoist.view.week_viewpager

import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter


class PagerAdapter(private val pageList : MutableList<RelativeLayout>) : PagerAdapter() {
    private var pageCount = 0
    override fun getCount(): Int {
        return pageList.size
    }

//    constructor(context: Context, images: IntArray) : super(){
//        this.context = context
//        this.images = images
//
//    }

//    fun setNowPage(pageCount :Int){
//        this.pageCount =pageCount
//    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return o === view
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(pageList[position])
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
package com.example.yanghuiwen.habittodoist.view.calendar_page

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.yanghuiwen.habittodoist.R
import kotlinx.android.synthetic.main.customview_calendar_day.view.*

class GeneralCalendar@JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {


    val view = View.inflate(context, R.layout.customview_calendar_day, this)
    private var mHoliday =false
    private var mThisMonth  =false
    private var mToday =false
    init {
        context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.GeneralCalendar,
                0, 0).apply {

            try {
                mToday = getBoolean(R.styleable.GeneralCalendar_today, false)
                mThisMonth = getBoolean(R.styleable.GeneralCalendar_thisMonth, true)
                mHoliday = getBoolean(R.styleable.GeneralCalendar_holiday, false)
                view.dayNum.text = getInteger(R.styleable.GeneralCalendar_dayNum, 1).toString()
                if(mHoliday){
                    view.dayNum.setTextColor(getResources().getColor(R.color.blue))
                }
            } finally {
                recycle()
            }
        }
    }

    fun isHoliday(): Boolean {
        return mHoliday
    }

    fun setHoliday(holiday: Boolean) {
        mHoliday = holiday
        if(mHoliday){
            view.dayNum.setTextColor(getResources().getColor(R.color.blue))
        }
        invalidate()
        requestLayout()
    }

    fun setDayNum(dayNum: Int) {
        view.dayNum.text = dayNum.toString()
        invalidate()
        requestLayout()
    }

}
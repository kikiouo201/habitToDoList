package com.example.yanghuiwen.habittodoist.view.calendar_page

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.yanghuiwen.habittodoist.AllItemData
import com.example.yanghuiwen.habittodoist.ItemDate
import com.example.yanghuiwen.habittodoist.R
import java.util.*
import kotlin.collections.ArrayList

class GeneralCalendar: ConstraintLayout{
//JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {
//@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {
//    (context: Context, attrs: AttributeSet) : View(context, attrs){

    companion object {
        private const val TAG = "GeneralCalendar"
    }

    val view =View.inflate(context, R.layout.customview_calendar_day, this)
    private var mMondayDay = 1
    private var mMondayMonth  = 1
    private var mYear = 0
    private var mToday = 0
    private var mWeekNum = 0

    val weeks =intArrayOf(R.id.week7,R.id.week1,R.id.week2,R.id.week3,R.id.week4,R.id.week5,R.id.week6)
    var daysOfWeek = ArrayList<String>()

//
    constructor(context: Context?) : super(context){
//    initViews(attrs)
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        setWillNotDraw(false) ;
        initViews(attrs)
        view.invalidate()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        setWillNotDraw(false)
        initViews(attrs)
        view.invalidate()
    }


//    init {
//
//
//    }

    private  fun  initViews(attrs: AttributeSet?){
        context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.GeneralCalendar,
                0, 0).apply {

            try {
                mToday = getInteger(R.styleable.GeneralCalendar_today, 0)
                mMondayMonth = getInteger(R.styleable.GeneralCalendar_mondayMonth, 1)
                mMondayDay = getInteger(R.styleable.GeneralCalendar_monday, 0)
                mWeekNum= getInteger(R.styleable.GeneralCalendar_weekNum, 1)
                setCalendarDate()

//                if(mHoliday){
//                    view.dayNum.setTextColor(getResources().getColor(R.color.blue))
//                }
            } finally {
                recycle()
            }
        }


    }


    fun setCalendarDate(){
        var monthEndDate= getMonthEndDate(mMondayMonth)
        Log.i(TAG,"mMondayMonth:${mMondayMonth}")
        var currentYear = mYear
        var currentMonth = mMondayMonth
        var currentDate = mMondayDay
        daysOfWeek = ArrayList<String>()
        for (i in 0..6){
            val week = view.findViewById<TextView>(weeks[i])

            if ( monthEndDate >= currentDate){
                week.text = currentDate.toString()

            }else{
                mMondayMonth++
                currentMonth++
                monthEndDate = getMonthEndDate(currentMonth)
                Log.i(TAG,"mMondayMonth++:${mMondayMonth}")
                currentDate = 1
                week.text = currentDate.toString()

            }

            daysOfWeek.add(AllItemData.dateFormatter(currentYear,currentMonth,currentDate))
            currentDate++
        }
    }

    fun getMonthEndDate(mondayNum: Int): Int {
        val cal = Calendar.getInstance()
        cal.set(Calendar.MONTH, mondayNum)

        return cal.getActualMaximum(Calendar.DAY_OF_MONTH)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        Log.i(TAG,"daysOfWeek$daysOfWeek")
        val todayToDoList =AllItemData.getIntervalDateToDayToDo(daysOfWeek)
        val habitToDoList=AllItemData.getIntervalDateHabitToDo(daysOfWeek)
        val activityList =AllItemData.getIntervalDateActivity(daysOfWeek)
        val sortToDoList = mutableMapOf<String,ArrayList<ItemDate>>()
        for (activity in activityList){
            val date =activity.startDate.split("-")

            if(sortToDoList.get(date[2]) != null){
                sortToDoList.get(date[2])!!.add(activity)
            }else{
                val todo = ArrayList<ItemDate>()
                todo.add(activity)
                sortToDoList.put(date[2],todo)
            }
        }

        for (todayToDo in todayToDoList){
            val date =todayToDo.startDate.split("-")

            if(sortToDoList.get(date[2]) != null){
                sortToDoList.get(date[2])!!.add(todayToDo)
            }else{
                val todo = ArrayList<ItemDate>()
                todo.add(todayToDo)
                sortToDoList.put(date[2],todo)
            }
        }

        for (habitToDo in habitToDoList){
            val date =habitToDo.startDate.split("-")

            if(sortToDoList.get(date[2]) != null){
                sortToDoList.get(date[2])!!.add(habitToDo)
            }else{
                val todo = ArrayList<ItemDate>()
                todo.add(habitToDo)
                sortToDoList.put(date[2],todo)
            }
        }


        var dayIndex = 0
        daysOfWeek.forEach { days ->
            val day =days.split("-")
            for ((key ,toDo) in sortToDoList){
                if (day[2].equals(key)){
                    drawEvent(canvas, toDo,dayIndex)
                }

            }
            dayIndex++
        }


//        val events =ArrayList<String>()
//
//        events.add("讀日文")
//        events.add("讀12文")
//        events.add("讀文")
//        drawEvent(canvas,events)


    }

    private fun drawEvent(canvas: Canvas,events:ArrayList<ItemDate>,dayIndex:Int){

        for (i in 0..events.size-1){
//            val eventStartTime =events[i].startTime.split(":")
//            val eventEndTime =events[i].endTime.split(":")
            val eventTop =  50f*(i+1)+10f*i
            val eventBottom = eventTop+50f
            var eventLeft = 10f+153f*dayIndex
            var eventRight =eventLeft+140f



            val paint = Paint()
            paint.color = getResources().getColor(R.color.lightBlue)
            if(events[i].isActivity){
                paint.color = getResources().getColor(R.color.blue)
            }
            canvas.drawRect(eventLeft,eventTop,eventRight,eventBottom,paint)
            val paint1 = Paint()
            paint1.color = Color.BLACK

            paint1.textSize = 30F

            //把字數縮在6個以內(含)
            var name =events[i].name
            if(events[i].name.split("").size>8){
                name =events[i].name.substring(0..6)
            }

            canvas.drawText(name,eventLeft+10f, eventTop+35f,paint1)

        }

//        for (i in 0..events.size-1){
//            val eventPosition = 50f*(i+1)+10f*i
//            val paint = Paint()
//            paint.color = getResources().getColor(R.color.lightBlue)
//            canvas.drawRect(0f,eventPosition,140f,eventPosition+50f,paint)
//            val paint1 = Paint()
//            paint1.color = Color.BLACK
//
//            paint1.textSize = 30F
//            canvas.drawText(events[i],10F, eventPosition+35F,paint1)
//        }

    }

//    fun isHoliday(): Boolean {
//        return mMonday
//    }

//    fun setHoliday(holiday: Boolean) {
//        mMonday = holiday
//        if(mMonday){
//            view.dayNum.setTextColor(getResources().getColor(R.color.blue))
//        }
//        invalidate()
//        requestLayout()
//    }

    fun setMondayDate(year:Int,month: Int,day: Int) {
        mYear = year
        mMondayMonth = month
        mMondayDay  = day
        setCalendarDate()
        invalidate()
        requestLayout()
    }

}
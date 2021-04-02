package com.example.yanghuiwen.habittodoist.view.main_page

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.yanghuiwen.habittodoist.AllItemData
import com.example.yanghuiwen.habittodoist.ItemDate
import com.example.yanghuiwen.habittodoist.R

class Schedule: ConstraintLayout{
//JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {
//@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {
//    (context: Context, attrs: AttributeSet) : View(context, attrs){

    val view =View.inflate(context, R.layout.customview_schedule, this)
//    private var mHoliday =false
//    private var mThisMonth  =false
    private var mComplete =false


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
                R.styleable.Schedule,
                0, 0).apply {

            try {
                mComplete = getBoolean(R.styleable.Schedule_complete, false)
//                mThisMonth = getBoolean(R.styleable.GeneralCalendar_thisMonth, true)
//                mHoliday = getBoolean(R.styleable.GeneralCalendar_holiday, false)
//                view.dayNum.text = getInteger(R.styleable.GeneralCalendar_dayNum, 1).toString()
//                if(mHoliday){
//                    view.dayNum.setTextColor(getResources().getColor(R.color.blue))
//                }
            } finally {
                recycle()
            }
        }


    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val todayToDoList =AllItemData.getDateToDayToDo()
        val habitToDoList=AllItemData.getDateHabitToDo()
        val activityList =AllItemData.getDateActivity()
        val sortToDoList = sortedMapOf<String, ArrayList<ItemDate>>()
        for (todayToDo in todayToDoList){
            val hour =todayToDo.startTime.split(":")

            if(sortToDoList.get(hour[0]) != null){
                sortToDoList.get(hour[0])!!.add(todayToDo)
            }else{
                val todo = ArrayList<ItemDate>()
                todo.add(todayToDo)
                sortToDoList.put(hour[0],todo)
            }
        }

        for (habitToDo in habitToDoList){
            val hour =habitToDo.startTime.split(":")

            var addItemDate =ItemDate()
            addItemDate.name = habitToDo.name
            addItemDate.startTime =habitToDo.startTime
            addItemDate.endTime =habitToDo.endTime
            if(sortToDoList.get(hour[0]) != null){
                sortToDoList.get(hour[0])!!.add(addItemDate)
            }else{
                val todo = ArrayList<ItemDate>()
                todo.add(addItemDate)
                sortToDoList.put(hour[0],todo)
            }
        }

        for (activity in activityList){
            val hour =activity.startTime.split(":")

            if(sortToDoList.get(hour[0]) != null){
                sortToDoList.get(hour[0])!!.add(activity)
            }else{
                val todo = ArrayList<ItemDate>()
                todo.add(activity)
                sortToDoList.put(hour[0],todo)
            }
        }

        for ((key,toDo) in sortToDoList){
            drawEvent(canvas,toDo)
        }



    }

    private fun drawEvent(canvas: Canvas,events:ArrayList<ItemDate>){


        for (i in 0..events.size-1){
            val eventStartTime =events[i].startTime.split(":")
            val eventEndTime =events[i].endTime.split(":")
            val eventTop = eventStartTime[0].toInt()*167f+eventStartTime[1].toInt()*3f
            val eventBottom = eventEndTime[0].toInt()*167f+eventEndTime[1].toInt()*3f
            var eventLeft = 140f
            var eventRight =600f
            if (events.size!= 1){
                eventLeft =140f+(460f/events.size)*i+10f*i
                eventRight = eventLeft+(460f/events.size)
            }


            val paint = Paint()
            paint.color = getResources().getColor(R.color.lightBlue)
            if(events[i].isActivity){
                paint.color = getResources().getColor(R.color.blue)
            }
            canvas.drawRect(eventLeft,eventTop,eventRight,eventBottom,paint)
            val paint1 = Paint()
            paint1.color = Color.BLACK

            paint1.textSize = 30F
            canvas.drawText(events[i].name,eventLeft+15F, eventTop+40f,paint1)

        }

    }

//    fun isHoliday(): Boolean {
//        return mHoliday
//    }
//
//    fun setHoliday(holiday: Boolean) {
//        mHoliday = holiday
//        if(mHoliday){
//            view.dayNum.setTextColor(getResources().getColor(R.color.blue))
//        }
//        invalidate()
//        requestLayout()
//    }

//    fun setDayNum(dayNum: Int) {
//        view.dayNum.text = dayNum.toString()
//        invalidate()
//        requestLayout()
//    }

}
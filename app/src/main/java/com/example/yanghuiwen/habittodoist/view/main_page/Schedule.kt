package com.example.yanghuiwen.habittodoist.view.main_page

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.yanghuiwen.habittodoist.R
import kotlinx.android.synthetic.main.customview_calendar_day.view.*

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
        val events =ArrayList<String>()
        events.add("讀日文")
        events.add("讀12文")
        events.add("讀文")
        drawEvent(canvas,events)


    }

    private fun drawEvent(canvas: Canvas,events:ArrayList<String>){

        for (i in 0..events.size-1){
            val eventPosition = 50f*(i+1)+10f*i
            val paint = Paint()
            paint.color = getResources().getColor(R.color.lightBlue)
            canvas.drawRect(0f,eventPosition,140f,eventPosition+50f,paint)
            val paint1 = Paint()
            paint1.color = Color.BLACK

            paint1.textSize = 30F
            canvas.drawText(events[i],10F, eventPosition+35F,paint1)
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
package com.example.yanghuiwen.habittodoist.view.chart_sample
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ScrollView
import android.widget.Spinner
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.yanghuiwen.habittodoist.R
import com.example.yanghuiwen.habittodoist.view.calendar_page.GeneralCalendar
import java.util.*
import kotlin.collections.ArrayList

class SimpleCalendar: ConstraintLayout{
//JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {
//@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {
//    (context: Context, attrs: AttributeSet) : View(context, attrs){

    val view =View.inflate(context, R.layout.customview_bar_chart, this)
    //    private var mHoliday =false
//    private var mThisMonth  =false
    private var mComplete =false
    private val TAG ="SimpleCalendar"

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
//        val dateCategorySpinner =findViewById<Spinner>(R.id.dateCategorySpinner)
//        val adapter = ArrayAdapter.createFromResource( context, R.array.dateCategory, android.R.layout.simple_spinner_dropdown_item)
//        dateCategorySpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
//
//            }
//            override fun onNothingSelected(parent: AdapterView<*>) {}
//
//        }
//        dateCategorySpinner.adapter = adapter

        val chartScroll = findViewById<ScrollView>(R.id.chartScrollView)

//        chartScroll.scrollTo(100,100)
//        context.theme.obtainStyledAttributes(
//                attrs,
//                R.styleable.Schedule,
//                0, 0).apply {
//
//            try {
//                mComplete = getBoolean(R.styleable.Schedule_complete, false)
//
//            } finally {
//                recycle()
//            }
//        }


    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


        var intervalDate = ArrayList<Int>()

        intervalDate.add(0)
        intervalDate.add(1)
        intervalDate.add(2)
        intervalDate.add(3)
        intervalDate.add(4)
        drawEvent(canvas,intervalDate,"")
    }



    private fun drawEvent(canvas: Canvas, intervalDate:ArrayList<Int>, category:String){

        val cal = Calendar.getInstance()
        val nowCal = Calendar.getInstance()
        var eventLeft = 0f



        cal.set(Calendar.MONTH, intervalDate[0])
        cal.set(Calendar.DAY_OF_MONTH, 1) //這個月第一天
        val firstDateWeek =cal.get(Calendar.DAY_OF_WEEK)-1

        if(firstDateWeek != 7){
            cal.add(Calendar.DATE, - firstDateWeek) // 這個月 第一週的禮拜日
            Log.i("CalendarView","第一週的禮拜日cal.time${cal.time}")
        }
        var currentWeekOfMonth = cal.time
        Log.i(TAG,"nowCal${nowCal.time}")
        Log.i(TAG,"cal.time${cal.time.date}")
        while ((nowCal.time.month != cal.time.month)||(nowCal.time.date > cal.time.date && nowCal.time.month == cal.time.month)){

            for (j in 0..6){
                val eventTop = 500f-60f*j
                val eventBottom = eventTop+50f

                val eventRight = eventLeft+50f

                val line = Paint()
                line.color = getResources().getColor(R.color.superLightGray)
                canvas.drawRect(eventLeft,eventTop,eventRight,eventBottom,line)

                val paint1 = Paint()
                paint1.color = Color.BLACK

                paint1.textSize = 30F
                canvas.drawText(currentWeekOfMonth.date.toString(),eventLeft+5f, eventTop+30f,paint1)
                if(currentWeekOfMonth.date ==1){
                    canvas.drawText((currentWeekOfMonth.month+1).toString()+"月",eventLeft+5f, 120f,paint1)
                }
                cal.add(Calendar.DATE, +1)
                currentWeekOfMonth = cal.time
            }
            eventLeft += 60f
        }
//        Log.i(TAG,"nowCal${nowCal.time}")
//        Log.i(TAG,"cal.time${cal.time}")






    }



}
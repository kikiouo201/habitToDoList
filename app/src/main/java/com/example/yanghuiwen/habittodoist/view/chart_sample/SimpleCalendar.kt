package com.example.yanghuiwen.habittodoist.view.chart_sample
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ScrollView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.yanghuiwen.habittodoist.HabitDate
import com.example.yanghuiwen.habittodoist.R
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

class SimpleCalendar: ConstraintLayout{
//JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {
//@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {
//    (context: Context, attrs: AttributeSet) : View(context, attrs){

    val view =View.inflate(context, R.layout.customview_bar_chart, this)
    //    private var mHoliday =false
    private var habitItem  = HabitDate()
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


        var intervalDate = ArrayList<Int>()

        val nowDate = LocalDateTime.now()
        var nowMonth=nowDate.month.value-1

        for ( i in 0..3){
            if (nowMonth>=0){
                intervalDate.add(nowMonth)
                nowMonth-=1
            }else{
                nowMonth=11
                intervalDate.add(nowMonth)
                nowMonth-=1
            }

        }
        intervalDate.reverse()

        drawEvent(canvas,intervalDate,"")
    }



    private fun drawEvent(canvas: Canvas, intervalDate:ArrayList<Int>, category:String){

        val cal = Calendar.getInstance()
        val nowCal = Calendar.getInstance()
        var eventLeft = 0f



        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR))
        cal.set(Calendar.MONTH, intervalDate[0])
        cal.set(Calendar.DATE, 1) //這個月第一天
        val firstDateWeek =cal.get(Calendar.DAY_OF_WEEK)-1

        if(firstDateWeek != 7){
            cal.add(Calendar.DATE, - firstDateWeek) // 這個月 第一週的禮拜日
            Log.i(TAG,"第一週的禮拜日cal.time${cal.time}")
        }
        var currentWeekOfMonth = cal.time
        while ((nowCal.time.month != cal.time.month)||(nowCal.time.date > cal.time.date && nowCal.time.month == cal.time.month)){

            val date = nowCal.time.date
            for (j in 0..6){

                val eventTop = 430f-60f*j
                val eventBottom = eventTop+50f
                val eventRight = eventLeft+50f

                val mCurrentDate  = SimpleDateFormat("yyyy-MM-dd").format(cal.time)
                val frame = Paint()
                frame.color = getResources().getColor(R.color.superLightGray)
                for( endItemDate in habitItem.endItemList){
                    Log.i(TAG,"mCurrentDate${mCurrentDate}")
                    if(endItemDate.equals(mCurrentDate)){
                        frame.color = getResources().getColor(R.color.lightBlue)
                        Log.i(TAG,"come on lol lol")
                    }
                }

                canvas.drawRect(eventLeft,eventTop,eventRight,eventBottom,frame)


                val dateText = Paint()
                dateText.color = Color.BLACK


                dateText.textSize = 30F
                if(currentWeekOfMonth.date>9){
                    canvas.drawText(currentWeekOfMonth.date.toString(),eventLeft+5f, eventTop+35f,dateText)
                }else{
                    canvas.drawText(currentWeekOfMonth.date.toString(),eventLeft+15f, eventTop+35f,dateText)
                }

                if(currentWeekOfMonth.date ==1){
                    canvas.drawText((currentWeekOfMonth.month+1).toString()+"月",eventLeft+5f, 40f,dateText)
                }
                cal.add(Calendar.DATE, +1)
                currentWeekOfMonth = cal.time
            }
            eventLeft += 60f
        }

    }
    fun setHabitItem(habitItem: HabitDate){
        this.habitItem = habitItem
    }


}
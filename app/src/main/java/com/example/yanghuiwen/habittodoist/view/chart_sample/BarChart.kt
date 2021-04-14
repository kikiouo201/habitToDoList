package com.example.yanghuiwen.habittodoist.view.chart_sample
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.ScrollView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.yanghuiwen.habittodoist.HabitDate
import com.example.yanghuiwen.habittodoist.R
import java.time.LocalDate

class BarChart: ConstraintLayout{
//JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {
//@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {
//    (context: Context, attrs: AttributeSet) : View(context, attrs){

    val view =View.inflate(context, R.layout.customview_bar_chart, this)

    private var habitItem  = HabitDate()
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
         val cal = LocalDate.now()

        var times = ArrayList<Int>()
        var intervalDate = ArrayList<String>()
        val monthTimes = countMonthTimes()
        var currentMonth= 12+cal.month.value-11
        for (i in 0..11){

            if(currentMonth<13){
                val monthTime =monthTimes[currentMonth]
                if (monthTime != null){
                    times.add(monthTime)
                }else{
                    times.add(0)
                }

                intervalDate.add("${currentMonth}月")
                currentMonth+=1
            }else{
                currentMonth=1
                val monthTime =monthTimes[currentMonth]
                if (monthTime != null){
                    times.add(monthTime)
                }else{
                    times.add(0)
                }
                intervalDate.add("${currentMonth}月")
                currentMonth+=1
            }
        }
        drawEvent(canvas,times,intervalDate,"")
    }

    fun countMonthTimes():Map<Int,Int>{
        var mMonthTimes = sortedMapOf<Int,Int>()

       for (endItemDate in  habitItem.endItemList){
           val endItemMonth = endItemDate.split("-")[1].toInt()
           if (mMonthTimes[endItemMonth]==null){
               mMonthTimes.put(endItemMonth,1)
           }else{
               mMonthTimes.put(endItemMonth,mMonthTimes[endItemMonth]?.plus(1))
           }
       }
        return mMonthTimes
    }

    private fun drawEvent(canvas: Canvas, times:ArrayList<Int>, intervalDate:ArrayList<String>, category:String){
        var highestValue =0
        times.forEach { time->
            if(time>highestValue) {
                highestValue = time
            }
        }
        val line = Paint()
        line.color = getResources().getColor(R.color.lightGray)
        canvas.drawRect(0f,490f,1190f,492f,line)
        canvas.drawRect(0f,390f,1190f,392f,line)
        canvas.drawRect(0f,290f,1190f,292f,line)
        canvas.drawRect(0f,190f,1190f,192f,line)
        canvas.drawRect(0f,90f,1190f,92f,line)
        for (i in 0..times.size-1){

            val eventTop = 490f-times[i]*(450f/highestValue)
            val eventBottom = 490f
            var eventRight = 20f+10f*(i+1)+70f*i
            var eventLeft = eventRight+70f


            val paint = Paint()
            paint.color = getResources().getColor(R.color.blue)
//            if(times[i].isActivity){
//                paint.color = getResources().getColor(R.color.blue)
//            }
            canvas.drawRect(eventLeft,eventTop,eventRight,eventBottom,paint)
            val paint1 = Paint()
            paint1.color = Color.BLACK

            paint1.textSize = 30F
            canvas.drawText(intervalDate[i],eventRight+5f, eventBottom+33f,paint1)

            val paint2 = Paint()
            paint2.color = Color.BLACK

            paint1.textSize = 30F
            canvas.drawText(times[i].toString(),eventRight+19f, eventTop-15f,paint1)
        }




    }

    fun setHabitItem(habitItem: HabitDate){
        this.habitItem = habitItem
    }


}
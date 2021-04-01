package com.example.yanghuiwen.habittodoist.view.chart_sample
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ScrollView
import android.widget.Spinner
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.yanghuiwen.habittodoist.R

class BarChart: ConstraintLayout{
//JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {
//@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {
//    (context: Context, attrs: AttributeSet) : View(context, attrs){

    val view =View.inflate(context, R.layout.customview_bar_chart, this)
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
//        val todayToDoList =AllItemData.getDateToDayToDo()
//        val habitToDoList=AllItemData.getDateHabitToDo()
//        val activityList =AllItemData.getDateActivity()
//        val sortToDoList = sortedMapOf<String, ArrayList<ItemDate>>()
//        for (todayToDo in todayToDoList){
//            val hour =todayToDo.startTime.split(":")
//
//            if(sortToDoList.get(hour[0]) != null){
//                sortToDoList.get(hour[0])!!.add(todayToDo)
//            }else{
//                val todo = ArrayList<ItemDate>()
//                todo.add(todayToDo)
//                sortToDoList.put(hour[0],todo)
//            }
//        }
//
//        for (habitToDo in habitToDoList){
//            val hour =habitToDo.startTime.split(":")
//
//            if(sortToDoList.get(hour[0]) != null){
//                sortToDoList.get(hour[0])!!.add(habitToDo)
//            }else{
//                val todo = ArrayList<ItemDate>()
//                todo.add(habitToDo)
//                sortToDoList.put(hour[0],todo)
//            }
//        }
//
//        for (activity in activityList){
//            val hour =activity.startTime.split(":")
//
//            if(sortToDoList.get(hour[0]) != null){
//                sortToDoList.get(hour[0])!!.add(activity)
//            }else{
//                val todo = ArrayList<ItemDate>()
//                todo.add(activity)
//                sortToDoList.put(hour[0],todo)
//            }
//        }
//
//        for ((key,toDo) in sortToDoList){
//            drawEvent(canvas,toDo)
//        }
      var times = ArrayList<Int>()
        times.add(10)
        times.add(20)
        times.add(10)
        times.add(30)
        times.add(20)
        times.add(10)
        times.add(30)
        times.add(25)
        times.add(10)
        times.add(30)
        times.add(20)
        times.add(10)
        var intervalDate = ArrayList<String>()
        intervalDate.add("1月")
        intervalDate.add("2月")
        intervalDate.add("3月")
        intervalDate.add("4月")
        intervalDate.add("5月")
        intervalDate.add("6月")
        intervalDate.add("7月")
        intervalDate.add("8月")
        intervalDate.add("9月")
        intervalDate.add("10月")
        intervalDate.add("11月")
        intervalDate.add("12月")
        drawEvent(canvas,times,intervalDate,"")
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
        canvas.drawRect(0f,500f,1190f,502f,line)
        canvas.drawRect(0f,400f,1190f,402f,line)
        canvas.drawRect(0f,300f,1190f,302f,line)
        canvas.drawRect(0f,200f,1190f,202f,line)
        canvas.drawRect(0f,100f,1190f,102f,line)
        for (i in 0..times.size-1){

            val eventTop = 500f-times[i]*(450f/highestValue)
            val eventBottom = 500f
            var eventRight = 20f+10f*(i+1)+70f*i
            var eventLeft = eventRight+70f
//            if (times.size!= 1){
//                eventLeft =140f+(460f/times.size)*i+10f*i
//                eventRight = eventLeft+(460f/times.size)
//            }


            val paint = Paint()
            paint.color = getResources().getColor(R.color.blue)
//            if(times[i].isActivity){
//                paint.color = getResources().getColor(R.color.blue)
//            }
            canvas.drawRect(eventLeft,eventTop,eventRight,eventBottom,paint)
            val paint1 = Paint()
            paint1.color = Color.BLACK

            paint1.textSize = 30F
            canvas.drawText(intervalDate[i],eventRight+5f, eventBottom+35f,paint1)

            val paint2 = Paint()
            paint2.color = Color.BLACK

            paint1.textSize = 30F
            canvas.drawText(times[i].toString(),eventRight+19f, eventTop-20f,paint1)
        }




    }



}
package com.example.yanghuiwen.habittodoist.view

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yanghuiwen.habittodoist.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class AddHabitActivity : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_habit)

        var addToDoName = ""
        var modifyToDoName = "null"
        var addItemDate = ItemDate()

        //start日期
        val startDate = findViewById<Button>(R.id.startDate)
        val endDate = findViewById<Button>(R.id.endDate)
        val currentDate = LocalDateTime.now()
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val dateFormatted = currentDate.format(dateFormatter)
        startDate.setText(dateFormatted)
        addItemDate.startDate = dateFormatted
        startDate.setOnClickListener{
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)


            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val nowEndDates= endDate.text.split("-")
                val nowEndDate = LocalDate.of(nowEndDates[0].toInt(),nowEndDates[1].toInt(),nowEndDates[2].toInt())
                val chooseDate = LocalDate.of(year,monthOfYear+1,dayOfMonth)
                val chooseTime ="${year}-${monthOfYear+1}-${dayOfMonth}"
                if(chooseDate.isAfter(nowEndDate)) {
                    endDate.setText(chooseTime)
                }

                startDate.setText(chooseTime)
                addItemDate.startDate = chooseTime

            }, year, month, day)

            dpd.show()

        }

        //end日期
        val endCurrentDate =currentDate.plusHours(1)
        val endDateFormatted = endCurrentDate.format(dateFormatter)
        endDate.setText(endDateFormatted)
        addItemDate.endDate = endDateFormatted
        endDate.setOnClickListener{
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)


            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val chooseTime ="${year}-${monthOfYear+1}-${dayOfMonth}"
                startDate.setText(chooseTime)
                addItemDate.endDate = chooseTime

            }, year, month, day)

            dpd.show()

        }
        //Time 預設 日
        val dayGroup =findViewById<LinearLayout>(R.id.dayGroup)
        val weekGroup =findViewById<View>(R.id.weekGroup)
        weekGroup.setVisibility(View.GONE)
        val monthGroup =findViewById<LinearLayout>(R.id.monthGroup)
        monthGroup.setVisibility(View.GONE)
        val yearGroup =findViewById<LinearLayout>(R.id.yearGroup)
        yearGroup.setVisibility(View.GONE)
        val dayNumSpinner =findViewById<Spinner>(R.id.dayNumSpinner)
        val adapter = ArrayAdapter.createFromResource(this, R.array.dayNum, android.R.layout.simple_spinner_dropdown_item)
        dayNumSpinner.adapter = adapter

        //Time
        val times =intArrayOf(R.id.dayTime,R.id.weekTime,R.id.monthTime,R.id.yearTime)
        val timeGroup = findViewById<RadioGroup>(R.id.timeGroup)
        val dayTime = findViewById<RadioButton>(R.id.dayTime)
        addToDoName = "日"
        dayTime.isChecked = true
        addItemDate.timeType = 0
        timeGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, i ->
            val radio =findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            addToDoName = radio.text.toString()
            val groupIndex =  timeGroup.indexOfChild(radio)
            addItemDate.timeType = groupIndex

            when(addToDoName){
                "日" -> {
                    // 日
                    dayGroup.setVisibility(View.VISIBLE)
                    weekGroup.setVisibility(View.GONE)
                    monthGroup.setVisibility(View.GONE)
                    yearGroup.setVisibility(View.GONE)
                }
                "週" -> {

                    dayGroup.setVisibility(View.GONE)
                    weekGroup.setVisibility(View.VISIBLE)
                    monthGroup.setVisibility(View.GONE)
                    yearGroup.setVisibility(View.GONE)

                    addItemDate.repeat = false

                    val weeks =intArrayOf(R.id.week1,R.id.week2,R.id.week3,R.id.week4,R.id.week5,R.id.week6,R.id.week7)
                    for(i in 0..6){

                        val week = findViewById<Button>(weeks[i])
                        week.setOnClickListener{
                            addItemDate.week = i+1
                        }

                    }
                }
                "月" -> {
                    dayGroup.setVisibility(View.GONE)
                    weekGroup.setVisibility(View.GONE)
                    monthGroup.setVisibility(View.VISIBLE)
                    yearGroup.setVisibility(View.GONE)
                    var dateArrayList =ArrayList<String>()
                    dateArrayList.add("1 號")
                    dateArrayList.add("13 號")
                    var dateList: AddDayItem<String>? = AddDayItem(dateArrayList)
                    val monthLayoutManager = LinearLayoutManager(this)
                    monthLayoutManager.orientation = LinearLayoutManager.VERTICAL
                    var dateOfMonthRecyclerView:RecyclerView?  = findViewById<View>(R.id.monthRecyclerView) as RecyclerView
                    dateOfMonthRecyclerView?.layoutManager = monthLayoutManager
                    dateOfMonthRecyclerView?.adapter = dateList

                    val addDateOfMonth = findViewById<ImageView>(R.id.addDateOfMonth)
                    addDateOfMonth.setOnClickListener {
                        dateArrayList.add("24 號")
                        if (dateList != null) {
                            dateList.notifyDataSetChanged()
                        }
                    }


                }
                "年" -> {
                    dayGroup.setVisibility(View.GONE)
                    weekGroup.setVisibility(View.GONE)
                    monthGroup.setVisibility(View.GONE)
                    yearGroup.setVisibility(View.VISIBLE)
                    var dateArrayList =ArrayList<String>()
                    dateArrayList.add("01/24")
                    dateArrayList.add("02/12")
                    var dateList: AddDayItem<String>? = AddDayItem(dateArrayList)
                    val yearLayoutManager = LinearLayoutManager(this)
                    yearLayoutManager.orientation = LinearLayoutManager.VERTICAL
                    var dateOfYearRecyclerView:RecyclerView?  = findViewById<View>(R.id.yearRecyclerView) as RecyclerView
                    dateOfYearRecyclerView?.layoutManager = yearLayoutManager
                    dateOfYearRecyclerView?.adapter = dateList

                    val addDateOfYear = findViewById<ImageView>(R.id.addDateOfYear)
                    addDateOfYear.setOnClickListener {
                        dateArrayList.add("02/24")
                        if (dateList != null) {
                            dateList.notifyDataSetChanged()
                        }
                    }

                }
            }

            if(addToDoName !="日"){

                addItemDate.startDate = "無"
                addItemDate.endDate = "無"
                addItemDate.startTime = "無"
                addItemDate.endTime = "無"
            }
            Log.i("AddHabitActivity","addToDoName  i${ i.toInt()}")
        })





        //儲存
        val save = findViewById<Button>(R.id.save)
        val name =findViewById<EditText>(R.id.name)

        // val notTime =findViewById<CheckBox>(R.id.notTime)
        save.setOnClickListener{

            addItemDate.name = name.text.toString()
            Log.i("AddItemActivity", addItemDate.startDate)
            val addHabitDate =HabitDate()
            when(modifyToDoName){
                "null" -> {
                    AllItemData.setAllHabit(addHabitDate)
                }
                "habitToDo" -> {
                    //AllItemData.habitToDo.removeAt(modifyItemIndex)
                }

            }



            var intent = Intent(this, MainActivity::class.java)

            startActivity(intent)


        }

        //刪除
        val delete = findViewById<Button>(R.id.delete)
        delete.setOnClickListener {
//            if(modifyItemIndex != -1){
//                when (modifyToDoName) {
//                    "singleItemToDo" -> {
//                        AllItemData.deleteSingleItem(modifyItemIndex)
//                    }
//                    "habitToDo" -> {
//                        AllItemData.habitToDo.removeAt(modifyItemIndex)
//                    }
//                    "todayToDo" -> {
//                        AllItemData.deleteDateToDayToDo(modifyItemIndex)
//                        AllItemData.todayToDo.removeAt(modifyItemIndex)
//                    }
//                }
//            }
//            var intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
        }

        try{
//            modifyToDoName = intent.getBundleExtra("bundle")?.getString("toDoName").toString()
//            var addName = intent.getBundleExtra("bundle")?.getString("name").toString()
//            when (modifyToDoName){
//                "singleItemToDo" -> {
//                    for ((key,todayToDo) in AllItemData.allToDoMap){
//                        if(addName.equals(todayToDo?.name)){
//                            name.setText(addName)
//                            startDate.setText(todayToDo?.startDate)
//                            endDate.setText(todayToDo?.endDate)
//                            startTime.setText(todayToDo?.startTime)
//                            endTime.setText(todayToDo?.endTime)
//                            modifyItemIndex = key
//                            if (todayToDo != null) {
//                                addItemDate = todayToDo
//                            }
//                        }
//                    }
////
//                }
//                "habitToDo" ->{
//                    AllItemData.habitToDo.forEachIndexed { index, habitToDo ->
//
//                        if(addName.equals(habitToDo.name)){
//                            name.setText(addName)
//                            startDate.setText(habitToDo.startDate)
//                            endDate.setText(habitToDo.endDate)
//                            startTime.setText(habitToDo.startTime)
//                            endTime.setText(habitToDo.endTime)
//                            modifyItemIndex = index
//                            addItemDate = habitToDo
//
//                        }
//                    }
//                }
//                "todayToDo" ->{
//
//                    for ((key,todayToDo) in AllItemData.allToDoMap){
//                        if(addName.equals(todayToDo?.name)){
//                            name.setText(addName)
//                            startDate.setText(todayToDo?.startDate)
//                            endDate.setText(todayToDo?.endDate)
//                            startTime.setText(todayToDo?.startTime)
//                            endTime.setText(todayToDo?.endTime)
//                            modifyItemIndex = key
//                            if (todayToDo != null) {
//                                addItemDate = todayToDo
//                            }
//                        }
//                    }
//                }
//            }



        }catch (e :Exception){
            e.printStackTrace()
        }

    }




    inner class AddDayItem<T>(data: ArrayList<String>) : RecyclerView.Adapter<AddDayItem<T>.ViewHolder>() {
        var dateData: ArrayList<String> = ArrayList()

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var date: Button
            var minus: ImageView
            init {
                date = itemView.findViewById<View>(R.id.date) as Button
                minus= itemView.findViewById<View>(R.id.minus) as ImageView

            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.add_day, parent, false)
            return ViewHolder(v)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         holder.minus.setOnClickListener {
             dateData.removeAt(position)
             notifyDataSetChanged()
         }
            holder.date.text = dateData[position]

        }

        override fun getItemCount(): Int {
            return dateData.size
        }

        init {
            dateData = data
        }
    }



}

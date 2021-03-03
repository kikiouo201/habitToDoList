package com.example.yanghuiwen.habittodoist.view

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
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
import kotlin.collections.HashSet

class AddHabitActivity : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_habit)

        var addToDoName = ""
        var modifyToDoName = "null"
        var addHabitDate = HabitDate()
        var modifyItemIndex = -1
        var repeatCycle =ArrayList<String>()

        //start日期
        val startDate = findViewById<Button>(R.id.startDate)
        val endDate = findViewById<Button>(R.id.endDate)
        val currentDate = LocalDateTime.now()
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val dateFormatted = currentDate.format(dateFormatter)
        startDate.setText(dateFormatted)
        addHabitDate.startDate = dateFormatted
        startDate.setOnClickListener{
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)


            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val nowEndDates= endDate.text.split("-")
                val nowEndDate = LocalDate.of(nowEndDates[0].toInt(),nowEndDates[1].toInt(),nowEndDates[2].toInt())
                val chooseDate = LocalDate.of(year,monthOfYear+1,dayOfMonth)
                var chooseTime =""

                chooseTime ="${year}"
                if(monthOfYear+1<10){
                    chooseTime +="-0${monthOfYear+1}"
                }else{
                    chooseTime +="-${monthOfYear+1}"
                }
                if (dayOfMonth<10){
                    chooseTime +="-0${dayOfMonth}"
                }else{
                    chooseTime +="-${dayOfMonth}"
                }
                if(chooseDate.isAfter(nowEndDate)) {
                    endDate.setText(chooseTime)
                }

                startDate.setText(chooseTime)
                addHabitDate.startDate = chooseTime

            }, year, month, day)

            dpd.show()

        }

        //end日期
        val endCurrentDate =currentDate.plusHours(1)
        val endDateFormatted = endCurrentDate.format(dateFormatter)
        endDate.setText(endDateFormatted)
        addHabitDate.endDate = endDateFormatted
        endDate.setOnClickListener{
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)


            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                var chooseTime ="${year}"
                if(monthOfYear+1<10){
                    chooseTime +="-0${monthOfYear+1}"
                }else{
                    chooseTime +="-${monthOfYear+1}"
                }
                if (dayOfMonth<10){
                    chooseTime +="-0${dayOfMonth}"
                }else{
                    chooseTime +="-${dayOfMonth}"
                }

                endDate.setText(chooseTime)
                addHabitDate.endDate = chooseTime

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


        var dayTime = String()
        var weekTime = ArrayList<String>()
        var monthTime = ArrayList<String>()
        var yearTime = ArrayList<String>()
        val dayNumSpinner =findViewById<Spinner>(R.id.dayNumSpinner)
        val adapter = ArrayAdapter.createFromResource(this, R.array.dayNum, android.R.layout.simple_spinner_dropdown_item)
        dayNumSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                dayTime = (pos+1).toString()
                var dayRepeatCycle = ArrayList<String>()
                dayRepeatCycle.add(dayTime)
                repeatCycle = dayRepeatCycle
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}

        }
        dayNumSpinner.adapter = adapter



        //Time
        val times =intArrayOf(R.id.dayTime,R.id.weekTime,R.id.monthTime,R.id.yearTime)
        val timeGroup = findViewById<RadioGroup>(R.id.timeGroup)
        val timeRadio =findViewById<RadioButton>(times[0])
        timeRadio.isChecked = true
//        var chooseWeek = arrayListOf<Int>()
        addToDoName = "日"
        addHabitDate.timeType = "日"


        fun chooseTimeGroup(modifyRepeatCycle:ArrayList<String>){
            when(addToDoName){
                "日" -> {
                    // 日
                    dayGroup.setVisibility(View.VISIBLE)
                    weekGroup.setVisibility(View.GONE)
                    monthGroup.setVisibility(View.GONE)
                    yearGroup.setVisibility(View.GONE)
                    if(modifyRepeatCycle.size!=0){
                    dayNumSpinner.setSelection(modifyRepeatCycle[0].toInt()-1)
                    }
                    addHabitDate.timeType = "日"

                }
                "週" -> {

                    dayGroup.setVisibility(View.GONE)
                    weekGroup.setVisibility(View.VISIBLE)
                    monthGroup.setVisibility(View.GONE)
                    yearGroup.setVisibility(View.GONE)
                    addHabitDate.timeType = "週"
                    val timeRadio =findViewById<RadioButton>(times[1])
                    timeRadio.isChecked = true

                    val weeks =intArrayOf(R.id.week1,R.id.week2,R.id.week3,R.id.week4,R.id.week5,R.id.week6,R.id.week7)
                    for(i in 0..6){

                        val week = findViewById<Button>(weeks[i])
                        if(modifyRepeatCycle.size!=0){
                            for (j in 0..modifyRepeatCycle.size-1){
                                if ((i+1)== modifyRepeatCycle[j].toInt()){
                                    week.setBackgroundColor(Color.parseColor("#84C1FF"))
                              }
                            }
                        }
                        week.setOnClickListener{
                            week.setBackgroundColor(Color.parseColor("#84C1FF"))
                            weekTime.add((i+1).toString())
                            repeatCycle = weekTime

                        }

                    }
                }
                "月" -> {
                    dayGroup.setVisibility(View.GONE)
                    weekGroup.setVisibility(View.GONE)
                    monthGroup.setVisibility(View.VISIBLE)
                    yearGroup.setVisibility(View.GONE)
                    addHabitDate.timeType = "月"



                    if(modifyRepeatCycle.size != 0){
                        monthTime = modifyRepeatCycle
                    }
                    monthTime.add("1")
                    monthTime.add("13")
                    var dateList: AddDayItem<String>? = AddDayItem(this,monthTime)
                    val monthLayoutManager = LinearLayoutManager(this)
                    monthLayoutManager.orientation = LinearLayoutManager.VERTICAL
                    var dateOfMonthRecyclerView:RecyclerView?  = findViewById<View>(R.id.monthRecyclerView) as RecyclerView
                    dateOfMonthRecyclerView?.layoutManager = monthLayoutManager
                    dateOfMonthRecyclerView?.adapter = dateList

                    val addDateOfMonth = findViewById<ImageView>(R.id.addDateOfMonth)
                    addDateOfMonth.setOnClickListener {
                        monthTime.add("1")
                        if (dateList != null) {
                            dateList.notifyDataSetChanged()
                        }
                        repeatCycle = monthTime
                    }




                }
                "年" -> {
                    dayGroup.setVisibility(View.GONE)
                    weekGroup.setVisibility(View.GONE)
                    monthGroup.setVisibility(View.GONE)
                    yearGroup.setVisibility(View.VISIBLE)
                    addHabitDate.timeType = "年"


                    if(modifyRepeatCycle.size != 0){
                        yearTime = modifyRepeatCycle
                    }
                    var dateList: AddYearItem<String>? = AddYearItem(yearTime)
                    val yearLayoutManager = LinearLayoutManager(this)
                    yearLayoutManager.orientation = LinearLayoutManager.VERTICAL
                    var dateOfYearRecyclerView:RecyclerView?  = findViewById<View>(R.id.yearRecyclerView) as RecyclerView
                    dateOfYearRecyclerView?.layoutManager = yearLayoutManager
                    dateOfYearRecyclerView?.adapter = dateList

                    val addDateOfYear = findViewById<ImageView>(R.id.addDateOfYear)
                    addDateOfYear.setOnClickListener{
                        val c = Calendar.getInstance()
                        val year = c.get(Calendar.YEAR)
                        val month = c.get(Calendar.MONTH)
                        val day = c.get(Calendar.DAY_OF_MONTH)


                        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                            var chooseTime =""
                            if(monthOfYear+1<10){
                                chooseTime +="0${monthOfYear+1}"
                            }else{
                                chooseTime +="${monthOfYear+1}"
                            }
                            if (dayOfMonth<10){
                                chooseTime +="-0${dayOfMonth}"
                            }else{
                                chooseTime +="-${dayOfMonth}"
                            }
                            yearTime.add(chooseTime)
                            if (dateList != null) {
                                dateList.notifyDataSetChanged()
                            }
                            repeatCycle = yearTime

                        }, year, month, day)

                        dpd.show()

                    }


                }
            }
        }
        chooseTimeGroup(ArrayList<String>())
        timeGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, i ->
            val radio =findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            addToDoName = radio.text.toString()
            chooseTimeGroup(ArrayList<String>())
        })



        //重要
        val importants =intArrayOf(R.id.important5,R.id.important4,R.id.important3,R.id.important2,R.id.important1)
        val importantGroup = findViewById<RadioGroup>(R.id.importantGroup)
        val important3 = findViewById<RadioButton>(R.id.important3)
        important3.isChecked = true
        addHabitDate.important = 2
        importantGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, i ->
            val radio =findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            val groupIndex = importantGroup.indexOfChild(radio)
            addHabitDate.important  = groupIndex

        })


        //急
        val urgents =intArrayOf(R.id.urgent5,R.id.urgent4,R.id.urgent3,R.id.urgent2,R.id.urgent1)
        val urgent3 = findViewById<RadioButton>(R.id.urgent3)
        urgent3.isChecked = true
        addHabitDate.urgent = 2
        val urgentGroup = findViewById<RadioGroup>(R.id.urgentGroup)
        urgentGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, i ->
            val radio =findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            val groupIndex = urgentGroup.indexOfChild(radio)
            addHabitDate.urgent = groupIndex
        })


        //專案
        val spinner = findViewById<Spinner>(R.id.spinner)
        val project = arrayListOf("無", "日文", "程式")
        val projectAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, project)
        spinner.adapter = projectAdapter
        addHabitDate.project = project[0]
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                addHabitDate.project = project[pos]
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}

        }

        //儲存
        val save = findViewById<Button>(R.id.save)
        val name =findViewById<EditText>(R.id.name)

        // val notTime =findViewById<CheckBox>(R.id.notTime)
        save.setOnClickListener{

            addHabitDate.name = name.text.toString()
           // Log.i("AddHabitActivity", addHabitDate.startDate)


            when(modifyToDoName){
                "null" -> {
                    repeatCycle = ArrayList<String>(HashSet<String>(repeatCycle))
                    addHabitDate.repeatCycle = repeatCycle
                    AllItemData.setDateHabitToDo(addHabitDate)
                }
                "habitToDo" -> {
                    AllItemData.modifyDateHabitToDo(modifyItemIndex,addHabitDate)
                }

            }



            var intent = Intent(this, MainActivity::class.java)

            startActivity(intent)


        }

        //刪除
        val delete = findViewById<Button>(R.id.delete)
        delete.setOnClickListener {
            if(modifyItemIndex != -1){
                when (modifyToDoName) {
                    "habitToDo" -> {
                           AllItemData.deleteDateHabitToDo(modifyItemIndex)
                    }
                }
            }
            var intent = Intent(this, MainActivity::class.java)

            startActivity(intent)
        }

        try{
            modifyToDoName = intent.getBundleExtra("bundle")?.getString("toDoName").toString()
            var addName = intent.getBundleExtra("bundle")?.getString("name").toString()
            when (modifyToDoName){

                "habitToDo" ->{
                    for ((key,habitToDo) in AllItemData.allHabitToDoMap){
                        if(addName.equals(habitToDo?.name)){
                            name.setText(addName)
                            startDate.setText(habitToDo?.startDate)
                            endDate.setText(habitToDo?.endDate)
                            addToDoName = habitToDo?.timeType.toString()
                            addHabitDate.timeType = habitToDo?.timeType.toString()
                            repeatCycle = habitToDo?.repeatCycle!!
                            chooseTimeGroup(habitToDo?.repeatCycle!!)
                            var chooseTimeRadio = 0
                            when(addToDoName){
                                "日" -> {
                                    chooseTimeRadio = 0
                                }
                                "週" -> {
                                    chooseTimeRadio = 1
                                }
                                "月" -> {
                                    chooseTimeRadio = 2
                                }
                                "年" -> {
                                    chooseTimeRadio = 3
                                }
                            }
                            val timeRadio =findViewById<RadioButton>(times[chooseTimeRadio])
                            timeRadio.isChecked = true
//                            startTime.setText(todayToDo?.startTime)
//                            endTime.setText(todayToDo?.endTime)
                            modifyItemIndex = key
                            if (habitToDo != null) {
                                addHabitDate = habitToDo
                            }
                        }
                    }
                }

            }



        }catch (e :Exception){
            e.printStackTrace()
        }

    }




    inner class AddDayItem<T>(context: Context,data: ArrayList<String>) : RecyclerView.Adapter<AddDayItem<T>.ViewHolder>() {
        var dateData: ArrayList<String> = ArrayList()
        var context:Context = context

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var date: Spinner
            var minus: ImageView
            init {

                date = itemView.findViewById<View>(R.id.dateSpinner) as Spinner
                minus= itemView.findViewById<View>(R.id.minus) as ImageView

            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.add_month_item, parent, false)
            return ViewHolder(v)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         holder.minus.setOnClickListener {
             dateData.removeAt(position)
             notifyDataSetChanged()
         }

            val adapter = ArrayAdapter.createFromResource(context, R.array.dayNum, android.R.layout.simple_spinner_dropdown_item)
            holder.date.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                    dateData.set(position,parent.selectedItem.toString())
                  //  notifyDataSetChanged()
                }
                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
            holder.date.adapter = adapter
            holder.date.setSelection(dateData[position].toInt()-1)
            Log.i("AddHabitActivity"," dateData[position].toInt()"+dateData[position].toInt())
        }

        override fun getItemCount(): Int {
            return dateData.size
        }

        init {
            dateData = data
        }
    }



    inner class AddYearItem<T>(data: ArrayList<String>) : RecyclerView.Adapter<AddYearItem<T>.ViewHolder>() {
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
            val v = LayoutInflater.from(parent.context).inflate(R.layout.add_year_item, parent, false)
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

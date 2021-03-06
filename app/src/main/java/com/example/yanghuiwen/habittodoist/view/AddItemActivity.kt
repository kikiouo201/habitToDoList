package com.example.yanghuiwen.habittodoist.view

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.yanghuiwen.habittodoist.AllItemData
import com.example.yanghuiwen.habittodoist.ItemDate
import com.example.yanghuiwen.habittodoist.MainActivity
import com.example.yanghuiwen.habittodoist.R
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class AddItemActivity : AppCompatActivity() {
    private val TAG = "AddItemActivity"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)


        var addToDoName = ""
        var modifyToDoName = ""
        var addItemDate = ItemDate()
        var modifyItemIndex = -1
        val spinner = findViewById<Spinner>(R.id.spinner)
        var project = AllItemData.getProjectName()
        project.add("自訂")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, project)
        spinner.adapter = adapter
        addItemDate.project = project[0]
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                if(pos == project.size-1){
                    val item = LayoutInflater.from(this@AddItemActivity).inflate(R.layout.dialog_signin, null)
                    AlertDialog.Builder(this@AddItemActivity)
                            .setTitle("請輸入專案名稱")
                            .setView(item)
                            .setPositiveButton("OK") { _, _ ->
                                val editText = item.findViewById(R.id.edit_text) as EditText
                                val name = editText.text.toString()
                                if (TextUtils.isEmpty(name)) {
                                    Toast.makeText(applicationContext, "請輸入專案名稱", Toast.LENGTH_SHORT).show()
                                    spinner.setSelection(0)
                                } else {
                                    Toast.makeText(applicationContext,  "已新增"+name, Toast.LENGTH_SHORT).show()
                                    AllItemData.setProject(name)
                                    project.add((project.size-1),name)
                                    addItemDate.project = project[(project.size-2)]
                                    adapter.notifyDataSetChanged()
                                    spinner.setSelection(project.size-2)
                                }
                            }
                            .show()

                }else{
                    addItemDate.project = project[pos]
                }

            }
            override fun onNothingSelected(parent: AdapterView<*>) {}

        }




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
                val chooseTime =AllItemData.dateFormatter(year,monthOfYear,dayOfMonth)
//                if(chooseDate.isAfter(nowEndDate)) {
//                    endDate.setText(chooseTime)
//                }

                startDate.setText(chooseTime)
                endDate.setText(chooseTime)
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
                val chooseTime =AllItemData.dateFormatter(year,monthOfYear,dayOfMonth)
                endDate.setText(chooseTime)
                addItemDate.endDate = chooseTime

            }, year, month, day)

            dpd.show()

        }


        // start時間
        val startTime = findViewById<Button>(R.id.startTime)
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        val startTimeFormatted = currentDate.format(timeFormatter)
        startTime.setText(startTimeFormatted)
        addItemDate.startTime = startTimeFormatted
        startTime.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            val chooseTime = SimpleDateFormat("HH:mm").format(cal.time)
                startTime.setText(chooseTime)
                addItemDate.startTime = chooseTime
        }
        TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        // end時間
        val endTime = findViewById<Button>(R.id.endTime)
        var endTimeFormatted = endCurrentDate.format(timeFormatter)
        endTime.setText(endTimeFormatted)
        addItemDate.endTime = endTimeFormatted
        endTime.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                val chooseTime = SimpleDateFormat("HH:mm").format(cal.time)
                endTime.setText(chooseTime)
                addItemDate.endTime = chooseTime
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        //重要
        val importants =intArrayOf(R.id.important5,R.id.important4,R.id.important3,R.id.important2,R.id.important1)
        val importantGroup = findViewById<RadioGroup>(R.id.importantGroup)
        val important3 = findViewById<RadioButton>(R.id.important3)
        important3.isChecked = true
        addItemDate.important = 2
        importantGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, i ->
            val radio =findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            val groupIndex = importantGroup.indexOfChild(radio)

            addItemDate.important  = 4-groupIndex

        })


        //急
//        val urgents =intArrayOf(R.id.urgent5,R.id.urgent4,R.id.urgent3,R.id.urgent2,R.id.urgent1)
//        val urgent3 = findViewById<RadioButton>(R.id.urgent3)
//        urgent3.isChecked = true
//        addItemDate.urgent = 2
//        val urgentGroup = findViewById<RadioGroup>(R.id.urgentGroup)
//        urgentGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, i ->
//            val radio =findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
//            val groupIndex = urgentGroup.indexOfChild(radio)
//            addItemDate.urgent = groupIndex
//        })

        //重複
        val repeat =findViewById<CheckBox>(R.id.repeat)
        val weekGroup =findViewById<View>(R.id.weekGroup)
        weekGroup.setVisibility(View.GONE)
        addItemDate.repeat = false
        repeat.setOnCheckedChangeListener{ buttonView, isChecked ->

            if (!isChecked) {
                weekGroup.setVisibility(View.GONE)
                addItemDate.repeat = false
            }else{
                weekGroup.setVisibility(View.VISIBLE)
                addItemDate.repeat = true
            }
        }

        val weeks =intArrayOf(R.id.week1,R.id.week2,R.id.week3,R.id.week4,R.id.week5,R.id.week6,R.id.week7)
        for(i in 0..6){

            val week = findViewById<Button>(weeks[i])
            week.setOnClickListener{
                addItemDate.week = i+1
            }

        }


        //Time
        val times =intArrayOf(R.id.notTime,R.id.dayTime,R.id.weekTime,R.id.monthTime,R.id.yearTime)
        val timeGroup = findViewById<RadioGroup>(R.id.timeGroup)
        val dayTime = findViewById<RadioButton>(R.id.dayTime)
        addToDoName = "日"
        dayTime.isChecked = true
        addItemDate.timeType = 1
        timeGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, i ->
            val radio =findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            addToDoName = radio.text.toString()
            val groupIndex =  timeGroup.indexOfChild(radio)
            addItemDate.timeType = groupIndex

            when(addToDoName){
                "無" -> {
                    addItemDate.startDate = "無"
                    addItemDate.endDate = "無"
                    addItemDate.startTime = "無"
                    addItemDate.endTime = "無"

                }
                "日" -> {

                }
                "週" -> {

                    addItemDate.startTime = "無"
                    addItemDate.endTime = "無"
                }
                "月" -> {

                    addItemDate.startTime = "無"
                    addItemDate.endTime = "無"
                }
                "年" -> {

                    addItemDate.startTime = "無"
                    addItemDate.endTime = "無"
                }
            }
//            if(addToDoName !="日"){
//
//                addItemDate.startDate = "無"
//                addItemDate.endDate = "無"
//                addItemDate.startTime = "無"
//                addItemDate.endTime = "無"
//            }
            Log.i("AddItemActivity","addToDoName  i${ addToDoName}")
        })

        //儲存
        val save = findViewById<Button>(R.id.save)
        val name =findViewById<EditText>(R.id.name)

       // val notTime =findViewById<CheckBox>(R.id.notTime)
        save.setOnClickListener{

           addItemDate.name = name.text.toString()
            Log.i("AddItemActivity", addItemDate.startDate)
            Log.i("AddItemActivity", "toDoName"+modifyToDoName)
            when(modifyToDoName){
                "null" -> {
                   when(addToDoName){
                        "無" -> {
                            AllItemData.setSingleItem(addItemDate)
                        }
                        "日" -> {
                            AllItemData.setDateToDayToDo(addItemDate)
                        }
                        "週" -> {
                            val mStartDate = LocalDate.parse(addItemDate.startDate)
                            val week = mStartDate.dayOfWeek.value
                            val startDateFormatted = mStartDate.minusDays((week-1).toLong()).format(dateFormatter)
                            addItemDate.startDate = startDateFormatted
                            val endDateFormatted = mStartDate.plusDays(7-(week).toLong()).format(dateFormatter)
                            addItemDate.endDate = endDateFormatted

                            AllItemData.setSingleItem(addItemDate)
                        }
                       "月" -> {
                           AllItemData.setSingleItem(addItemDate)
                       }
                       "年" -> {
                           AllItemData.setSingleItem(addItemDate)
                       }
                   }

                }
                "singleItemToDo" -> {
                    AllItemData.modifySingleItem(modifyItemIndex,addItemDate)
                }
                "habitToDo" -> {
                    //AllItemData.habitToDo.removeAt(modifyItemIndex)
                }
                "todayToDo" -> {
                    AllItemData.modifyDateToDayToDo(modifyItemIndex,addItemDate)
                    //AllItemData.setDateToDayToDo(addItemDate)
                }
            }



            var intent =Intent(this,MainActivity::class.java)

            startActivity(intent)


        }

        //刪除
        val delete = findViewById<Button>(R.id.delete)
        delete.setOnClickListener {
            if(modifyItemIndex != -1){
                when (modifyToDoName) {
                    "singleItemToDo" -> {
                        AllItemData.deleteSingleItem(modifyItemIndex)
                    }
                    "habitToDo" -> {
//                        AllItemData.habitToDo.removeAt(modifyItemIndex)
                    }
                    "todayToDo" -> {
                        AllItemData.deleteDateToDayToDo(modifyItemIndex)
                        AllItemData.todayToDo.removeAt(modifyItemIndex)
                    }
                }
            }
            var intent =Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        try{
            modifyToDoName = intent.getBundleExtra("bundle")?.getString("toDoName").toString()
            var addName = intent.getBundleExtra("bundle")?.getString("name").toString()
            when (modifyToDoName){
                "singleItemToDo" -> {
                    for ((key,todayToDo) in AllItemData.allToDoMap){
                        if(addName.equals(todayToDo?.name)){
                            name.setText(addName)
                            startDate.setText(todayToDo?.startDate)
                            endDate.setText(todayToDo?.endDate)
                            startTime.setText(todayToDo?.startTime)
                            endTime.setText(todayToDo?.endTime)
                            modifyItemIndex = key
                            if (todayToDo != null) {
                                addItemDate = todayToDo
                            }
                        }
                    }
//
                }
                "habitToDo" ->{
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
                }
                "todayToDo" ->{

                  for ((key,todayToDo) in AllItemData.allToDoMap){
                        if(addName.equals(todayToDo?.name)){
                            name.setText(addName)
                            startDate.setText(todayToDo?.startDate)
                            endDate.setText(todayToDo?.endDate)
                            startTime.setText(todayToDo?.startTime)
                            endTime.setText(todayToDo?.endTime)
                            modifyItemIndex = key
                            if (todayToDo != null) {
                                addItemDate = todayToDo
                            }
                        }
                    }
                }
            }



            //important.set
            //urgent =intent.getBundleExtra("bundle").getInt("urgent")
            //repeat = intent.getBundleExtra("bundle").getBoolean("repeat")
//            if(repeat){
//                week = intent.getBundleExtra("bundle").getInt("week")
//            }
//            project =intent.getBundleExtra("bundle")?.getString("project").toString()


        }catch (e :Exception){
            e.printStackTrace()
        }

    }

}
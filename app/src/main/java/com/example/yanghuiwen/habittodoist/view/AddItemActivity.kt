package com.example.yanghuiwen.habittodoist.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
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


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)



        var toDoName = ""
        var addItemDate = ItemDate()
        var modifyItemIndex = -1
        val spinner = findViewById<Spinner>(R.id.spinner)
        val project = arrayListOf("", "日文", "程式")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, project)
        spinner.adapter = adapter
        addItemDate.project = project[0]
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                addItemDate.project = project[pos]
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
        val importantGroup = findViewById<RadioGroup>(R.id.importantGroup)
        val important3 = findViewById<RadioButton>(R.id.important3)
        important3.isChecked = true
        addItemDate.important = 3
        importantGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, i ->
            val radio =findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            addItemDate.important = i+1
        })


        //急
        val urgent3 = findViewById<RadioButton>(R.id.urgent3)
        urgent3.isChecked = true
        addItemDate.urgent = 3
        val urgentGroup = findViewById<RadioGroup>(R.id.urgentGroup)
        urgentGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, i ->
            val radio =findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            addItemDate.urgent = i+1
        })

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

        //儲存
        val save = findViewById<Button>(R.id.save)
        val name =findViewById<EditText>(R.id.name)
        save.setOnClickListener{

           addItemDate.name = name.text.toString()
            Log.i("kiki", addItemDate.startDate)
            Log.i("kiki", "toDoName"+toDoName)
            if(toDoName.equals("null")){
                Log.i("kiki", "toDoNamePass")
                AllItemData.setDateToDayToDo(addItemDate)
            }
            for( todayToDo in AllItemData.todayToDo){
                Log.i("kiki",todayToDo.name)
                Log.i("kiki",todayToDo.startDate)
            }

            var intent =Intent(this,MainActivity::class.java)

            startActivity(intent)


        }

        //刪除
        val delete = findViewById<Button>(R.id.delete)
        delete.setOnClickListener {
            if(modifyItemIndex != -1){
                when (toDoName) {
                    "habitToDo" -> {
                        AllItemData.habitToDo.removeAt(modifyItemIndex)
                    }
                    "todayToDo" -> {
                        AllItemData.todayToDo.removeAt(modifyItemIndex)
                    }
                }
            }
            var intent =Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        try{
            toDoName = intent.getBundleExtra("bundle")?.getString("toDoName").toString()
            var addName = intent.getBundleExtra("bundle")?.getString("name").toString()
            when (toDoName){
                "habitToDo" ->{
                    AllItemData.habitToDo.forEachIndexed { index, habitToDo ->

                        if(addName.equals(habitToDo.name)){
                            name.setText(addName)
                            startDate.setText(habitToDo.startDate)
                            endDate.setText(habitToDo.endDate)
                            startTime.setText(habitToDo.startTime)
                            endTime.setText(habitToDo.endTime)
                            modifyItemIndex = index
                            addItemDate = habitToDo

                        }
                    }
                }
                "todayToDo" ->{
                  AllItemData.todayToDo.forEachIndexed { index, todayToDo ->
                        if(addName.equals(todayToDo.name)){
                            name.setText(addName)
                            startDate.setText(todayToDo.startDate)
                            endDate.setText(todayToDo.endDate)
                            startTime.setText(todayToDo.startTime)
                            endTime.setText(todayToDo.endTime)
                            modifyItemIndex = index
                            addItemDate = todayToDo
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
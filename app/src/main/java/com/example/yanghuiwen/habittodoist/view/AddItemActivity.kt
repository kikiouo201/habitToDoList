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
import com.example.yanghuiwen.habittodoist.MainActivity
import com.example.yanghuiwen.habittodoist.R
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class AddItemActivity : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        var bundle=Bundle()

        val spinner = findViewById<Spinner>(R.id.spinner)
        val project = arrayListOf("", "日文", "程式")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, project)
        spinner.adapter = adapter
        bundle.putString("project","null")
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                bundle.putString("project",project[pos])
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}

        }



        //start日期
        val startDate = findViewById<Button>(R.id.startDate)
        val currentDate = LocalDateTime.now()
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val dateFormatted = currentDate.format(dateFormatter)
        startDate.setText(dateFormatted)
        bundle.putString("startDate",dateFormatted)
        startDate.setOnClickListener{
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)


            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val chooseTime ="${year}-${monthOfYear+1}-${dayOfMonth}"
                startDate.setText(chooseTime)
                bundle.putString("startDate",chooseTime)

            }, year, month, day)

            dpd.show()

        }

        //end日期
        val endDate = findViewById<Button>(R.id.endDate)
        val endCurrentDate =currentDate.plusHours(1)
        val endDateFormatted = endCurrentDate.format(dateFormatter)
        endDate.setText(endDateFormatted)
        bundle.putString("endDate",endDateFormatted)
        endDate.setOnClickListener{
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)


            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val chooseTime ="${year}-${monthOfYear+1}-${dayOfMonth}"
                startDate.setText(chooseTime)
                bundle.putString("endDate",chooseTime)

            }, year, month, day)

            dpd.show()

        }


        // start時間
        val startTime = findViewById<Button>(R.id.startTime)
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        val startTimeFormatted = currentDate.format(timeFormatter)
        startTime.setText(startTimeFormatted)
        bundle.putString("startTime",startTimeFormatted)
        startTime.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            val chooseTime = SimpleDateFormat("HH:mm").format(cal.time)
                startTime.setText(chooseTime)
                bundle.putString("startTime",chooseTime)
        }
        TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        // end時間
        val endTime = findViewById<Button>(R.id.endTime)
        var endTimeFormatted = endCurrentDate.format(timeFormatter)
        endTime.setText(endTimeFormatted)
        bundle.putString("endTime",endTimeFormatted)
        endTime.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                val chooseTime = SimpleDateFormat("HH:mm").format(cal.time)
                endTime.setText(chooseTime)
                bundle.putString("endTime",chooseTime)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        //重要
        val importantGroup = findViewById<RadioGroup>(R.id.importantGroup)
        val important3 = findViewById<RadioButton>(R.id.important3)
        important3.isChecked = true
        bundle.putInt("important",3)
        importantGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, i ->
            val radio =findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            bundle.putInt("important",i+1)
        })


        //急
        val urgent3 = findViewById<RadioButton>(R.id.urgent3)
        urgent3.isChecked = true
        bundle.putInt("urgent",3)
        val urgentGroup = findViewById<RadioGroup>(R.id.urgentGroup)
        urgentGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, i ->
            val radio =findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            bundle.putInt("urgent",i+1)
        })

        //重複
        val repeat =findViewById<CheckBox>(R.id.repeat)
        val weekGroup =findViewById<View>(R.id.weekGroup)
        weekGroup.setVisibility(View.GONE)
        bundle.putBoolean("repeat",false)
        repeat.setOnCheckedChangeListener{ buttonView, isChecked ->

            if (!isChecked) {
                weekGroup.setVisibility(View.GONE)
                bundle.putBoolean("repeat",false)
            }else{
                weekGroup.setVisibility(View.VISIBLE)
                bundle.putBoolean("repeat",true)
            }
        }

        val weeks =intArrayOf(R.id.week1,R.id.week2,R.id.week3,R.id.week4,R.id.week5,R.id.week6,R.id.week7)
        for(i in 0..6){

            val week = findViewById<Button>(weeks[i])
            week.setOnClickListener{
                bundle.putInt("week",i+1)
            }

        }

        //儲存
        var save = findViewById<Button>(R.id.save)
        save.setOnClickListener{
            val name =findViewById<EditText>(R.id.name)
            bundle.putString("name",name.text.toString())


            var intent =Intent(this,MainActivity::class.java)
            intent.putExtra("bundle",bundle)
            startActivity(intent)

            // 傳值過去
        }



    }

}
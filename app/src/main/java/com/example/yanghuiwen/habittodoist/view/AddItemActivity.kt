package com.example.yanghuiwen.habittodoist.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.yanghuiwen.habittodoist.R
import kotlinx.android.synthetic.main.activity_add_item.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class AddItemActivity : AppCompatActivity() {

     var jsonObject =ArrayList<Array<String>>()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)
        val spinner = findViewById<Spinner>(R.id.spinner)
        val project = arrayListOf("", "日文", "程式")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, project)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                jsonObject.add(arrayOf("project",project[pos]))
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }



        //日期
        val date = findViewById<Button>(R.id.date)
        val current = LocalDateTime.now()

        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val dateFormatted = current.format(dateFormatter)
        date.setText(dateFormatted)
        jsonObject.add(arrayOf("date",dateFormatted))
        date.setOnClickListener{
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)


            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val chooseTime ="${year}-${monthOfYear+1}-${dayOfMonth}"
                date.setText(chooseTime)
                jsonObject.add(arrayOf("date",chooseTime))

            }, year, month, day)

            dpd.show()

        }

        //時間
        val time = findViewById<Button>(R.id.time)
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        val timeFormatted = current.format(timeFormatter)
        time.setText(timeFormatted)
        jsonObject.add(arrayOf("time",timeFormatted))
        time.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            val chooseTime = SimpleDateFormat("HH:mm").format(cal.time)
                time.setText(chooseTime)
            jsonObject.add(arrayOf("time",chooseTime))
        }
        TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        //重要

        val importantGroup = findViewById<RadioGroup>(R.id.importantGroup)
        val important3 = findViewById<RadioButton>(R.id.important3)
        important3.isChecked = true
        jsonObject.add(arrayOf("important",important3.text.toString()))
        importantGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, i ->
            val radio =findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            jsonObject.add(arrayOf("important",radio.text.toString()))
        })


        //急
        val urgent3 = findViewById<RadioButton>(R.id.urgent3)
        urgent3.isChecked = true
        jsonObject.add(arrayOf("urgent",urgent3.text.toString()))
        val urgentGroup = findViewById<RadioGroup>(R.id.urgentGroup)
        urgentGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, i ->
            val radio =findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            jsonObject.add(arrayOf("urgent",radio.text.toString()))
        })

        //重複
        val repeat =findViewById<CheckBox>(R.id.repeat)
        val weekGroup =findViewById<View>(R.id.weekGroup)
        weekGroup.setVisibility(View.GONE)
        repeat.setOnCheckedChangeListener{ buttonView, isChecked ->

            if (!isChecked) {
                weekGroup.setVisibility(View.GONE)
            }else{
                weekGroup.setVisibility(View.VISIBLE)
            }
        }
        val weeks =intArrayOf(R.id.week1,R.id.week2,R.id.week3,R.id.week4,R.id.week5,R.id.week6,R.id.week7)
        for(i in 0..6){

            val week = findViewById<Button>(weeks[i])
            week.setOnClickListener{
                jsonObject.add(arrayOf("week",(i+1).toString()))
            }



        }

        //儲存
        var save = findViewById<Button>(R.id.save)
        save.setOnClickListener{
            val name =findViewById<EditText>(R.id.name)
            jsonObject.add(arrayOf("name",name.text.toString()))
            Log.i("kiki","jsonObject"+jsonObject)
            var bundle=Bundle()
            for(i in jsonObject){
                Log.i("kiki",i[0]+i[1])
            }
            //git上去 傳值過去
        }



    }

}
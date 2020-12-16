package com.example.yanghuiwen.habittodoist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager

import java.util.ArrayList

object AllItemData {
    val todayToDo = ArrayList<String>()
    val habitToDo = ArrayList<String>()
    val scheduleToDo = ArrayList<String>()

    fun setHabitToDo(str: String){
        habitToDo.add(str)

    }





}
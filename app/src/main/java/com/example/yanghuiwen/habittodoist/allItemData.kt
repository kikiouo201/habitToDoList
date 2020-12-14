package com.example.yanghuiwen.habittodoist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class allItemData {

    val todayToDo = ArrayList<String>()
    val habitToDo = ArrayList<String>()
    val scheduleToDo = ArrayList<String>()

     fun sethabitToDo(str: String){
        habitToDo.add(str)

    }




}
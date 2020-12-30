package com.example.yanghuiwen.habittodoist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

import java.util.ArrayList

object AllItemData {

    val database = Firebase.database
    private lateinit var todayToDoReference: DatabaseReference
    val myRef = database.getReference("user")
    val todayToDo = ArrayList<ItemDate>()
    var nowTodayToDoIndex = ""
    val habitToDo = ArrayList<ItemDate>()
    var nowHabitToDoIndex = ""
    val scheduleToDo = ArrayList<ItemDate>()
    var nowScheduleToDoIndex = ""
    var currentDate ="2020-12-26"
    var currentWeekIndex = 1
    fun getFirebaseDate(){
        todayToDoReference = database.getReference("user/todayToDo/")
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ItemSnapshot in dataSnapshot.children) {
                    var itemDate= ItemSnapshot.getValue<ItemDate>()
                    nowTodayToDoIndex = ItemSnapshot.key.toString()
                    Log.i("AllItemData","nowTodayToDoIndex"+nowTodayToDoIndex)
                    Log.i("AllItemData","itemData.name"+itemDate?.name)
                    if (itemDate != null) {
                        todayToDo.add(itemDate)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
               // Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }
        todayToDoReference.addValueEventListener(postListener)
    }

    fun getDateToDayToDo():ArrayList<ItemDate>{
        var DateTodayToDo = ArrayList<ItemDate>()
        for(i in 0..todayToDo.size-1){
            if(currentDate.equals(todayToDo[i].startDate)){
                DateTodayToDo.add(todayToDo[i])
            }
        }
        return DateTodayToDo
    }

    fun setDateToDayToDo(AddItem:ItemDate){
        todayToDo.add(AddItem)
        Log.i("AllItemData","setDateToDayToDo.nowTodayToDoIndex"+nowTodayToDoIndex)
        val nowTodayToDo = database.getReference("user/todayToDo/")
        if(!nowTodayToDoIndex.equals("")){
            val nextTodayToDoIndex = (nowTodayToDoIndex.toInt()+1).toString()
            nowTodayToDo.child(nextTodayToDoIndex).setValue(AddItem)
            Log.i("AllItemData"," nowTodayToDo")
        }

    }

    fun getDateHabitToDo():ArrayList<ItemDate>{
        var DateHabitToDo = ArrayList<ItemDate>()
        for(i in 0..habitToDo.size-1){
            if(currentDate.equals(habitToDo[i].startDate)){
                DateHabitToDo.add(habitToDo[i])
            }
        }
        return DateHabitToDo
    }


    fun getDateScheduleToDo():ArrayList<ItemDate>{
        var DateScheduleToDo = ArrayList<ItemDate>()
        for(i in 0..scheduleToDo.size-1){
            if(currentDate.equals(scheduleToDo[i].startDate)){
                DateScheduleToDo.add(scheduleToDo[i])
            }
        }
        return DateScheduleToDo
    }


}
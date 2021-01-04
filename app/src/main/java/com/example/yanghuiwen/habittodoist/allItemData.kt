package com.example.yanghuiwen.habittodoist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Comment

import java.util.ArrayList

object AllItemData {

    val database = Firebase.database
    private lateinit var todayToDoReference: DatabaseReference
    var todayToDoMap = sortedMapOf<Int, ItemDate?>()
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
                    todayToDoMap.put(nowTodayToDoIndex.toInt(),itemDate)
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
//        val childEventListener = object : ChildEventListener {
//            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
//               // Log.d(TAG, "onChildAdded:" + dataSnapshot.key!!)
//
//                // A new comment has been added, add it to the displayed list
//                val comment = dataSnapshot.getValue<Comment>()
//
//                // ...
//            }
//
//            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
//               // Log.d(TAG, "onChildChanged: ${dataSnapshot.key}")
//
//                // A comment has changed, use the key to determine if we are displaying this
//                // comment and if so displayed the changed comment.
//                val newComment = dataSnapshot.getValue<Comment>()
//                val commentKey = dataSnapshot.key
//
//                // ...
//            }
//
//            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
//               // Log.d(TAG, "onChildRemoved:" + dataSnapshot.key!!)
//
//                // A comment has changed, use the key to determine if we are displaying this
//                // comment and if so remove it.
//                val commentKey = dataSnapshot.key
//
//                // ...
//            }
//
//            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
//             //   Log.d(TAG, "onChildMoved:" + dataSnapshot.key!!)
//
//                // A comment has changed position, use the key to determine if we are
//                // displaying this comment and if so move it.
//                val movedComment = dataSnapshot.getValue<Comment>()
//                val commentKey = dataSnapshot.key
//
//                // ...
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//             //   Log.w(TAG, "postComments:onCancelled", databaseError.toException())
//              //  Toast.makeText(context, "Failed to load comments.",
//                 //       Toast.LENGTH_SHORT).show()
//            }
//        }
//        todayToDoReference.addChildEventListener(childEventListener)
    }

    fun modifyDateToDayToDo(modifyIndex:Int,modifyItem:ItemDate){
        val nowTodayToDo = database.getReference("user/todayToDo/")
        nowTodayToDo.child(modifyIndex.toString()).setValue(modifyItem)

    }

    fun deleteDateToDayToDo(deleteIndex:Int){
        val nowTodayToDo = database.getReference("user/todayToDo/")
        nowTodayToDo.child(deleteIndex.toString()).removeValue()
        AllItemData.todayToDoMap.remove(deleteIndex)

    }

    fun getDateToDayToDo():ArrayList<ItemDate>{
        var DateTodayToDo = sortedMapOf<Int, ItemDate?>()
        var habitToDo = ArrayList<ItemDate>()
        for((key,itemDate) in todayToDoMap){
            if(currentDate.equals(itemDate?.startDate)){
                if (itemDate != null) {
                    habitToDo.add(itemDate)
                }
            }
        }

//        for((key,itemDate) in DateTodayToDo){
//            if (itemDate != null) {
//                habitToDo.add(itemDate)
//            }
//        }
        return habitToDo
    }

    fun setDateToDayToDo(AddItem:ItemDate){
        todayToDo.add(AddItem)
        Log.i("AllItemData","setDateToDayToDo.nowTodayToDoIndex"+nowTodayToDoIndex)
        val nowTodayToDo = database.getReference("user/todayToDo/")
        if(!nowTodayToDoIndex.equals("")){
            val nextTodayToDoIndex = (nowTodayToDoIndex.toInt()+1).toString()
            //上傳firebase
            nowTodayToDo.child(nextTodayToDoIndex).setValue(AddItem)
            Log.i("AllItemData"," nowTodayToDo")
            nowTodayToDoIndex = (nowTodayToDoIndex.toInt()+1).toString()
            //存本地
            todayToDoMap.put(nextTodayToDoIndex.toInt(),AddItem)
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
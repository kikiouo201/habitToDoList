package com.example.yanghuiwen.habittodoist

import android.util.Log
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

import java.util.ArrayList

object AllItemData {




    val database = Firebase.database
    private lateinit var allItemReference: DatabaseReference
    private lateinit var todayToDoItemReference: DatabaseReference
    private lateinit var notTimeToDoReference: DatabaseReference

    var allToDoMap = sortedMapOf<Int, ItemDate?>()

    val todayToDoItem = mutableSetOf<String>()
    var nowTodayToDoIndex = ""
    var lastallItemIndex =""
    var todayToDo = ArrayList<ItemDate>()


    val habitToDo = ArrayList<ItemDate>()
    var nowHabitToDoIndex = ""
    val scheduleToDo = ArrayList<ItemDate>()
    var nowScheduleToDoIndex = ""
    var notTimToDo = ArrayList<ItemDate>()
    val notTimeToDoMap = sortedMapOf<Int, ItemDate?>()
    var nowNotTimeToDoIndex = ""
    var currentDate ="2020-02-07"
    var currentWeekIndex = 1
    fun getFirebaseDate(){

        // allItem 所有單項
        allItemReference = database.getReference("user/allItem/")
        val allItemPostListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ItemSnapshot in dataSnapshot.children) {
                    var itemDate= ItemSnapshot.getValue<ItemDate>()
                    lastallItemIndex = ItemSnapshot.key.toString()
                    allToDoMap.put(lastallItemIndex.toInt(),itemDate)
                }
//                Log.i("AllItemData","allToDoMap="+allToDoMap)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                // Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }
        allItemReference.addValueEventListener(allItemPostListener)

        // todayToDoItem 所有單項
        todayToDoItemReference = database.getReference("user/todayToDoItem/")
        val todayToDoItemPostListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (ItemSnapshot in dataSnapshot.children) {
                    var itemDate= ItemSnapshot.getValue<String>()
                    if (itemDate != null) {
                        todayToDoItem.add(itemDate)
                    }
                }
//                Log.i("AllItemData","allToDoMap="+allToDoMap)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                // Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }
        todayToDoItemReference.addValueEventListener(todayToDoItemPostListener)

        // notTimeToDo
        notTimeToDoReference = database.getReference("user/notTimeToDo/")
        val notTimePostListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ItemSnapshot in dataSnapshot.children) {
                    var itemDate= ItemSnapshot.getValue<ItemDate>()
                    nowNotTimeToDoIndex = ItemSnapshot.key.toString()
                    notTimeToDoMap.put(nowNotTimeToDoIndex.toInt(),itemDate)
                    Log.i("AllItemData"," notTimeToDo"+  nowNotTimeToDoIndex)
                    Log.i("AllItemData","itemData.name"+itemDate?.name)
//                    if (itemDate != null) {
//                        notTimeToDo.add(itemDate)
//                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                // Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }
        notTimeToDoReference.addValueEventListener(notTimePostListener)



    }

    // 上傳 AllItem 到firebase
    fun  setAllItem(AddItem:ItemDate):Int{
        val allItem = database.getReference("user/allItem/")

        if(!lastallItemIndex.equals("")){
            lastallItemIndex = (lastallItemIndex.toInt()+1).toString()
        }else{
            lastallItemIndex = "1"
        }
        //上傳firebase
        allItem.child(lastallItemIndex).setValue(AddItem)
        //存本地
        allToDoMap.put(lastallItemIndex.toInt(),AddItem)
        return lastallItemIndex.toInt()
    }
    fun  deleteAllItem(deleteIndex:Int){
        val allItem = database.getReference("user/allItem/")
        //移除firebase上的項目
        allItem.child(deleteIndex.toString()).removeValue()
        allToDoMap.remove(deleteIndex)
    }
    fun  modifyAllItem(modifyIndex:Int,modifyItem:ItemDate){
        val allItem = database.getReference("user/allItem/")
        //修改firebase上的項目
        allItem.child(modifyIndex.toString()).setValue(modifyItem)
        allToDoMap.remove(modifyIndex)
    }

    fun modifyNotTimeToDo(modifyIndex:Int,modifyItem:ItemDate){
        val notTimeToDo = database.getReference("user/notTimeToDo/")
        notTimeToDo.child(modifyIndex.toString()).setValue(modifyItem)

    }
    fun deleteNotTimeToDo(deleteIndex:Int){
        val notTimeToDo = database.getReference("user/notTimeToDo/")
        notTimeToDo.child(deleteIndex.toString()).removeValue()
        notTimeToDoMap.remove(deleteIndex)

    }
    fun setNotTimeToDo(AddItem:ItemDate) {
        //todayToDo.add(AddItem)
       // Log.i("AllItemData","setDateToDayToDo.nowTodayToDoIndex"+nowTodayToDoIndex)
        val notTimeToDo = database.getReference("user/notTimeToDo/")
        if(!nowNotTimeToDoIndex.equals("")){
            val nextTimeToDoIndex = (nowNotTimeToDoIndex.toInt()+1).toString()
            //上傳firebase
            notTimeToDo.child(nextTimeToDoIndex).setValue(AddItem)
           // Log.i("AllItemData","notTimeToDo")
            nowNotTimeToDoIndex = (nowNotTimeToDoIndex.toInt()+1).toString()
            //存本地
            notTimeToDoMap.put(nextTimeToDoIndex.toInt(),AddItem)
        }else{
            val nextTimeToDoIndex = "1"
            //上傳firebase
            notTimeToDo.child(nextTimeToDoIndex).setValue(AddItem)
            // Log.i("AllItemData"," nowTodayToDo")
            nowNotTimeToDoIndex =  "1"
            //存本地
            notTimeToDoMap.put(nextTimeToDoIndex.toInt(),AddItem)
        }
    }
    fun getNotTimeToDo():ArrayList<ItemDate> {

        var nowNotDateToDo = ArrayList<ItemDate>()
        for((key,itemDate) in notTimeToDoMap){
          // Log.i("AllItemData","itemDate?.name${itemDate?.name}")
            if (itemDate != null) {
                nowNotDateToDo.add(itemDate)
            }
        }
      // Log.i("AllItemData","nowNotTimeToDo${nowNotDateToDo}")
        return  nowNotDateToDo
    }

    fun modifyDateToDayToDo(modifyIndex:Int, modifyItem:ItemDate){
        modifyAllItem(modifyIndex,modifyItem)
        Log.i("AllItemData","modify todayToDoItem=${todayToDoItem}")
    }
    fun deleteDateToDayToDo(deleteIndex:Int){
        deleteAllItem(deleteIndex)
        val todayToDo = database.getReference("user/todayToDoItem/")
        todayToDo.child(deleteIndex.toString()).removeValue()
        todayToDoItem.remove(deleteIndex.toString())
    }
    fun getDateToDayToDo():ArrayList<ItemDate>{

        todayToDo = ArrayList<ItemDate>()

        for (itemIndex in todayToDoItem){
            var nowItemDate = allToDoMap.get(itemIndex.toInt())
//            Log.i("AllItemData","nowItemDate="+nowItemDate)
            if(currentDate.equals(nowItemDate?.startDate)){
                if (nowItemDate != null) {
                    todayToDo.add(nowItemDate)
                }
            }
        }

        return todayToDo
    }
    fun setDateToDayToDo(AddItem:ItemDate){
        var addItemIndex= setAllItem(AddItem)
        todayToDoItem.add(addItemIndex.toString())

        val todayToDo = database.getReference("user/todayToDoItem/")
        if(!nowTodayToDoIndex.equals("")){
            val nextTodayToDoIndex = (nowTodayToDoIndex.toInt()+1).toString()
            //上傳firebase
            todayToDo.child(addItemIndex.toString()).setValue(addItemIndex.toString())
           // Log.i("AllItemData"," nowTodayToDo")
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
package com.example.yanghuiwen.habittodoist

import android.util.Log
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

object AllItemData {




    val database = Firebase.database
    private lateinit var allItemReference: DatabaseReference
    private lateinit var todayToDoItemReference: DatabaseReference
    private lateinit var singleItemReference: DatabaseReference

    var allToDoMap = sortedMapOf<Int, ItemDate?>()

    // 今天事項
    val todayToDoItem = mutableSetOf<String>()
    var nowTodayToDoIndex = ""
    var lastallItemIndex =""
    var todayToDo = ArrayList<ItemDate>()


    //單項
    var endSingleItemMap = sortedMapOf<String, ArrayList<Int>>()
    var notEndSingleItemMap = sortedMapOf<String, ArrayList<Int>>()


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
                    val itemIndex = ItemSnapshot.key.toString()
                    lastallItemIndex = itemIndex
                    allToDoMap.put(lastallItemIndex.toInt(),itemDate)


                    if (itemDate != null) {
                        if(itemDate.IsEndItem){
                            //單項項目完成
                           if(endSingleItemMap.get("all") != null){
                               val endSingleItem =endSingleItemMap.get("all")
                               endSingleItem?.add(itemIndex.toInt())
                           }else{
                               val endSingleItem =ArrayList<Int>()
                               endSingleItem?.add(itemIndex.toInt())
                               endSingleItemMap.put("all",endSingleItem)
                           }
                        }else{
                            //單項項目未完成


                            if(notEndSingleItemMap.get("all") != null){
                                val notEndSingleItem =notEndSingleItemMap.get("all")
                                notEndSingleItem?.add(itemIndex.toInt())
                             //   Log.i("AllItemData","notEndSingleItem${notEndSingleItem}")
                            }else{

                                val notEndSingleItem =ArrayList<Int>()
                                notEndSingleItem?.add(itemIndex.toInt())
                                notEndSingleItemMap.put("all",notEndSingleItem)
                            }
                        }
                       // Log.i("AllItemData","endSingleItemMap${endSingleItemMap}")
                       // Log.i("AllItemData","notEndSingleItemMap.size${notEndSingleItemMap.size}")

                    }
                }

               for ((key,itemIndexs)in notEndSingleItemMap){
                   notEndSingleItemMap[key] =deleteDuplicateArrayList(itemIndexs)
                }
                for ((key,itemIndexs)in endSingleItemMap){
                    endSingleItemMap[key] =deleteDuplicateArrayList(itemIndexs)
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




    }
    //刪除重複項目
    fun deleteDuplicateArrayList(duplicateItems:ArrayList<Int>):ArrayList<Int>{
        val toDoItemIndex = mutableSetOf<Int>()
        for (itemIndex in duplicateItems){
            toDoItemIndex.add(itemIndex)
        }
        val f = ArrayList<Int>()

        for (itemIndex in  toDoItemIndex){
            f.add(itemIndex)
        }
        return f
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

    fun modifySingleItem(modifyIndex:Int, modifyItem:ItemDate){
      //  Log.i("AllItemData","modifySingleItem")
        modifyAllItem(modifyIndex,modifyItem)
//        val notTimeToDo = database.getReference("user/notTimeToDo/")
//        notTimeToDo.child(modifyIndex.toString()).setValue(modifyItem)

    }
    fun deleteSingleItem(deleteIndex:Int){
//        val itemDate=allToDoMap[deleteIndex]
//        val projectName = itemDate?.project
//        val projectItems = notEndSingleItemMap.get(projectName)
//        if (projectItems != null) {
//            projectItems.remove(deleteIndex)
//        }
      //  Log.i("AllItemData","deleteSingleItem")
        deleteAllItem(deleteIndex)
    }
    fun setSingleItem(AddItem:ItemDate) {
      //  Log.i("AllItemData","setSingleItem=${todayToDoItem}")
        var addItemIndex= setAllItem(AddItem)
    }
    fun getProjectSingleItem():Map<String, ArrayList<ItemDate>> {

        var singleItemMap = sortedMapOf<String, ArrayList<ItemDate>>()

        for ((key,itemIndexs)in notEndSingleItemMap){
           val itemDates = ArrayList<ItemDate>()
            for (itemIndex in itemIndexs) {
                var nowItemDate = allToDoMap.get(itemIndex.toInt())
                if (nowItemDate != null) {
                    if(singleItemMap.get(nowItemDate.project) != null){
                        val endSingleItem =singleItemMap.get(nowItemDate.project)
                        endSingleItem?.add(nowItemDate)
                    }else{
                        val endSingleItem =ArrayList<ItemDate>()
                        endSingleItem?.add(nowItemDate)
                        singleItemMap.put(nowItemDate.project,endSingleItem)
                    }
                    itemDates.add(nowItemDate)
                }
            }

        }

       //Log.i("AllItemData"," getSingleItem notEndSingleItemMap${notEndSingleItemMap}")
        return singleItemMap
    }

    fun modifyDateToDayToDo(modifyIndex:Int, modifyItem:ItemDate){
        modifyAllItem(modifyIndex,modifyItem)
       // Log.i("AllItemData","modify todayToDoItem=${todayToDoItem}")
    }
    fun deleteDateToDayToDo(deleteIndex:Int){
        deleteAllItem(deleteIndex)
        val todayToDo = database.getReference("user/todayToDoItem/")
        todayToDo.child(deleteIndex.toString()).removeValue()
        todayToDoItem.remove(deleteIndex.toString())
     //   Log.i("AllItemData","delete todayToDoItem=${todayToDoItem}")
    }
    fun getDateToDayToDo():ArrayList<ItemDate>{

        todayToDo = ArrayList<ItemDate>()

        for (itemIndex in todayToDoItem){
            var nowItemDate = allToDoMap.get(itemIndex.toInt())
          // Log.i("AllItemData","nowItemDate="+nowItemDate)
            if(currentDate.equals(nowItemDate?.startDate)){
                if (nowItemDate != null) {
                    todayToDo.add(nowItemDate)
                }
            }
        }
      //  Log.i("AllItemData","todayToDo="+todayToDo)
        return todayToDo
    }
    fun setDateToDayToDo(AddItem:ItemDate){
        var addItemIndex= setAllItem(AddItem)
        todayToDoItem.add(addItemIndex.toString())

        val todayToDo = database.getReference("user/todayToDoItem/")
//        if(!nowTodayToDoIndex.equals("")){
//            val nextTodayToDoIndex = (nowTodayToDoIndex.toInt()+1).toString()
            //上傳firebase
            todayToDo.child(addItemIndex.toString()).setValue(addItemIndex.toString())
        //    Log.i("AllItemData","setDateToDayToDo")
//        }

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



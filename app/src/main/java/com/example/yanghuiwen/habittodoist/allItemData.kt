package com.example.yanghuiwen.habittodoist

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.collections.ArrayList

object AllItemData {


    private const val TAG = "AllItemData"
    val database = Firebase.database
    private lateinit var allItemReference: DatabaseReference
    private lateinit var todayToDoItemReference: DatabaseReference
    private lateinit var diaryItemReference: DatabaseReference
    private lateinit var habitToDoItemReference: DatabaseReference
    private lateinit var projectItemReference: DatabaseReference


    lateinit var auth: FirebaseAuth

    var allToDoMap = sortedMapOf<Int, ItemDate?>()
    var allActivityMap = sortedMapOf<Int, ItemDate?>()
    var allHabitToDoMap = sortedMapOf<Int, HabitDate?>()
    var allProjectMap = sortedMapOf<Int, String>()
    var allDiaryMap = sortedMapOf<String, String>()

    // 今天事項
    val todayToDoItem = mutableSetOf<String>()
    var lastallItemIndex =""
    var lastallHabitIndex = ""
    var todayToDo = ArrayList<ItemDate>()


    // 單項
    var endSingleItemMap = sortedMapOf<String, ArrayList<Int>>()
    var notEndSingleItemMap = sortedMapOf<String, ArrayList<Int>>()



    // 無 週 月 年 事項
    val noDateToDoItem = mutableSetOf<String>()
    val weekToDoItem = mutableSetOf<String>()
    val monthToDoItem = mutableSetOf<String>()
    val yearToDoItem = mutableSetOf<String>()


    var currentDate ="2020-02-07"
    var currentWeekIndex = 1
    @RequiresApi(Build.VERSION_CODES.O)
    fun getFirebaseDate(){

        val timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")



        // allItem 所有單項
        allItemReference = database.getReference("user/allItem/")
        val allItemPostListener = object : ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDataChange(dataSnapshot: DataSnapshot) {


                for (ItemSnapshot in dataSnapshot.children) {
                    var itemDate= ItemSnapshot.getValue<ItemDate>()
                    val itemIndex = ItemSnapshot.key.toString()
                    lastallItemIndex = itemIndex
                    allToDoMap.put(lastallItemIndex.toInt(),itemDate)





                    if (itemDate != null) {

                        //檢查項目有沒有過期
                        if (!itemDate.endDate.equals("無")){
                            val itemStartDateFormatter = LocalDateTime.parse(itemDate.endDate+" 00:00:00",timeFormatter)
                            val currentDateFormatter = LocalDateTime.now()
                            val dateDifference = ChronoUnit.DAYS.between(itemStartDateFormatter, currentDateFormatter).toInt()
                            if(dateDifference>0){
                                itemDate.IsProhibitItem = true
                               allItemReference.child(itemIndex).setValue(itemDate)
                            }

                        }

                        //單項項目沒有被禁止
                        if(!itemDate.IsProhibitItem){
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





        // habitToDoItem 所有單項
        habitToDoItemReference = database.getReference("user/allHabit/")
        val habitToDoItemPostListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (ItemSnapshot in dataSnapshot.children) {
                    var habitDate= ItemSnapshot.getValue<HabitDate>()
                    val itemIndex = ItemSnapshot.key.toString()

                    lastallHabitIndex = itemIndex


                    if (habitDate != null) {

                        //檢查項目有沒有過期
                        if (!habitDate.endDate.equals("無")){
                            val itemStartDateFormatter = LocalDateTime.parse(habitDate.endDate+" 00:00:00",timeFormatter)
                            val currentDateFormatter = LocalDateTime.now()
                            val dateDifference = ChronoUnit.DAYS.between(itemStartDateFormatter, currentDateFormatter).toInt()
                            if(dateDifference>0){
                                habitDate.IsProhibitItem = true
                                habitToDoItemReference.child(itemIndex).setValue(habitDate)
                            }
                        }


                        if (!habitDate.IsProhibitItem){
                            allHabitToDoMap.put(lastallHabitIndex.toInt(),habitDate)
                        }

                    }
                   // Log.i(TAG," HabitToDoMap lastallItemIndex="+lastallHabitIndex.toInt())
                }
               // Log.i(TAG," first allHabitToDoMap="+allHabitToDoMap)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                // Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }
        habitToDoItemReference.addValueEventListener(habitToDoItemPostListener)


        // projectItem 所有專案
        projectItemReference = database.getReference("user/allProject/")
        val projectItemPostListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (ItemSnapshot in dataSnapshot.children) {
                    var itemDate= ItemSnapshot.getValue<String>()
                    val itemIndex = ItemSnapshot.key?.toInt()
                    if (itemDate != null) {
                        allProjectMap.put(itemIndex,itemDate)
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
        projectItemReference.addValueEventListener(projectItemPostListener)

        // DiaryItem 所有專案
        diaryItemReference = database.getReference("user/allDiary/")

        val diaryItemPostListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (ItemSnapshot in dataSnapshot.children) {
                    var itemDate= ItemSnapshot.getValue<String>()
                    val itemIndex = ItemSnapshot.key
                    if (itemDate != null) {
                        allDiaryMap.put(itemIndex,itemDate)
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
        diaryItemReference.addValueEventListener(diaryItemPostListener)

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
        //allToDoMap.remove(modifyIndex)
    }

    // 上傳 AllHabit 到firebase
    fun  setAllHabit(AddHabit:HabitDate):Int{
        val allHabit = database.getReference("user/allHabit/")

        if(!lastallHabitIndex.equals("")){
            lastallHabitIndex = (lastallHabitIndex.toInt()+1).toString()
        }else{
            lastallHabitIndex = "1"
        }
        //上傳firebase
        allHabit.child(lastallHabitIndex).setValue(AddHabit)
        //存本地
       // allToDoMap.put(lastallHabitIndex.toInt(),AddHabit)
        return lastallHabitIndex.toInt()
    }
    fun  modifyAllHabit(modifyIndex:Int,modifyItem:HabitDate){
        val allItem = database.getReference("user/allHabit/")
        //修改firebase上的項目
        allItem.child(modifyIndex.toString()).setValue(modifyItem)
        //allToDoMap.remove(modifyIndex)
    }
    fun deleteAllHabit(deleteIndex:Int){
        val habitToDo = database.getReference("user/allHabit/")
        habitToDo.child(deleteIndex.toString()).removeValue()
        allHabitToDoMap.remove(deleteIndex)
    }

    // 上傳 AllHabit 到firebase
    private fun  setAllProject(AddProject:String){
        val allHabit = database.getReference("user/allProject/")

        //上傳firebase
        if(allProjectMap.size != 0){
            allHabit.child((allProjectMap.size+1).toString()).setValue(AddProject)
        }else{
            allHabit.child("1").setValue(AddProject)
        }

        //存本地
        allProjectMap.put((allProjectMap.size+1),AddProject)
    }

    // 上傳 AllDiary 到firebase
    private fun setAllDiary(diaryDate :String,AddDiary:String){
        val allHabit = database.getReference("user/allDiary/")

        //上傳firebase
        allHabit.child(diaryDate).setValue(AddDiary)

        //存本地
//        allProjectMap.put((allProjectMap.size+1),AddDiary)
    }

    fun setDiary(diaryDate:String, AddDiary: String){
       setAllDiary(diaryDate,AddDiary)
    }
    fun getDiary(diaryDate:String):String{
        var diary = ""
        if(allDiaryMap[diaryDate] !=null){
            diary = allDiaryMap[diaryDate].toString()
        }
        return diary
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
    fun getImportantSingleItem():Map<String, Map<String, ItemDate>> {

        var singleItemMap = sortedMapOf<String,Map<String, ItemDate>>()
        var endSingleItem = sortedMapOf<String, ItemDate>()
        for ((key,itemIndexs)in notEndSingleItemMap){
            val itemDates = ArrayList<ItemDate>()
            for (itemIndex in itemIndexs) {
                var nowItemDate = allToDoMap.get(itemIndex.toInt())
                if (nowItemDate != null && !nowItemDate.isHabit) {
                    if(singleItemMap.get(nowItemDate.important.toString()) != null){
                        endSingleItem = singleItemMap.get(nowItemDate.important.toString()) as SortedMap<String, ItemDate>
                        endSingleItem.put(itemIndex.toString(),nowItemDate)
                    }else{
                        endSingleItem = sortedMapOf<String, ItemDate>()
                        endSingleItem.put(itemIndex.toString(),nowItemDate)
                        singleItemMap.put(nowItemDate.important.toString(),endSingleItem)
                    }
                    itemDates.add(nowItemDate)
                }
            }

        }

        //Log.i("AllItemData"," getSingleItem notEndSingleItemMap${notEndSingleItemMap}")
        return singleItemMap
    }
    fun getTimeSingleItem():Map<String, Map<String, ItemDate>> {

        var singleItemMap = sortedMapOf<String,Map<String, ItemDate>>()
        var endSingleItem = sortedMapOf<String, ItemDate>()

        for ((key,itemIndexs)in notEndSingleItemMap){
            val itemDates = ArrayList<ItemDate>()
            for (itemIndex in itemIndexs) {
                var nowItemDate = allToDoMap.get(itemIndex.toInt())
                if (nowItemDate != null && !nowItemDate.isHabit) {
                    if(singleItemMap.get(nowItemDate.startDate) != null){
                        endSingleItem =singleItemMap.get(nowItemDate.startDate) as SortedMap<String, ItemDate>
                        endSingleItem.put(itemIndex.toString(),nowItemDate)
                    }else{
                        endSingleItem =sortedMapOf<String, ItemDate>()
                        endSingleItem.put(itemIndex.toString(),nowItemDate)
                        singleItemMap.put(nowItemDate.startDate,endSingleItem)
                    }
                    itemDates.add(nowItemDate)
                }
            }

        }

        return singleItemMap
    }

//    fun convertDate(d: String): String {
//        if (d.equals("無")){
//            return "無"
//        }else{
//            val array = d.split("-")
//            return array[0] + array[1] + array[2]
//        }
//    }

    fun getProjectHabitItem():Map<String, ArrayList<HabitDate>> {

        var habitToDoMap = sortedMapOf<String, ArrayList<HabitDate>>()
       // Log.i("AllItemData","allHabitToDoMap=${allHabitToDoMap}")
        for ((key,itemIndexs)in allHabitToDoMap){
            val HabitDates = ArrayList<HabitDate>()


                var nowItemDate = itemIndexs
                if (nowItemDate != null) {
                    if(habitToDoMap.get(nowItemDate.project) != null){
                        val endSingleItem =habitToDoMap.get(nowItemDate.project)
                        endSingleItem?.add(nowItemDate)
                    }else{
                        val endHabitItem =ArrayList<HabitDate>()
                        endHabitItem?.add(nowItemDate)
                        habitToDoMap.put(nowItemDate.project,endHabitItem)
                    }
                    HabitDates.add(nowItemDate)
                }


        }
        //Log.i("AllItemData"," getSingleItem notEndSingleItemMap${notEndSingleItemMap}")
        return habitToDoMap
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
    fun getDateToDayToDo():Map<String, ItemDate>{

        val todayToDo = sortedMapOf<String, ItemDate>()

        for (itemIndex in todayToDoItem){
            var nowItemDate = allToDoMap.get(itemIndex.toInt())
          // Log.i("AllItemData","nowItemDate="+nowItemDate)
            if(currentDate.equals(nowItemDate?.startDate)){
                if (nowItemDate != null && !nowItemDate.isHabit) {
                    todayToDo.put(itemIndex,nowItemDate)
                }
            }
        }

        return todayToDo
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getIntervalDateToDo(dateCategory:String):Map<String, ItemDate>{
       var  intervalDateToDo = sortedMapOf<String, ItemDate>()

       // Log.i(TAG,"dateCategory${dateCategory}")
        when(dateCategory){
            "年" ->{
                for ((key,itemDate) in allToDoMap){

                    // Log.i("AllItemData","nowItemDate="+nowItemDate)
                    if(itemDate?.timeType==4){
                        val mCurrentDate = LocalDate.parse(currentDate)
                        val mNowItemDate = LocalDate.parse(itemDate?.startDate)
                        if(mCurrentDate.year.equals(mNowItemDate.year)){
                            if (itemDate != null && !itemDate.isHabit) {
                                intervalDateToDo.put(key.toString(),itemDate)
                            }
                        }
                    }

                }
            }
            "月" ->{
                for ((key,itemDate) in allToDoMap){
                    if(itemDate?.timeType==3){
                        val mCurrentDate = LocalDate.parse(currentDate)
                        val mNowItemDate = LocalDate.parse(itemDate?.startDate)
                        if(mCurrentDate.month.equals(mNowItemDate.month)){
                            if (itemDate != null && !itemDate.isHabit) {
                                intervalDateToDo.put(key.toString(),itemDate)
                            }
                        }
                    }

                }
            }
            "週" ->{
                for ((key,itemDate) in allToDoMap){
                    if(itemDate?.timeType==2){
                        var mCurrentDate = LocalDate.parse(currentDate)
                        val week = mCurrentDate.dayOfWeek.value
                        mCurrentDate = mCurrentDate.minusDays((week-1).toLong())
                        val mNowItemDate = LocalDate.parse(itemDate?.startDate)
                        if(mCurrentDate.equals(mNowItemDate)){
                            if (itemDate != null && !itemDate.isHabit) {
                                intervalDateToDo.put(key.toString(),itemDate)
                            }
                        }
                    }

                }
            }
        }



        return intervalDateToDo
    }

    fun getIntervalDateToDayToDo(intervalDates: ArrayList<String>):ArrayList<ItemDate>{

        todayToDo = ArrayList<ItemDate>()

        for (itemIndex in todayToDoItem){
            var nowItemDate = allToDoMap.get(itemIndex.toInt())
             //Log.i(TAG,"nowItemDate="+nowItemDate)
            for (intervalDate in intervalDates) {
                if (intervalDate.equals(nowItemDate?.startDate)) {
                    if (nowItemDate != null && !nowItemDate.isHabit) {

                        todayToDo.add(nowItemDate)
                    }
                }
            }
        }

        return todayToDo
    }
    fun setDateToDayToDo(AddItem:ItemDate):Int{
        var addItemIndex= setAllItem(AddItem)
        todayToDoItem.add(addItemIndex.toString())

        val todayToDo = database.getReference("user/todayToDoItem/")
//        if(!nowTodayToDoIndex.equals("")){
//            val nextTodayToDoIndex = (nowTodayToDoIndex.toInt()+1).toString()
            //上傳firebase
            todayToDo.child(addItemIndex.toString()).setValue(addItemIndex.toString())
//        }
        return addItemIndex
    }

    fun setIntervalDateToDayToDo(AddItem:ItemDate,dateCategory:String):Int{

//        when(dateCategory){
//            "年" ->{
//
//            }
//            "月" ->{
//
//            }
//            "週" ->{
//
//            }
//        }

        var addItemIndex= setAllItem(AddItem)
        todayToDoItem.add(addItemIndex.toString())

        val todayToDo = database.getReference("user/todayToDoItem/")
//        if(!nowTodayToDoIndex.equals("")){
//            val nextTodayToDoIndex = (nowTodayToDoIndex.toInt()+1).toString()
        //上傳firebase
        todayToDo.child(addItemIndex.toString()).setValue(addItemIndex.toString())
//        }
        return addItemIndex
    }

    fun getAllHabitToDo(): Map<Int,HabitDate>{

        var habitToDoMap = sortedMapOf<Int, HabitDate>()
        // Log.i("AllItemData","allHabitToDoMap=${allHabitToDoMap}")

        for ((key,itemIndexs)in allHabitToDoMap){
            habitToDoMap.put(key,itemIndexs)
        }
        //Log.i("AllItemData"," getSingleItem notEndSingleItemMap${notEndSingleItemMap}")
        return habitToDoMap
    }
    fun getDateHabitToDo():Map<String, HabitDate>{

         var habitToDo = sortedMapOf<String, HabitDate>()

        for ((key,itemIndexs)in allHabitToDoMap){
            for (date in itemIndexs?.allDate!!){

                 if(currentDate.equals(date)){
                        habitToDo.put(key.toString(),itemIndexs)
                }

            }
        }
        return habitToDo

    }
    fun getIntervalDateHabitToDo(intervalDates: ArrayList<String>):ArrayList<HabitDate>{

        var habitToDo = ArrayList<HabitDate>()

        for ((key,itemIndexs)in allHabitToDoMap){
            for (date in itemIndexs?.allDate!!){

                for (intervalDate in intervalDates) {
                    if (intervalDate.equals(date)) {
                        //      Log.i("AllItemData","nowItemDate="+nowItemDate)

                            habitToDo.add(itemIndexs)

                    }
                }

            }
        }
        return habitToDo

    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun setDateHabitToDo(AddHabit:HabitDate){
        //拆成一個個 item
        //更新addItemIndex
        when(AddHabit.timeType){
            "日" -> {
                val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                val mStart = LocalDateTime.parse(AddHabit.startDate+" 00:00:00",timeFormatter)
                val mEnd = LocalDateTime.parse(AddHabit.endDate+" 00:00:00",timeFormatter)

                val difference = ChronoUnit.DAYS.between(mStart, mEnd).toInt()
                val repeatNum = AddHabit.repeatCycle[0].toInt()
                for (i in 0..difference step repeatNum){
                    AddHabit.allDate.add( mStart.plusDays(i.toLong()).format(dateFormatter))
                    AddHabit.notEndItemList.add(mStart.plusDays(i.toLong()).format(dateFormatter))
                }
            }
            "週" -> {
                val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                val mStart = LocalDateTime.parse(AddHabit.startDate+" 00:00:00",timeFormatter)
                val mEnd = LocalDateTime.parse(AddHabit.endDate+" 00:00:00",timeFormatter)

                val difference = ChronoUnit.DAYS.between(mStart, mEnd).toInt()

                for (i in 0..difference){
                    for (j in 0..AddHabit.repeatCycle.size-1){
                        val mDate = mStart.plusDays(i.toLong())
                        if(mDate.dayOfWeek.value == AddHabit.repeatCycle[j].toInt()){
                            AddHabit.allDate.add( mStart.plusDays(i.toLong()).format(dateFormatter))
                            AddHabit.notEndItemList.add(mStart.plusDays(i.toLong()).format(dateFormatter))
                        }
                    }
                }

            }
            "月" -> {
                val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                val mStart = LocalDateTime.parse(AddHabit.startDate+" 00:00:00",timeFormatter)
                val mEnd = LocalDateTime.parse(AddHabit.endDate+" 00:00:00",timeFormatter)

                val difference = ChronoUnit.DAYS.between(mStart, mEnd).toInt()

                for (i in 0..difference){
                    for (j in 0..AddHabit.repeatCycle.size-1){
                        val mDate = mStart.plusDays(i.toLong())
                        if(mDate.dayOfMonth == AddHabit.repeatCycle[j].toInt()){
                            AddHabit.allDate.add( mStart.plusDays(i.toLong()).format(dateFormatter))
                            AddHabit.notEndItemList.add(mStart.plusDays(i.toLong()).format(dateFormatter))
                        }
                    }
                }
            }
            "年" -> {

                val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                val mStart = LocalDateTime.parse(AddHabit.startDate+" 00:00:00",timeFormatter)
                val mEnd = LocalDateTime.parse(AddHabit.endDate+" 00:00:00",timeFormatter)

                val difference = ChronoUnit.DAYS.between(mStart, mEnd).toInt()

                for (i in 0..difference){
                    for (j in 0..AddHabit.repeatCycle.size-1){
                        val mDate = mStart.plusDays(i.toLong())
                        var chooseTime =""
                        if(mDate.monthValue<10){
                            chooseTime +="0${mDate.monthValue}"
                        }else{
                            chooseTime +="${mDate.monthValue}"
                        }
                        if (mDate.dayOfMonth<10){
                            chooseTime +="-0${mDate.dayOfMonth}"
                        }else{
                            chooseTime +="-${mDate.dayOfMonth}"
                        }
                        if(chooseTime == AddHabit.repeatCycle[j]){
                            AddHabit.allDate.add( mStart.plusDays(i.toLong()).format(dateFormatter))
                            AddHabit.notEndItemList.add(mStart.plusDays(i.toLong()).format(dateFormatter))
                        }
                    }
                }
            }
        }
        //上傳到 firebase
        var addHabitIndex= setAllHabit(AddHabit)
      //  habitToDoItem.add(addHabitIndex.toString())


//        val habitToDo = database.getReference("user/habitToDoItem/")
//        //上傳firebase
//        habitToDo.child(addHabitIndex.toString()).setValue(addHabitIndex.toString())


    }
    fun checkDateHabitToDo(modifyIndex:Int, modifyItem:HabitDate){
        modifyAllHabit(modifyIndex,modifyItem)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun modifyDateHabitToDo(modifyIndex:Int, modifyItem:HabitDate){
        setDateHabitToDo(modifyItem)
        deleteAllHabit(modifyIndex)
        //modifyAllHabit(modifyIndex,modifyItem)

        //把過去紀錄以外的紀錄刪除
        val currentDate = LocalDateTime.now()
        val timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val nowDate = LocalDateTime.parse(currentDate.format(timeFormatter),timeFormatter)
        val startDate = LocalDateTime.parse(modifyItem.startDate+" 00:00:00",timeFormatter)
        val endDate = LocalDateTime.parse(modifyItem.endDate+" 00:00:00",timeFormatter)

        val nowToEndDateDifference = ChronoUnit.DAYS.between(nowDate, endDate).toInt()
//        Log.i("AllItemData","nowToEndDateDifference=${nowToEndDateDifference}")
//        if(nowToEndDateDifference>0){
//            //項目中有未來 可動
//            //刪掉未來的
//            for (notEndItemDate in modifyItem.notEndItemList){
//                val notEndItem = allToDoMap[notEndItemDate.toInt()]
//                val notEndStartDate = LocalDateTime.parse(notEndItem?.startDate+" 00:00:00",timeFormatter)
//                val nowToNotEndDateDifference = ChronoUnit.DAYS.between(nowDate, notEndStartDate).toInt()
//                if(nowToNotEndDateDifference>0){
//                    //未來的項目
//                    modifyItem.notEndItemList.remove(notEndItemDate)
//                }
//            }
//            //重新建未來的
//            addSingleItemOfHabit()
//
//        }

       //modifyAllItem(modifyIndex,modifyItem)
        // Log.i("AllItemData","modify todayToDoItem=${todayToDoItem}")
    }
    fun deleteDateHabitToDo(deleteIndex:Int){
        deleteAllHabit(deleteIndex)
    }
    fun addSingleItemOfHabit(){

    }

    fun getHabitToDoList():ArrayList<HabitDate>{

       val habitToDoList = ArrayList<HabitDate>()


        for (itemIndex in allHabitToDoMap){
            itemIndex.value?.let { habitToDoList.add(it) }
            // Log.i("AllItemData","nowItemDate="+nowItemDate)

        }


        return habitToDoList

    }


    fun getDateActivity():Map<String, ItemDate>{
        var activity = sortedMapOf<String, ItemDate>()

        for ((key,nowItemDate)in  allActivityMap){

                if(currentDate.equals(nowItemDate?.startDate)){
                        //  Log.i("AllItemData","nowItemDate="+nowItemDate?.startDate)
                    if (nowItemDate != null ){
                        activity.put(key.toString(),nowItemDate)
                    }
                }

        }
        return activity
    }

    fun getIntervalDateActivity(intervalDates: ArrayList<String>):ArrayList<ItemDate>{
        var activity = ArrayList<ItemDate>()

        for ((key,nowItemDate)in  allActivityMap){
            for (intervalDate in intervalDates){
                if(intervalDate.equals(nowItemDate?.startDate)){
                  //  Log.i("AllItemData","nowItemDate="+nowItemDate?.startDate)
                    if (nowItemDate != null ){
                        activity.add(nowItemDate)
                    }
                }
            }


        }
        return activity
    }

    fun setActivity(eventID:Long,startDate:String,endDate:String,startTime:String,endTime:String,title:String){
        val addItem =ItemDate()
        addItem.name = title
        addItem.startDate = startDate
        addItem.endDate = endDate
        addItem.startTime = startTime
        addItem.endTime = endTime
        addItem.isActivity = true

        allActivityMap.put(eventID.toInt(),addItem)
    }


    //專案

    fun getProjectHabitDate(projectName:String):Map<String,HabitDate>{
        val allProjectHabitDate = sortedMapOf<String,HabitDate>()
        for ((key,item)in   allHabitToDoMap){
            if (item != null) {
                if(item.project.equals(projectName)){
                    allProjectHabitDate.put(key.toString(),item)
                }
            }
        }
        return allProjectHabitDate

    }

    fun getProjectItemDate(projectName : String):Map<String,ItemDate>{
        val allProjectItemDate = sortedMapOf<String,ItemDate>()
        for ((key,item)in   allToDoMap){
            if (item != null) {
                if(item.project.equals(projectName)){
                    allProjectItemDate.put(key.toString(),item)
                }
            }
        }
        return allProjectItemDate

    }

    fun getProjectName():ArrayList<String>{
        var allProject = ArrayList<String>()
        for ((key,item)in   allProjectMap){
            allProject.add(item)
         }
        return allProject
    }

    fun setProject(AddProject:String){
        setAllProject(AddProject)
    }

    fun dateFormatter(year:Int,monthOfYear:Int,dayOfMonth:Int):String{
        var chooseTime =""

        chooseTime ="${year}"
        if(monthOfYear+1<10){
            chooseTime +="-0${monthOfYear+1}"
        }else{
            chooseTime +="-${monthOfYear+1}"
        }
        if (dayOfMonth<10){
            chooseTime +="-0${dayOfMonth}"
        }else{
            chooseTime +="-${dayOfMonth}"
        }
        return chooseTime

    }

}



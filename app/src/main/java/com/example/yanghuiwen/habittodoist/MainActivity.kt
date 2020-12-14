package com.example.yanghuiwen.habittodoist

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.util.*


class MainActivity : AppCompatActivity(), BasicDialogFragment.OnHeadlineSelectedListener  {
    var todayList:SingleItem<String>?  = null
    var habitList:SingleItem<String>?  = null
    var scheduleList:ScheduleItem<String>? =null
    private var isFabOpen = false

    var habit_RecyclerView:RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val allItemData = allItemData()

        allItemData.scheduleToDo.add("habit")
        allItemData.scheduleToDo.add("呵")
        scheduleList = ScheduleItem(allItemData.scheduleToDo)
        val scheduleLayoutManager = LinearLayoutManager(this);
        scheduleLayoutManager.orientation = LinearLayoutManager.VERTICAL
        val schedule_RecyclerView = findViewById<View>(R.id.timeList) as RecyclerView
        schedule_RecyclerView.layoutManager = scheduleLayoutManager
        schedule_RecyclerView.adapter = scheduleList


        allItemData.todayToDo.add("早安")
        allItemData.todayToDo.add("呵呵")


        allItemData.habitToDo.add("habit")
        allItemData.habitToDo.add("呵")


        todayList = SingleItem(allItemData.todayToDo)
        val layoutManager = LinearLayoutManager(this);
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        val mRecyclerView = findViewById<View>(R.id.todayList) as RecyclerView
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.adapter = todayList

        habitList = SingleItem(allItemData.habitToDo)
        val habitLayoutManager = LinearLayoutManager(this);
        habitLayoutManager.orientation = LinearLayoutManager.VERTICAL
        habit_RecyclerView = findViewById<View>(R.id.habitList) as RecyclerView
        habit_RecyclerView?.layoutManager = habitLayoutManager
        habit_RecyclerView?.adapter = habitList


        val addItemFab: FloatingActionButton = findViewById(R.id.addItem)
        addItemFab.setOnClickListener { view ->
        var basicDialogFragment = BasicDialogFragment(allItemData)
            basicDialogFragment.setOnHeadlineSelectedListener(this)
            //            var bundle = Bundle()
//            bundle.put("callback", object :Callback{
//                override fun response(message: String) {
//
//                }
//            })
//            bundle.putSerializable("callback", object :Callback{
//                override fun response(message: String) {
//
//                }
//            })
//            dialog.arguments=bundle

            supportFragmentManager.let {
                basicDialogFragment.show(it, "")
            }
        }
        addItemFab.hide()
        val addHabitFab: FloatingActionButton = findViewById(R.id.addHabit)
        addHabitFab.setOnClickListener { view ->
            Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show()
        }
        addHabitFab.hide()
        val addFab: FloatingActionButton = findViewById(R.id.add)
        addFab.setOnClickListener { view ->
           if(!isFabOpen){
               addItemFab.show()
               addHabitFab.show()
           }else{
               addItemFab.hide()
               addHabitFab.hide()

           }
            isFabOpen = !isFabOpen
        }




    }

    override fun onArticleSelected(position: allItemData) {
        Log.i("kiki","habitToDo="+position.habitToDo)
        habitList = SingleItem(position.habitToDo)

        habit_RecyclerView?.adapter = habitList
    }

    inner class SingleItem<T>(data: List<T>) : RecyclerView.Adapter<SingleItem<T>.ViewHolder>() {
        var mData: List<T> = ArrayList()

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var mTextView: TextView

            init {
                mTextView = itemView.findViewById<View>(R.id.info_text) as TextView
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.to_do_item, parent, false)
            return ViewHolder(v)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.mTextView.text = mData[position].toString()
            holder.itemView.setOnClickListener {
                val textView = findViewById<TextView>(R.id.info_text)
                textView.text = mData.toString() + ""
            }
            holder.itemView.setOnLongClickListener { false }
        }

        override fun getItemCount(): Int {
            return mData.size
        }

        init {
            mData = data
        }
    }




    inner class ScheduleItem<T>(data: List<T>) : RecyclerView.Adapter<ScheduleItem<T>.ViewHolder>() {
        var todoData: List<T> = ArrayList()

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var startTimeTextView: TextView
            var endTimeTextView: TextView
            init {
                startTimeTextView = itemView.findViewById<View>(R.id.startTime) as TextView
                endTimeTextView = itemView.findViewById<View>(R.id.endTime) as TextView
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.schedule, parent, false)
            return ViewHolder(v)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            var startTime = if(position<10) "0${position}:00" else "${position}:00"
            var endTime = if(position+1<10) "0${position+1}:00" else "${position+1}:00"



            holder.startTimeTextView.text = startTime
            holder.endTimeTextView.text = endTime
            holder.startTimeTextView.setOnLongClickListener { false }
            holder.endTimeTextView.setOnLongClickListener { false }
        }

        override fun getItemCount(): Int {
            return 24
        }

        init {
            todoData = data
        }
    }


}

class BasicDialogFragment(ItemData: allItemData) : DialogFragment() {
    var allItemData =ItemData

    internal var callback: OnHeadlineSelectedListener? = null
    fun setOnHeadlineSelectedListener(callback: OnHeadlineSelectedListener) {
        this.callback = callback
    }

    interface OnHeadlineSelectedListener {
        fun onArticleSelected(position: allItemData)
    }



    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val v=LayoutInflater.from(context).inflate(R.layout.dialog_signin, container, false)

        return v
    }



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.dialog_signin, null))
                    // Add action buttons
                    .setPositiveButton("signin",
                            DialogInterface.OnClickListener { dialog, id ->

                                    var e = getDialog()?.findViewById<EditText>(R.id.toDo)
                                    allItemData.habitToDo.add(e?.text.toString())
                                    Log.i("kiki", "habitToDo=" + allItemData.habitToDo)
                                    callback?.onArticleSelected(allItemData)

                                //抓不到值
                            })
                    .setNegativeButton("cancel",
                            DialogInterface.OnClickListener { dialog, id ->

                                dialog.cancel()
                            })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}

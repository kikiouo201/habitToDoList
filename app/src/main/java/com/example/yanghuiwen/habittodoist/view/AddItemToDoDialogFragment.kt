package com.example.yanghuiwen.habittodoist.view

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.yanghuiwen.habittodoist.AllItemData
//import com.example.yanghuiwen.habittodoist.AllItemData
import com.example.yanghuiwen.habittodoist.R

class AddItemToDoDialogFragment : DialogFragment() {


    internal var callback: OnHeadlineSelectedListener? = null
    fun setOnHeadlineSelectedListener(callback: OnHeadlineSelectedListener) {
        this.callback = callback
    }

    interface OnHeadlineSelectedListener {
        fun onArticleSelected(position: AllItemData)
    }



    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val v= LayoutInflater.from(context).inflate(R.layout.dialog_signin, container, false)

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

                                val e = getDialog()?.findViewById<EditText>(R.id.toDo)
                                //AllItemData.habitToDo.add(e?.text.toString())
                                Log.i("kiki", "habitToDo=" + AllItemData.habitToDo)
                                callback?.onArticleSelected(AllItemData)
                            })
                    .setNegativeButton("cancel",
                            DialogInterface.OnClickListener { dialog, id ->

                                dialog.cancel()
                            })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}

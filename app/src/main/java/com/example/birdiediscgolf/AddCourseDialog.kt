package com.example.birdiediscgolf


import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatDialogFragment

class AddCourseDialog : AppCompatDialogFragment() {
    lateinit var courseNameEditText: EditText
    lateinit var holeCountNumberPicker: NumberPicker
    lateinit var listener: dialogListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity, R.style.AlertDialogTheme)

        val inflater: LayoutInflater? = activity?.layoutInflater



        val view: View
        if (inflater != null) {
            view = inflater.inflate(R.layout.dialog_layout, null)
            courseNameEditText = view.findViewById(R.id.edit_courseName)
            holeCountNumberPicker = view.findViewById(R.id.edit_holeCount)

            builder.setView(view)
                .setTitle("Add new course")
                .setNegativeButton("Cancel"){dialog, which ->

                }
                .setPositiveButton("Continue"){dialog, which ->
                    val courseName = courseNameEditText.text.toString()
                    val holeCount = holeCountNumberPicker.value
                    listener.getDialogInput(courseName, holeCount)
                }
            holeCountNumberPicker.minValue = 1
            holeCountNumberPicker.maxValue = 50
            holeCountNumberPicker.value = 9
            holeCountNumberPicker.wrapSelectorWheel = false
        }


        return builder.create()
        //return super.onCreateDialog(savedInstanceState)
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        try {
            listener = activity as dialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + "must implement dialogListener")
        }
    }

    interface dialogListener {
        fun getDialogInput(courseName: String, holeCount: Int) {

        }
    }
}
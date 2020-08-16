package com.example.birdiediscgolf

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatDialogFragment

class ChangeParDialog : AppCompatDialogFragment() {
    lateinit var parNumberPicker: NumberPicker
    lateinit var listener: DialogListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity, R.style.AlertDialogTheme)

        val inflater: LayoutInflater? = activity?.layoutInflater



        val view: View
        if (inflater != null) {
            view = inflater.inflate(R.layout.dialog_layout_change_par, null)
            parNumberPicker = view.findViewById(R.id.edit_par)
            parNumberPicker.maxValue = 6
            parNumberPicker.minValue = 1
            parNumberPicker.value = 3
            parNumberPicker.wrapSelectorWheel = true

            builder.setView(view)
                .setTitle("Change par")
                .setNegativeButton("Cancel"){dialog, which ->

                }
                .setPositiveButton("Continue"){dialog, which ->
                    val par = parNumberPicker.value
                    listener.getDialogInput(par)
                }
        }


        return builder.create()
        //return super.onCreateDialog(savedInstanceState)
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        try {
            listener = activity as DialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + "must implement dialogListener")
        }
    }

    interface DialogListener {
        fun getDialogInput(par: Int) {

        }
    }
}
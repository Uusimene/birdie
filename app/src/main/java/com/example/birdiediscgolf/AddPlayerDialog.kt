package com.example.birdiediscgolf

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatDialogFragment

class AddPlayerDialog : AppCompatDialogFragment() {
    lateinit var playerNameEditText: EditText
    lateinit var listener: AddPlayerDialog.DialogListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity, R.style.AlertDialogTheme)

        val inflater: LayoutInflater? = activity?.layoutInflater



        val view: View
        if (inflater != null) {
            view = inflater.inflate(R.layout.dialog_layout_add_player, null)
            playerNameEditText = view.findViewById(R.id.edit_playerName)

            builder.setView(view)
                .setTitle("Add new player")
                .setNegativeButton("Cancel"){dialog, which ->

                }
                .setPositiveButton("Continue"){dialog, which ->
                    val playerName = playerNameEditText.text.toString()
                    listener.getDialogInput(playerName)
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
        fun getDialogInput(playerName: String) {

        }
    }
}
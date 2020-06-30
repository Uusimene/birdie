package com.example.birdiediscgolf.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

import com.example.birdiediscgolf.R

class gameFragment : Fragment() {


    private lateinit var viewModel: GameViewModel
    private val numberButtons = mutableListOf<Button>()
    private val plusMinusButtons = mutableListOf<Button>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_game, container, false)

        numberButtons.add(root.findViewById(R.id.button1))
        numberButtons.add(root.findViewById(R.id.button2))
        numberButtons.add(root.findViewById(R.id.button3))
        numberButtons.add(root.findViewById(R.id.button4))
        numberButtons.add(root.findViewById(R.id.button5))
        numberButtons.add(root.findViewById(R.id.button6))
        numberButtons.add(root.findViewById(R.id.button7))
        numberButtons.add(root.findViewById(R.id.button8))
        numberButtons.add(root.findViewById(R.id.button9))
        plusMinusButtons.add(root.findViewById(R.id.buttonMinus))
        plusMinusButtons.add(root.findViewById(R.id.buttonPlus))

        for (button in numberButtons) {
            button.setOnClickListener{
                pressButton(it)
            }
        }
        for (button in plusMinusButtons) {
            button.setOnClickListener{
                pressButton(it)
            }
        }

        return root
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)
//        // TODO: Use the ViewModel
//    }
    private fun pressButton(view: View){
        when (view.tag){
            "1"-> Toast.makeText(activity, view.tag.toString(), Toast.LENGTH_SHORT).show()
            "2"-> Toast.makeText(activity, view.tag.toString(), Toast.LENGTH_SHORT).show()
            "3"-> Toast.makeText(activity, view.tag.toString(), Toast.LENGTH_SHORT).show()
            "4"-> Toast.makeText(activity, view.tag.toString(), Toast.LENGTH_SHORT).show()
            "5"-> Toast.makeText(activity, view.tag.toString(), Toast.LENGTH_SHORT).show()
            "6"-> Toast.makeText(activity, view.tag.toString(), Toast.LENGTH_SHORT).show()
            "7"-> Toast.makeText(activity, view.tag.toString(), Toast.LENGTH_SHORT).show()
            "8"-> Toast.makeText(activity, view.tag.toString(), Toast.LENGTH_SHORT).show()
            "9"-> Toast.makeText(activity, view.tag.toString(), Toast.LENGTH_SHORT).show()
            "-"-> adjustButtonNumbers(view.tag.toString())
            "+"-> adjustButtonNumbers(view.tag.toString())
            else -> {
                Toast.makeText(activity, "You shouldn't see this happen, something bork", Toast.LENGTH_SHORT).show()
            }

        }
}
    private fun adjustButtonNumbers(tag: String){
        var lowest = 99999
        for (button in numberButtons) {
            try {
                var value = 0
                when (tag) {
                    "+" -> value = button.text.toString().toInt() + 9
                    "-" -> value = button.text.toString().toInt() - 9
                }
                lowest = if (value < lowest) value else lowest
                button.text = value.toString()
            }
            catch (e: NumberFormatException) {
                continue
            }
        }

        if (lowest in 2..50){
            for (button in plusMinusButtons) {
                when (button.tag) {
                    "-" -> button.isEnabled = true
                    "+" -> button.isEnabled = true
                }
            }
        }
        else if (lowest > 50){
            for (button in plusMinusButtons) {
                when (button.tag) {
                    "-" -> button.isEnabled = true
                    "+" -> button.isEnabled = false
                }
            }
        }
        else if (lowest == 1){
            for (button in plusMinusButtons) {
                when (button.tag) {
                    "-" -> button.isEnabled = false
                    "+" -> button.isEnabled = true
                }
            }
        }
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): gameFragment {
            return gameFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

}

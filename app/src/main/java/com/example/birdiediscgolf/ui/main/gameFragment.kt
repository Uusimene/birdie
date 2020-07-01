package com.example.birdiediscgolf.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager

import com.example.birdiediscgolf.R
import kotlinx.android.synthetic.main.fragment_game.*

class gameFragment : Fragment() {


    private lateinit var viewModel: GameViewModel
    private val numberButtons = mutableListOf<Button>()
    private val plusMinusButtons = mutableListOf<Button>()

    private var score = 0
    private var selectedButtonTag = "0"


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

        val textView: TextView = root.findViewById(R.id.textViewPar)
        val par = arguments?.getInt(ARG_PAR) ?: -1
        val parText = "Par $par"
        textView.text = parText

        numberButtons.clear()
        plusMinusButtons.clear()
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

    override fun onResume() {
        super.onResume()
        if (selectedButtonTag != "0") {
            val btnIndex = selectedButtonTag.toInt() - 1
            numberButtons[btnIndex].background = ResourcesCompat.getDrawable(resources, R.color.colorPrimary, null)
        }
    }



    //    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)
//        // TODO: Use the ViewModel
//    }
    private fun pressButton(view: View){
        when (view.tag){
            "1"-> selectScore(view.tag.toString())
            "2"-> selectScore(view.tag.toString())
            "3"-> selectScore(view.tag.toString())
            "4"-> selectScore(view.tag.toString())
            "5"-> selectScore(view.tag.toString())
            "6"-> selectScore(view.tag.toString())
            "7"-> selectScore(view.tag.toString())
            "8"-> selectScore(view.tag.toString())
            "9"-> selectScore(view.tag.toString())
            "-"-> adjustButtonNumbers(view.tag.toString())
            "+"-> adjustButtonNumbers(view.tag.toString())
            else -> {
                Toast.makeText(activity, "You shouldn't see this happen, something bork", Toast.LENGTH_SHORT).show()
            }

        }
}

    private fun nextTab(){
        val viewPager = activity?.findViewById(R.id.view_pager) as ViewPager
        viewPager.setCurrentItem(viewPager.currentItem + 1, true)
    }

    private fun selectScore(tag: String) {
        for (button in numberButtons){
            if (button.tag == tag) {
                if (selectedButtonTag == "0") {
                    score = button.text.toString().toInt()
                    selectedButtonTag = tag
                    button.background = ResourcesCompat.getDrawable(resources, R.color.colorPrimary, null)
                    nextTab()
                }
                else if (selectedButtonTag == button.tag) {
                    score = 0
                    selectedButtonTag = "0"
                    button.background = ResourcesCompat.getDrawable(resources, android.R.color.transparent, null)

                }
                else {
                    for (selButton in numberButtons) {
                        if (selButton.tag == selectedButtonTag) {
                            selButton.background = ResourcesCompat.getDrawable(resources, android.R.color.transparent, null)
                            break
                        }
                    }
                    score = button.text.toString().toInt()
                    selectedButtonTag = tag
                    button.background = ResourcesCompat.getDrawable(resources, R.color.colorPrimary, null)
                }
                break
            }
        }
        Toast.makeText(activity, "score: $score   selectedButtonTag: $selectedButtonTag", Toast.LENGTH_SHORT).show()
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
        private const val ARG_PAR = "par"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int, par: Int): gameFragment {
            return gameFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                    putInt(ARG_PAR, par)
                }
            }
        }
    }

}

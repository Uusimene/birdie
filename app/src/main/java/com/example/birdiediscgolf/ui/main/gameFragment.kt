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

        val buttons = mutableListOf<Button>()
        buttons.add(root.findViewById(R.id.button1))
        buttons.add(root.findViewById(R.id.button2))
        buttons.add(root.findViewById(R.id.button3))
        buttons.add(root.findViewById(R.id.button4))
        buttons.add(root.findViewById(R.id.button5))
        buttons.add(root.findViewById(R.id.button6))
        buttons.add(root.findViewById(R.id.button7))
        buttons.add(root.findViewById(R.id.button8))
        buttons.add(root.findViewById(R.id.button9))
        buttons.add(root.findViewById(R.id.buttonMinus))
        buttons.add(root.findViewById(R.id.buttonPlus))

        for (button in buttons) {
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
    fun pressButton(view: View){
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
            "-"-> Toast.makeText(activity, view.tag.toString(), Toast.LENGTH_SHORT).show()
            "+"-> Toast.makeText(activity, view.tag.toString(), Toast.LENGTH_SHORT).show()

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

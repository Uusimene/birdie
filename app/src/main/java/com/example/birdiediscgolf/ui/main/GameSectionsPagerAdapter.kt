package com.example.birdiediscgolf.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.birdiediscgolf.CourseAndHoles

private var TAB_TITLES = emptyList<String>()
private var COURSE_AND_HOLES = CourseAndHoles()

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class GameSectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {


    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return gameFragment.newInstance(position + 1)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return TAB_TITLES[position]
    }

    override fun getCount(): Int {
        // Show n total pages.
        return TAB_TITLES.size
    }

    private fun setTabTitles(holeCount: Int) {
        TAB_TITLES = List(holeCount) { (it + 1).toString() }
    }

    fun setCourseScores(courseAndHoles: CourseAndHoles) {
        COURSE_AND_HOLES = courseAndHoles
        setTabTitles(COURSE_AND_HOLES.holes.size)
    }

}

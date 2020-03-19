package ua.allatra.allatraunites.ui.activity.ui.main

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ua.allatra.allatraunites.R

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager, private val languageCode: String) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return when (position) {
            0 -> SlideOneFragment.newInstance(position + 1)
            1 -> SlideTwoFragment.newInstance(position + 1)
            2 -> SlideThreeFragment.newInstance(position + 1, languageCode)
            3 -> SlideFourFragment.newInstance(position + 1, languageCode)

            else -> {
                Log.e("getItem", "unmapped position of fragment $position")
                SlideOneFragment.newInstance(position + 1)
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return position.toString()
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 4
    }
}
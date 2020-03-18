package ua.allatra.allatraunites.ui.activity.ui.main

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_slide_two.*
import ua.allatra.allatraunites.R

/**
 * A placeholder fragment containing a simple view.
 */
class SlideTwoFragment : Fragment() {

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
        fun newInstance(sectionNumber: Int): SlideTwoFragment {
            return SlideTwoFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    private lateinit var pageViewModel: PageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }

        setTxtRealitiesColors()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_slide_two, container, false)
    }

    private fun setTxtRealitiesColors(){
        val text1 = resources.getText(R.string.text_realities_of_our_times_1)
        val text2 = resources.getText(R.string.text_realities_of_our_times_2)

        val firstPart = "$text1 $text2"

        val spannableString = SpannableString(firstPart)
        val redColor = ForegroundColorSpan(Color.RED)

        spannableString.setSpan(redColor, text1.count(),firstPart.count(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        txtRealities1?.text = spannableString
    }
}
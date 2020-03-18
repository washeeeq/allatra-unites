package ua.allatra.allatraunites.ui.activity.ui.main

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import ua.allatra.allatraunites.R
import ua.allatra.allatraunites.ui.fonts.OpenSansRegularTextView

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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_slide_two, container, false)
        val textViewRealities1 = root.findViewById<OpenSansRegularTextView>(R.id.txtRealities1)
        val textViewPeopleHaveChance = root.findViewById<OpenSansRegularTextView>(R.id.txtPeopleHaveChance)
        setTxtRealitiesColors(textViewRealities1)
        setTxtPeopleHaveChance(textViewPeopleHaveChance)

        return root
    }

    private fun setTxtRealitiesColors(textView: OpenSansRegularTextView?){
        val text1 = resources.getText(R.string.text_realities_of_our_times_1)
        val text2 = resources.getText(R.string.text_realities_of_our_times_2)

        val wholeText = "$text1 \n\n$text2"

        val spannableString = SpannableString(wholeText)
        val redColor = ForegroundColorSpan(Color.RED)

        spannableString.setSpan(redColor, text1.count(),wholeText.count(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        setSpannableString(textView, spannableString)
    }

    private fun setTxtPeopleHaveChance(textView: OpenSansRegularTextView?){
        val textPart1 = resources.getText(R.string.text_people_have_chance_1)
        val textPart2 = resources.getText(R.string.text_people_have_chance_2)
        val textPart3 = resources.getText(R.string.text_people_have_chance_3)

        val wholeText = "$textPart1\n\n$textPart2\n\n$textPart3"

        val spannableString = SpannableString(wholeText)
        val redColor = ForegroundColorSpan(Color.RED)

        spannableString.setSpan(redColor, 0, textPart1.count(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        setSpannableString(textView, spannableString)
    }

    private fun setSpannableString(textView: OpenSansRegularTextView?, spannableString: SpannableString){
        textView?.let {
            it.text = spannableString
        }?: kotlin.run {
            Log.e("setTxtRealitiesColors", "View[txtRealities1] cannot be set cause object is null.")
        }
    }
}
package ua.allatra.allatraunites.ui.activity.ui.main

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import ua.allatra.allatraunites.R

/**
 * A placeholder fragment containing a simple view.
 */
class SlideThreeFragment(val languageCode: String) : Fragment() {
    companion object {
        private const val YOUTUBE_BASE_URL = "https://www.youtube.com/embed/"
        private const val CONFERENCE_URL_EN_ID = "8X3Z5RjnR1Y"
        private const val CONFERENCE_URL_RU_ID = "WIAA4w7C98w"
        private const val CONFERENCE_URL_CS_ID = "WvZXGN6WoIw"

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
        fun newInstance(sectionNumber: Int, languageCode: String): SlideThreeFragment {
            return SlideThreeFragment(languageCode).apply {
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
        val root = inflater.inflate(R.layout.fragment_slide_three, container, false)
        val mWebView = root.findViewById<WebView>(R.id.videoViewConference)
        //TODO: Pass language
        setVideoAboutTheConference(mWebView)

        return root
    }

    private fun setVideoAboutTheConference(mWebView: WebView?){
        mWebView?.let {
            var uri: String? = null
            uri = when(languageCode.toLowerCase()){
                "en" -> "$YOUTUBE_BASE_URL$CONFERENCE_URL_EN_ID"
                "ru" -> "$YOUTUBE_BASE_URL$CONFERENCE_URL_RU_ID"
                "cs" -> "$YOUTUBE_BASE_URL$CONFERENCE_URL_CS_ID"
                else -> {
                    Log.e("setVideoAboutTheConf", "Language unmapped: $languageCode, defaulting to EN.")
                    "$YOUTUBE_BASE_URL$CONFERENCE_URL_EN_ID"
                }
            }

            //TODO: Check another implementations
            //TODO: 1. Over Youtube Api Player, https://developers.google.com/youtube/android/player, this however requires direct extension from Activity or Fragment
            //TODO: 2. Over Videoview but we need to find a way how to get rst content, https://www.truiton.com/2013/08/android-videoview-example-with-youtube-playback/
            it.settings.javaScriptEnabled = true
            //mWebView.getSettings().setPluginState(PluginState.ON)
            it.webChromeClient = WebChromeClient()
            it.loadUrl(uri)
        }
    }
}
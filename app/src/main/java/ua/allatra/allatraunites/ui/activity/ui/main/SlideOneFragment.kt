package ua.allatra.allatraunites.ui.activity.ui.main

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
import java.util.*

/**
 * A placeholder fragment containing a simple view.
 */
class SlideOneFragment : Fragment() {

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
        val root = inflater.inflate(R.layout.fragment_slide_one, container, false)

        val mWebView1 = root.findViewById<WebView>(R.id.webViewTopLeft)
        val mWebView2 = root.findViewById<WebView>(R.id.webViewTopRight)
        val mWebView3 = root.findViewById<WebView>(R.id.webViewBottomLeft)
        val mWebView4 = root.findViewById<WebView>(R.id.webViewBottomRight)

        setFutureIsNowVideos(mWebView1, mWebView2, mWebView3, mWebView4)

//        val textView: TextView = root.findViewById(R.id.section_label)
//        pageViewModel.text.observe(this, Observer<String> {
//            textView.text = it
//        })
        return root
    }

    private fun setFutureIsNowVideos(mWebView1: WebView?, mWebView2: WebView?, mWebView3: WebView?, mWebView4: WebView?){

        val videoList = when (languageCode.toLowerCase(Locale.ROOT)){
            "en" -> FUTURE_IS_NOW_VIDEOS_EN
            "ru" -> FUTURE_IS_NOW_VIDEOS_RU
            "cs" -> FUTURE_IS_NOW_VIDEOS_CS
            else -> {
                Log.e("setFutureIsNowVideos", "Language unmapped: $languageCode, defaulting to EN.")
                FUTURE_IS_NOW_VIDEOS_EN
            }
        }

        mWebView1?.let {
            val uri = "$YOUTUBE_BASE_URL${videoList[0]}"

            it.settings.javaScriptEnabled = true
            //mWebView.getSettings().setPluginState(PluginState.ON)
            it.webChromeClient = WebChromeClient()
            it.loadUrl(uri)
        }
        mWebView2?.let {
            val uri = "$YOUTUBE_BASE_URL${videoList[1]}"

            it.settings.javaScriptEnabled = true
            //mWebView.getSettings().setPluginState(PluginState.ON)
            it.webChromeClient = WebChromeClient()
            it.loadUrl(uri)
        }
        mWebView3?.let {
            val uri = "$YOUTUBE_BASE_URL${videoList[2]}"

            it.settings.javaScriptEnabled = true
            //mWebView.getSettings().setPluginState(PluginState.ON)
            it.webChromeClient = WebChromeClient()
            it.loadUrl(uri)
        }
        mWebView4?.let {
            val uri = "$YOUTUBE_BASE_URL${videoList[3]}"

            it.settings.javaScriptEnabled = true
            //mWebView.getSettings().setPluginState(PluginState.ON)
            it.webChromeClient = WebChromeClient()
            it.loadUrl(uri)
        }
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"
        private const val YOUTUBE_BASE_URL = "https://www.youtube.com/embed/"
        private const val languageCode = "ru"

        private val FUTURE_IS_NOW_VIDEOS_CS = mutableListOf(
            "LjUP2oTUpZY", "wt1ppRC4AYI", "RUnjp6VcpDc", "iLyu9av8mCE"
        )

        private val FUTURE_IS_NOW_VIDEOS_EN = mutableListOf(
            "qyWNAg0yB4k", "_d7KEEvGpCU", "Kyf85-yi7GI", "hWHqY8Piios"
        )

        private val FUTURE_IS_NOW_VIDEOS_RU = mutableListOf(
            "_ibC-1KK2eY", "Xv7Ns-Wt33M", "PaJuT4uLhJA", "Q69gFVmrCiI"
        )

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): SlideOneFragment {
            return SlideOneFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}
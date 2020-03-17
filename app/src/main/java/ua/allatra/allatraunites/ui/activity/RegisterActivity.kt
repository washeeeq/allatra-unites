package ua.allatra.allatraunites.ui.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_register.*
import ua.allatra.allatraunites.R
import ua.allatra.allatraunites.ui.fonts.CustomTypefaceSpan

class RegisterActivity : AppCompatActivity() {
    private var realWidth: Int? = null
    private var realHeight: Int? = null

    companion object {
        private const val DELAY_SHOW_THANK_YOU = 2L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        realHeight = intent.extras?.getInt(SplashScreenActivity.SCREEN_HEIGHT)
        realWidth = intent.extras?.getInt(SplashScreenActivity.SCREEN_WIDTH)

        Picasso
            .get()
            .load(R.drawable.bg_people_blur)
            .into(imgGreatBackground)

        setGdprTextColors()
        setThankYouStyle()

        /**
         * GDPR check
         */
        imgCheckBoxGdpr?.setOnClickListener{
            Log.d("RegisterActivity", "User clicked.")

            if(it.background == null){
                Log.d("RegisterActivity", "Unticked.")
                it.background = resources.getDrawable(R.drawable.bitmap_check_red)
            } else {
                Log.d("RegisterActivity", "Ticked.")
                it.background = null
            }
        }

        imgBtnISupportIdea?.setOnClickListener{
            it as ImageView
            //TODO: Add more checks on name, e-mail, address
            if(!userGaveConsent()){
                Log.e("RegisterActivity", "Please agree on GDPR!")
                showConsentAgree()
            } else {
                //TODO: Fire register rest api call
                // youhuuu celebrate
                it.setImageDrawable(resources.getDrawable(R.drawable.vector_btn_agree_ticked))
                // wait here a bit
                thankYouLayout?.visibility = View.VISIBLE
                //TODO: Add animation, https://github.com/glomadrian/Grav

                Handler().postDelayed(
                    {
                        thankYouLayout.visibility = View.INVISIBLE
                        startFollowingActivity()
                    }, DELAY_SHOW_THANK_YOU * 1000
                ) // wait for 3 seconds
            }
        }
    }

    private fun startFollowingActivity(){
        val intent = Intent(this@RegisterActivity, StatisticalActivity::class.java)
        val bundle = Bundle()
        bundle.putInt(SplashScreenActivity.SCREEN_HEIGHT, realHeight!!)
        bundle.putInt(SplashScreenActivity.SCREEN_WIDTH, realWidth!!)
        intent.putExtras(bundle)
        startActivity(intent)
        finish()
    }

    private fun showConsentAgree(){
        val msg = getString(R.string.text_please_agree_to_give_consent)
        Log.e("RegisterActivity", msg)
        val snackBar: Snackbar = Snackbar
            .make(rootLayout, msg, Snackbar.LENGTH_LONG)
        snackBar.setActionTextColor(Color.RED)
        snackBar.show()
    }

    private fun userGaveConsent(): Boolean = imgCheckBoxGdpr?.background != null

    private fun setThankYouStyle(){
        val textThanks1 = resources.getText(R.string.text_thanks_for_supporting_1)
        val textThanks2 = resources.getText(R.string.text_thanks_for_supporting_2)
        val fontBold = Typeface.createFromAsset(assets, "OpenSans-Bold.ttf")
        val fontSemiBold = Typeface.createFromAsset(assets, "OpenSans-SemiBold.ttf")

        val spannableString1 = SpannableString(textThanks1)
        val spannableString2 = SpannableString(textThanks2)

        spannableString1.setSpan(CustomTypefaceSpan(fontBold, resources.getColor(R.color.colorAlertText)), 0, textThanks1.count()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString1.setSpan(CustomTypefaceSpan(fontSemiBold, resources.getColor(R.color.colorThankTextBlue)), textThanks1.count()-1, textThanks1.count(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        txtThanksForJoining1?.text = spannableString1

        spannableString2.setSpan(CustomTypefaceSpan(fontSemiBold, resources.getColor(R.color.colorThankTextBlue)), 0, textThanks2.count(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        txtThanksForJoining2?.text = spannableString2
    }

    private fun setGdprTextColors(){
        val language = "ru"
        val text = resources.getText(R.string.text_i_give_consent)
        val spannableString = SpannableString(text)
        val redColor = ForegroundColorSpan(Color.RED)

        when(language){
            "ru" -> {
                spannableString.setSpan(redColor, 1,2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                spannableString.setSpan(redColor, 13,45, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            else -> {}
        }

        txtGdprAgree?.text = spannableString
    }
}

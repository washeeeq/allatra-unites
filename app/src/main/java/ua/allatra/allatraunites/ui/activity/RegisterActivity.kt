package ua.allatra.allatraunites.ui.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_register.*
import ua.allatra.allatraunites.R

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setGdprTextColors()
        setThankYouStyle()

        /**
         * GDPR check
         */
        imgCheckBoxGdpr?.setOnClickListener{
            Log.d("RegisterActivity", "User clicked.")

            if(it.background == null){
                Log.d("RegisterActivity", "Unticked.")
                it.background = resources.getDrawable(R.drawable.ic_check_red_24dp)
            } else {
                Log.d("RegisterActivity", "Ticked.")
                it.background = null
            }
        }

        imgBtnISupportIdea?.setOnClickListener{
            //TODO: Add more checks on name, e-mail, address
            if(!userGaveConsent()){
                Log.e("RegisterActivity", "Please agree on GDPR!")
                showConsentAgree()
            } else {
                //TODO: Fire register rest api call
                // youhuuu celebrate
                it.background = resources.getDrawable(R.drawable.vector_btn_agree_ticked)
                // wait here a bit
                thankYouLayout.visibility = View.VISIBLE
                //TODO: Add animation, https://github.com/glomadrian/Grav

                Handler().postDelayed(
                    {
                        thankYouLayout.visibility = View.INVISIBLE
                        startActivity(Intent(this@RegisterActivity, StatisticalActivity::class.java))
                    }, 1 * 1000
                ) // wait for 3 seconds
            }
        }
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
        val language = "ru"
        val text = resources.getText(R.string.text_thanks_for_joining)

        val spannableString = SpannableString(text)
        val redColor = ForegroundColorSpan(Color.RED)
        val blueColor = ForegroundColorSpan(resources.getColor(R.color.colorThankTextBlue))

        when(language){
            "ru" -> {
                spannableString.setSpan(redColor, 0,7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                spannableString.setSpan(blueColor, 8,30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            else -> {}
        }

        txtThanksForJoining?.text = spannableString
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

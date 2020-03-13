package ua.allatra.allatraunites.ui.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_register.*
import ua.allatra.allatraunites.R

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setGdprTextColors()

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
            if(!getIsGdprAgreed()){
                Log.e("RegisterActivity", "Please agree on GDPR!")
                showConsentAgree()
            } else {
                //TODO: Fire register rest api call
                // youhuuu celebrate
                it.background = resources.getDrawable(R.drawable.vector_btn_agree_ticked)
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

    fun getIsGdprAgreed(): Boolean = imgCheckBoxGdpr?.background != null

    fun setGdprTextColors(){
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

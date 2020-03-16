package ua.allatra.allatraunites.ui.activity

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_splash_screen.*
import ua.allatra.allatraunites.R
import ua.allatra.allatraunites.ui.db.RealmHandler
import ua.allatra.allatraunites.ui.db.RealmHandler.Companion.DEFAULT_ID
import java.util.*


class RegisterActivity : AppCompatActivity() {
    private var realWidth: Int? = null
    private var realHeight: Int? = null
    private lateinit var realmHandler: RealmHandler

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
            //TODO: Add more checks on name, e-mail, address
            if(!userGaveConsent()){
                Log.e("RegisterActivity", "Please agree on GDPR!")
                showConsentAgree()
            } else {
                //TODO: Fire register rest api call
                // youhuuu celebrate
                it.background = resources.getDrawable(R.drawable.vector_btn_agree_ticked)
                // wait here a bit
                thankYouLayout?.visibility = View.VISIBLE
                //TODO: Add animation, https://github.com/glomadrian/Grav

                Handler().postDelayed(
                    {
                        thankYouLayout.visibility = View.INVISIBLE
                        startFollowingActivity()
                    }, 1 * 1000
                ) // wait for 3 seconds
            }
        }

        realmHandler = RealmHandler(this)
        val user = realmHandler.getUserDAO(DEFAULT_ID)

        user?.let {
            // setting user to
            //setLocale(user.getLanguage())
            setLocale(this,"ru")
        }?:run{
            val languageCode = Locale.getDefault().language
            realmHandler.createUserDAO(languageCode)
            Log.d("onCreate", "Locale is $languageCode, new user DAO created.")
        }
    }

    private fun setLocale(context: Context, languageCode: String){
        Log.d("setLocale", "Setting locale to: $languageCode")
        // Change locale settings in the app.
        // Change locale settings in the app.
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val dm: DisplayMetrics = resources.displayMetrics
        val configuration: Configuration = resources.configuration

        if (Build.VERSION.SDK_INT >= 17) {
            configuration.setLocale(locale)
            context.createConfigurationContext(configuration)
        } else {
            configuration.locale = locale
            resources.updateConfiguration(configuration, dm)
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
        val language = "en"
        val text = resources.getText(R.string.text_thanks_for_joining)

        val spannableString = SpannableString(text)
        val redColor = ForegroundColorSpan(Color.RED)
        val blueColor = ForegroundColorSpan(resources.getColor(R.color.colorThankTextBlue))

        when(language){
            "ru" -> {
                spannableString.setSpan(redColor, 0,7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                spannableString.setSpan(blueColor, 8,30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            "en" -> {
                spannableString.setSpan(redColor, 0,7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                spannableString.setSpan(blueColor, 8,27, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
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

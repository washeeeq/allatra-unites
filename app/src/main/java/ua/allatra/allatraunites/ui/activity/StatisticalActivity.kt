package ua.allatra.allatraunites.ui.activity

import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_statistical.*
import kotlinx.android.synthetic.main.upper_nav_menu_statistical.*
import ua.allatra.allatraunites.R
import ua.allatra.allatraunites.ui.activity.SplashScreenActivity.Companion.SCREEN_HEIGHT
import ua.allatra.allatraunites.ui.activity.SplashScreenActivity.Companion.SCREEN_WIDTH
import ua.allatra.allatraunites.ui.adapter.PersonAdapter
import ua.allatra.allatraunites.db.RealmHandler
import ua.allatra.allatraunites.db.UserDAO
import ua.allatra.allatraunites.ui.fonts.CustomTypefaceSpan
import java.util.*

class StatisticalActivity : AppCompatActivity() {

    private lateinit var realmHandler: RealmHandler
    private var user: UserDAO? = null
    private var isInitiated: Boolean = false

    companion object {
        private const val NUMBER_OF_PEOPLE = 158999
        private const val TOTAL_CITIES = 410
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistical)

        val screenHeight = intent.extras?.getInt(SCREEN_HEIGHT)
        val screenWidth = intent.extras?.getInt(SCREEN_WIDTH)

        /**
         * Show polls
         */
        btnShowPolls?.setOnClickListener {
            showMessage("Activity polls not implemented yet!")
        }

        /**
         * 9th May
         */
        btnRegisterShowNext?.setOnClickListener {
            startFollowingActivity()
        }

        val adapterChar = ArrayAdapter.createFromResource(this, R.array.languages, R.layout.custom_spinner_textview)
        adapterChar.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        spinnerLanguages.adapter = adapterChar

        spinnerLanguages.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View,
                position: Int,
                l: Long
            ) {
                val selectedLanguage: String = spinnerLanguages.getItemAtPosition(position).toString()
                Log.d("onCreate", "Clicked: $position, selectedLanguage, $selectedLanguage")

                if(isInitiated){
                    Log.d("onCreate", "User changed his preferred language.")
                    user?.let {
                        if(it.getLanguage().compareTo(selectedLanguage.toLowerCase())!=0) {
                            realmHandler.updateUserDAO(selectedLanguage.toLowerCase(), it)

                            finish()
                            startActivity(intent)
                        }
                    }
                }

                isInitiated = true
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

        screenHeight?.let {
            Picasso
                .get()
                .load(R.drawable.bg_planet_earth)
                .into(imgPlanetEarth)
        }?: kotlin.run {
            Log.e("onCreate", "height was not passed correctly")
        }

        /**
         * API call, TODO
         */
        listOfPeople?.let {
            it.layoutManager = LinearLayoutManager(this)
            val arrayList = mutableListOf<String>()
            arrayList.add("Jack (Britania)")
            arrayList.add("Livia (Moscow)")
            arrayList.add("Name (City)")
            arrayList.add("Jeremy (Boston)")
            arrayList.add("Jeremy (Boston)")
            arrayList.add("Jeremy (Boston)")
            arrayList.add("Jeremy (Boston)")
            arrayList.add("Jeremy (Boston)")
            arrayList.add("Jeremy (Boston)")

            it.adapter = PersonAdapter(arrayList)
        }

        realmHandler = RealmHandler(this)
        user = realmHandler.getUserDAO(RealmHandler.DEFAULT_ID)

        if(user == null){
            val languageCode = Locale.getDefault().language
            user = realmHandler.createUserDAO(languageCode)
            Log.d("onCreate", "Locale is $languageCode, new user DAO created.")
        }

        user?.let {
            val lang = it.getLanguage()
            Log.d("onCreate", "Locale is set to $lang.")
            setAllTextComponentsPerUserLocale(lang)
            setTextStyleNumberOfPeopleSupported(getLocalizedResources(lang))
            setTextStyleNumberOfPeopleSupportedToday(getLocalizedResources(lang))
            // set selection
            spinnerLanguages.setSelection(getPositionOfLanguage(lang))
        }?:run{
            Log.e("onCreate", "User is null, check for previous errors.")
        }
    }

    private fun startFollowingActivity(){
        val intent = Intent(this@StatisticalActivity, NinthMayActivity::class.java)
        startActivity(intent)
    }

    private fun getPositionOfLanguage(languageCode: String): Int{
        return when(languageCode){
            "en" -> 0
            "ru" -> 1
            "ua" -> 2
            "cs" -> 3
            "es" -> 4
            else -> 0
        }
    }

    private fun setAllTextComponentsPerUserLocale(languageCode: String){
        txtAmongThem?.text = getLocalizedResources(languageCode).getString(R.string.text_among_them)
        btnRegisterShowNext?.text = getLocalizedResources(languageCode).getString(R.string.text_sign_up_for_conference_and_show_previous)
    }

    private fun getLocalizedResources(
        languageCode: String
    ): Resources {
        val conf = resources.configuration
        conf.locale = Locale(languageCode)
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        return Resources(assets, metrics, conf)
    }

    private fun setTextStyleNumberOfPeopleSupported(resources: Resources){
        //TODO: Language and api call to get numbers
        val text_part1 = resources.getText(R.string.text_number_of_people_supported_1)
        val text_part2 = resources.getText(R.string.text_number_of_people_supported_2)
        val text_part3 = resources.getText(R.string.text_number_of_people_supported_3)
        val font = Typeface.createFromAsset(assets, "OpenSans-Bold.ttf")

        val partOne = "$text_part1 $NUMBER_OF_PEOPLE"
        val partTwo = "$partOne $text_part2"
        val partThree = "$partTwo $TOTAL_CITIES"
        val partFour = "$partThree $text_part3"

        val spannableString = SpannableString(partFour)
        spannableString.setSpan(CustomTypefaceSpan(font, resources.getColor(R.color.colorAlertText)), text_part1.count()+1, partOne.count(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(CustomTypefaceSpan(font, resources.getColor(R.color.colorAlertText)), partTwo.count()+1, partThree.count(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        txtNumberOfPeopleSupp?.text = spannableString
    }

    private fun setTextStyleNumberOfPeopleSupportedToday(resources: Resources){
        //TODO: Language and api call to get numbers
        val text_part1 = resources.getText(R.string.text_number_of_people_supported_today_1)
        val text_part2 = resources.getText(R.string.text_number_of_people_supported_today_2)
        val text_part3 = resources.getText(R.string.text_number_of_people_supported_today_3)
        val font = Typeface.createFromAsset(assets, "OpenSans-Bold.ttf")

        val partOne = "$text_part1 $NUMBER_OF_PEOPLE"
        val partTwo = "$partOne $text_part2"
        val partThree = "$partTwo $TOTAL_CITIES"
        val partFour = "$partThree $text_part3"

        val spannableString = SpannableString(partFour)
        spannableString.setSpan(CustomTypefaceSpan(font, resources.getColor(R.color.colorAlertText)), text_part1.count()+1, partOne.count(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(CustomTypefaceSpan(font, resources.getColor(R.color.colorAlertText)), partTwo.count()+1, partThree.count(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        txtNumberOfPeopleSuppToday?.text = spannableString
    }

    private fun showMessage(stringMessage: String){
        Log.e("RegisterActivity", stringMessage)
        val snackBar: Snackbar = Snackbar
            .make(rootLayout, stringMessage, Snackbar.LENGTH_LONG)
        snackBar.setActionTextColor(Color.RED)
        snackBar.show()
    }
}
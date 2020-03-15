package ua.allatra.allatraunites.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.upper_nav_menu_statistical.*
import ua.allatra.allatraunites.R

class StatisticalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistical)

        btnShowPolls?.setOnClickListener {
            showMessage("Activity polls not implemented yet!")
        }

        val adapterChar = ArrayAdapter.createFromResource(this, R.array.languages, R.layout.custom_spinner_text)
        adapterChar.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        spinnerLanguages.adapter = adapterChar

        spinnerLanguages.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View,
                position: Int,
                l: Long
            ) {
                val selectedLanguage: String = spinnerLanguages.getItemAtPosition(position).toString()
                Log.d("onCreate", "Clicked: $position,selectedLanguage, $selectedLanguage")
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        })
    }

    private fun showMessage(stringMessage: String){
        Log.e("RegisterActivity", stringMessage)
        val snackBar: Snackbar = Snackbar
            .make(rootLayout, stringMessage, Snackbar.LENGTH_LONG)
        snackBar.setActionTextColor(Color.RED)
        snackBar.show()
    }
}
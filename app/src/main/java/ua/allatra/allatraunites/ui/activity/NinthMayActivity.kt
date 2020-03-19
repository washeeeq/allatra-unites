package ua.allatra.allatraunites.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import ua.allatra.allatraunites.R
import ua.allatra.allatraunites.db.RealmHandler
import ua.allatra.allatraunites.ui.activity.ui.main.SectionsPagerAdapter

class NinthMayActivity : AppCompatActivity() {

    private lateinit var realmHandler: RealmHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ninth_may)
        realmHandler = RealmHandler(this)
        val user = realmHandler.getUserDAO(RealmHandler.DEFAULT_ID)
        var languageCode = "en"

        user?.let {
            languageCode = it.getLanguage()
        }?: kotlin.run {
            Log.e("onCreate","User is not initialized!!")
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, languageCode)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
    }
}
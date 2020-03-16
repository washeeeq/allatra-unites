package ua.allatra.allatraunites.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_splash_screen.*
import ua.allatra.allatraunites.R


class SplashScreenActivity : AppCompatActivity() {
    private var realWidth: Int? = null
    private var realHeight: Int? = null

    companion object {
        private const val DELAY_SPLASH = 2L
        const val SCREEN_HEIGHT = "real_height"
        const val SCREEN_WIDTH = "real_width"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        setScreenMetrics()

        Picasso
            .get()
            .load(R.drawable.bg_people)
            .into(imgSplashBackground)

        Handler().postDelayed(
            {
                val intent = Intent(this@SplashScreenActivity, RegisterActivity::class.java)
                val bundle = Bundle()
                bundle.putInt(SCREEN_HEIGHT, realHeight!!)
                bundle.putInt(SCREEN_WIDTH, realWidth!!)
                intent.putExtras(bundle)
                startActivity(intent)
                // close this activity
                finish()
            }, DELAY_SPLASH * 1000
        ) // wait for 3 seconds
    }

    private fun setScreenMetrics(){
        if (Build.VERSION.SDK_INT >= 17) { //new pleasant way to get real metrics
            val realMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getRealMetrics(realMetrics)
            realWidth = realMetrics.widthPixels
            realHeight = realMetrics.heightPixels
        } else { //This should be close, as lower API devices should not have window navigation bars
            realWidth = windowManager.defaultDisplay.width
            realHeight = windowManager.defaultDisplay.height
        }
    }

    fun getImage(imageName: String?): Int {
        return resources.getIdentifier(imageName, "drawable", packageName)
    }
}

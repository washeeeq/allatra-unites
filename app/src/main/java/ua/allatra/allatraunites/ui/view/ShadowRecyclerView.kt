package ua.allatra.allatraunites.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.recyclerview.widget.RecyclerView
import ua.allatra.allatraunites.R
import ua.allatra.allatraunites.util.ViewUtil

class ShadowRecyclerView: RecyclerView {
    constructor(context: Context) : super(context){
        initBackground()
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){
        initBackground()
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        initBackground()
    }

    private fun initBackground() {
        background = ViewUtil.generateBackgroundWithShadow(
            this, R.color.colorWhite,
            R.dimen.radius_corner, R.color.colorFormEdit, R.dimen.elevation, Gravity.BOTTOM
        )
    }
}
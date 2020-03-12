package ua.allatra.allatraunites.ui.fonts

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

abstract class AbstractMontserratTextView: AppCompatTextView {

    abstract fun getFontName(): String

    companion object {
        private val TAG = this::class.java.simpleName
    }

    private var mTypeface: Typeface? = null

    constructor(context: Context?) : super(context){
        setTypeFace()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        setTypeFace()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        setTypeFace()
    }

    private fun setTypeFace(){
        if (mTypeface == null){
            mTypeface = Typeface.createFromAsset(context?.assets, getFontName())
        }
        typeface = mTypeface
    }
}
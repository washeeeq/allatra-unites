package ua.allatra.allatraunites.ui.fonts

import android.content.Context
import android.util.AttributeSet

class OpenSansBoldTextView: AbstractMontserratTextView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun getFontName(): String {
        return "OpenSans-Bold.ttf"
    }
}
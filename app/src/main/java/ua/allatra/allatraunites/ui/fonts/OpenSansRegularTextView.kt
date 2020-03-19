package ua.allatra.allatraunites.ui.fonts

import android.content.Context
import android.util.AttributeSet

class OpenSansRegularTextView: AbstractMontserratTextView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun getFontName(): String {
        return "OpenSans-Regular.ttf"
    }
}
package ua.allatra.allatraunites.ui.fonts

import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.MetricAffectingSpan

class CustomTypefaceSpan: MetricAffectingSpan {

    private var typeface: Typeface? = null
    private var color: Int

    constructor(typeface: Typeface, color: Int){
        this.typeface = typeface
        this.color = color
    }

    override fun updateDrawState(ds: TextPaint) {
        applyCustomTypeFace(ds, typeface)
    }

    override fun updateMeasureState(paint: TextPaint) {
        applyCustomTypeFace(paint, typeface)
    }

    private fun applyCustomTypeFace(paint: Paint, tf: Typeface?) {
        paint.typeface = tf
        paint.color = color
    }
}
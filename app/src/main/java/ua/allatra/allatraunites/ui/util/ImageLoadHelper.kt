package ua.allatra.allatraunites.ui.util

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log

object ImageLoadHelper {
    private val TAG = this::class.java.simpleName

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        // Raw height and width of image
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if(height != 0 && width != 0) {
            if (height > reqHeight || width > reqWidth) {
                val halfHeight: Int = height / 2
                val halfWidth: Int = width / 2

                // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                // height and width larger than the requested height and width.
                while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                    inSampleSize *= 2
                }
            }

            Log.d(
                "ImageLoadHelper",
                "screenWidth = $reqWidth, screenHeight = $reqHeight, pic_height = $height, pic_width = $width"
            )
            Log.d("ImageLoadHelper", "inSampleSize = $inSampleSize")
        } else {
            Log.e(TAG, "Cannot downSample! height und width is null")
        }
        return inSampleSize
    }

    fun decodeSampledBitmapFromResource(
        resources: Resources,
        resId: Int,
        reqWidth: Int,
        reqHeight: Int
    ): Bitmap {
        Log.d(TAG, "Resources: $resources, resId = $resId, reqWidth = $reqWidth, reqHeight = $reqHeight")
        // First decode with inJustDecodeBounds=true to check dimensions
        return BitmapFactory.Options().run {
            inJustDecodeBounds = true
            BitmapFactory.decodeResource(resources, resId, this)

            // Calculate inSampleSize
            inSampleSize = calculateInSampleSize(this, reqWidth, reqHeight)

            // Decode bitmap with inSampleSize set
            inJustDecodeBounds = false

            BitmapFactory.decodeResource(resources, resId, this)
        }
    }

    fun getScaledBitmap(bitmap: Bitmap, imageViewHeight: Int, edgePercent: Double): Bitmap{
        val destinationHeight = (imageViewHeight + (imageViewHeight * edgePercent)).toInt()
        val scale = destinationHeight / bitmap.height.toDouble()
        val destinationWidth = (scale * bitmap.width).toInt()
        Log.d(TAG, "Bitmap original height: ${bitmap.height}, width: ${bitmap.width}.")
        Log.d(TAG, "Bitmap new H: ${destinationHeight}, width: ${destinationWidth}, scale = $scale")
        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, destinationWidth, destinationHeight, true)

        val cut = destinationWidth - destinationHeight + (destinationWidth * edgePercent)
        val cutFrom = (cut).toInt() / 2
        val cutTo = scaledBitmap.width - cutFrom * 2
        Log.d(TAG, "cutFrom: ${cutFrom}, cutTo: ${cutTo}.")
        val croppedBitmap: Bitmap = Bitmap.createBitmap(scaledBitmap, cutFrom, 0, cutTo, scaledBitmap.height)
        Log.d(TAG, "Bitmap new H: ${croppedBitmap.height}, width: ${croppedBitmap.width}, scale = $scale")

        return croppedBitmap
    }
}
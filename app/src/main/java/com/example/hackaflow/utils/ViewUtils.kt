package com.example.hackaflow.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.TouchDelegate
import android.view.View


object ViewUtils {

    /**
     * Delegates touchable area
     * Make sure the views are completely inflated before call
     *
     * @param context       Context to get device specific display metrics
     * @param parentView    the parent view of the view expand touchable area
     * @param view          the view expand the touchable area
     * @param view          the view expand the touchable area
     * @param extraLeftDp   the view expand the touchable area in dps
     * @param extraTopDp    the view expand the touchable area in dps
     * @param extraTopDp    the view expand the touchable area in dps
     * @param extraBottomDp the view expand the touchable area in dps
     */
    fun expandTouchArea(
        context: Context,
        parentView: View,
        view: View,
        extraLeftDp: Int,
        extraRightDp: Int,
        extraTopDp: Int,
        extraBottomDp: Int
    ) {
        parentView.post(Runnable {
            try {
                val rect = Rect()
                view.getHitRect(rect)
                rect.left -= convertDpToPx(extraLeftDp.toFloat(), context).toInt()
                rect.top -= convertDpToPx(extraTopDp.toFloat(), context).toInt()
                rect.right += convertDpToPx(extraRightDp.toFloat(), context).toInt()
                rect.bottom += convertDpToPx(extraBottomDp.toFloat(), context).toInt()
                parentView.touchDelegate = TouchDelegate(rect, view)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    fun convertDpToPx(dp: Float, context: Context): Float {
        val resources: Resources = context.resources
        val metrics: DisplayMetrics = resources.displayMetrics
        return dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}
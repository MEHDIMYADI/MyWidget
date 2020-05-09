package ir.dimyadi.mywidget.util

import android.graphics.PixelFormat
import android.os.Build
import android.view.WindowManager

/**
 * @author MEHDIMYADI
 **/

class Initializer  // the x and y coordinates of the mtouch should be traced here.... so as to share with the whole app
//    and to capitalize on that when is needed to update the view, since once the view is update the
//    x and y coordinates are reset and the param.x and param.y in motion touch are neglected.
//    hence the change in param. and param.y should happen here
{
    private var xCoordinate = 0
    private var yCoordinate = 0
    fun getXCoordinate(): Int {
        return xCoordinate
    }

    fun setXCoordinate(xCoordinate: Int) {
        this.xCoordinate = xCoordinate
    }

    fun getYCoordinate(): Int {
        return yCoordinate
    }

    fun setYCoordinate(yCoordinate: Int) {
        this.yCoordinate = yCoordinate
    }

    //    try to make this go to the params innitializer
    fun wmInitializer(width: Int, height: Int): WindowManager.LayoutParams {
        /*The type of windowmanager to be used
         * depending on the
         * android version*/
        val layoutParam: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_PHONE
        }
        val params = WindowManager.LayoutParams(
            width,
            height,
            layoutParam,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        // setting the mtouch button on top left
        params.x = xCoordinate
        params.y = yCoordinate
        return params
    }
}
package ir.dimyadi.mywidget.views

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.RelativeLayout
import ir.dimyadi.mywidget.R
import ir.dimyadi.mywidget.util.MyAssistiveTouch
import ir.dimyadi.mywidget.util.Initializer

/**
 * @author MEHDIMYADI
 **/

class CustomView(
    private val overplayedButton: ImageView,
    private val windowManager: WindowManager,
    private val relativeLayout: RelativeLayout,
    private val paramsInitializer: Initializer
) {
    private var tempoView: View? = null
    fun initializeCustomView(
        inflater: LayoutInflater,
        params_image: RelativeLayout.LayoutParams,
        settingButton: MyAssistiveTouch,
        context: Context?
    ) {

//        We need to make the window mananger occupy the whole screen for this to work
//        paramsInnitializer paramsInnitializer = new paramsInnitializer();
        var settingButton = settingButton
        val params = paramsInitializer.wmInitializer(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        //        but it should be noted that the window menanger layout parameters should be returned to WrapContnent
//        once the custom window is closed. So as to gain access to the screen while the mtouch is returned
//        final View popview = inflater.inflate(R.layout.custom_window_alternative, null);
        val popview = inflater.inflate(R.layout.assistive_touch, null)

        /*relative layout for the menu getting the params of windowmanager
         * and adding rules*/
        val viewParams = RelativeLayout.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

//        viewParams.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE); // this is to put with the alternate view
        viewParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE)
        viewParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE)
        if (settingButton.isEnable) {
            settingButton = MyAssistiveTouch(
                popview,
                relativeLayout,
                overplayedButton,
                paramsInitializer,
                windowManager,
                context
            )
            /*adding the popview menu
             * together with its
             * viewparams
             * to the main relative layout*/relativeLayout.removeAllViewsInLayout()
            windowManager.removeView(relativeLayout)
            relativeLayout.addView(popview, viewParams)
            windowManager.addView(relativeLayout, params)
            //            windowManager.updateViewLayout(relativeLayout, params);
            tempoView = popview
            settingButton.isEnable = false
            settingButton.settingButton()
            settingButton.isEnable = false
        } else {
            params_image.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT)
            relativeLayout.removeView(tempoView)
            settingButton.isEnable = true
        }
    }

    //removing the  custom view after using the services in setting Button
    fun removeCustomView() {
        val paramsImage = RelativeLayout.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        paramsImage.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE)
        val params = paramsInitializer.wmInitializer(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        params.gravity = Gravity.START
        //                windowManager.updateViewLayout(relativeLayout, params);
        windowManager.removeView(relativeLayout)
        relativeLayout.addView(overplayedButton, paramsImage)
        windowManager.addView(relativeLayout, params)
    }

}
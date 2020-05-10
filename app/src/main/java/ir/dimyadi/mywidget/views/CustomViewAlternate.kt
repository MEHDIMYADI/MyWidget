package ir.dimyadi.mywidget.views

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.RelativeLayout
import ir.dimyadi.mywidget.R
import ir.dimyadi.mywidget.util.Initializer
import ir.dimyadi.mywidget.util.MyAssistiveTouch

/**
 * @author MEHDIMYADI
 **/

class CustomViewAlternate(
    private val overLacedButton: ImageView,
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
        var settingButton = settingButton
        val params = paramsInitializer.wmInitializer(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        val popView = inflater.inflate(R.layout.assistive_touch, null)

        /*relative layout for the menu getting the params of windowmanager
         * and adding rules*/
        val viewParams = RelativeLayout.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        viewParams.addRule(
            RelativeLayout.ALIGN_PARENT_START,
            RelativeLayout.TRUE
        ) // this is to put with the alternate view

//        viewParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
//        viewParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        if (settingButton.isEnable) {
            settingButton = MyAssistiveTouch(
                popView,
                relativeLayout,
                overLacedButton,
                paramsInitializer,
                windowManager,
                context
            )
            /*adding the popview menu
             * together with its
             * viewparams
             * to the main relative layout*/relativeLayout.removeAllViewsInLayout()
            //            windowManager.removeView(relativeLayout); //this is used in custom view
            relativeLayout.addView(popView, viewParams)
            params.gravity = Gravity.START
            //            windowManager.addView(relativeLayout, params); // same! used in the custom view
            windowManager.updateViewLayout(relativeLayout, params)
            tempoView = popView
            settingButton.isEnable = false
            settingButton.settingButton()
            settingButton.isEnable = false
        } else {
            params_image.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT)
            relativeLayout.removeView(tempoView)
            settingButton.isEnable = true
        }
    }

    //removing the  custom alternative view after using the services in setting Button
    //fun removeCustomeView() {
    //    val params_imageview = RelativeLayout.LayoutParams(
    //        WindowManager.LayoutParams.WRAP_CONTENT,
    //        WindowManager.LayoutParams.WRAP_CONTENT
    //    )
    //    params_imageview.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE)
    //    val params = paramsInitializer.wmInnitializer(
    //        WindowManager.LayoutParams.WRAP_CONTENT,
    //        WindowManager.LayoutParams.WRAP_CONTENT
    //    )
    //    //        params.gravity = Gravity.START;
    //    windowManager.updateViewLayout(relativeLayout, params)
    //    //        windowManager.removeView(relativeLayout);
    //    relativeLayout.addView(overLacedButton, params_imageview)
    //    //        windowManager.addView(relativeLayout, params);
    //}

}
package ir.dimyadi.mywidget.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Point
import android.os.IBinder
import android.view.*
import android.view.View.OnTouchListener
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import ir.dimyadi.mywidget.R
import ir.dimyadi.mywidget.util.Initializer
import ir.dimyadi.mywidget.util.MyAssistiveTouch
import ir.dimyadi.mywidget.util.StringUtil.NAME
import ir.dimyadi.mywidget.util.StringUtil.PREF_NAME
import ir.dimyadi.mywidget.views.CustomViewAlternate

/**
 * @author MEHDIMYADI
 **/

class MyAssistiveTouchService : Service(), OnTouchListener, View.OnClickListener {

    private var paramsImage: RelativeLayout.LayoutParams? = null
    private var relativeLayout: RelativeLayout? = null
    private var overplayedButton: ImageView? = null
    private var moving = false
    private var windowManager: WindowManager? = null
    private var initialX = 0
    private var initialY = 0
    private var initialTouchX = 0f
    private var initialTouchY = 0f
    private var mWidth = 0
    private var sharedPreferences: SharedPreferences? = null
    private var settingButton: MyAssistiveTouch? = null
    private var restartService: MyAssistiveRestartService? = null
    private val btnOverlay = View.generateViewId()
    private val paramsInnitializer = Initializer()

    /// find a way to get rid of tempoview
    override fun onBind(intent: Intent): IBinder? {
        settingButton!!.isEnable = false
        return null
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate() {
        /*initializing  restart service
         * and
         * floating settingButton classes*/
        restartService = MyAssistiveRestartService(this)
        settingButton = MyAssistiveTouch(this)
        settingButton!!.isEnable = true
        /*
         * create
         * the
         * floating touch*/createMtouch()
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    private fun createMtouch() {

        /*windowmanager
         * to allow
         * drawing on the screen*/
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        /*initializing
         * imageview
         * to be displayed
         * and giving it params*/overplayedButton = ImageView(this)
        overplayedButton!!.setImageResource(R.drawable.drawble_icon)
        //setting id for the overlay button
        overplayedButton!!.id = btnOverlay
        overplayedButton!!.alpha = 0.7f
        overplayedButton!!.setOnTouchListener(this)
        overplayedButton!!.setOnClickListener(this)


        /*setting
         *Windowmanager params*/
//        paramsInnitializer paramsInnitializer = new paramsInnitializer();
        paramsInnitializer.xCoordinate = 0
        paramsInnitializer.yCoordinate = 150
        val params = paramsInnitializer.wmInitializer(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        params.gravity = Gravity.START or Gravity.TOP

        /*relative layout getting the params of windowmanager*/relativeLayout = RelativeLayout(this)
        paramsImage = RelativeLayout.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        paramsImage!!.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE)
        /*adding view to the relative layout*/relativeLayout!!.addView(
            overplayedButton,
            paramsImage
        )
        /*adding
         * the
         * relative layout inside the windowmanager*/windowManager!!.addView(relativeLayout, params)

        /*get screen width */screenWidth
    }

    //get screen width
    private val screenWidth: Unit
        get() {
            //get screen width
            val display = windowManager!!.defaultDisplay
            val size = Point()
            display.getSize(size)
            val vto = relativeLayout!!.viewTreeObserver
            vto.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    relativeLayout!!.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    val width = relativeLayout!!.measuredWidth
                    mWidth = size.x - width
                }
            })
        }

    override fun onTaskRemoved(rootIntent: Intent) {
        super.onTaskRemoved(rootIntent)
        /*on clearing recent apps
         * check if the service prefernce
         * was saved then restart it*/sharedPreferences =
            getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        //String d = sharedPreferences.getString(NAME, "");
        /*
                * restart the service*/
        if (sharedPreferences!!.contains(NAME)) restartService!!.restart() else {
            Toast.makeText(this, "Oop!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (overplayedButton != null) {
            windowManager!!.removeView(relativeLayout)
            overplayedButton = null
        }
    }

    /*onclick
     * show the menu*/
    override fun onClick(view: View) {
        showWindow()
    }

    private fun showWindow() {

        /*inflate the layout*/
        val inflater = (baseContext
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
        ////        We need to make the window mananger occupy the whole screen for this to work
////        paramsInnitializer paramsInnitializer = new paramsInnitializer();
//        WindowManager.LayoutParams params = paramsInnitializer.wmInnitializer(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
////        but it should be noted that the window menanger layout parameters should be returned to WrapContnent
////        once the custom window is closed. So as to gain access to the screen while the mtouch is returned
////        final View popview = inflater.inflate(R.layout.custom_window_alternative, null); //the original
//        final View popview = inflater.inflate(R.layout.custom_window, null);
//
//        /*relative layout for the menu getting the params of windowmanager
//         * and adding rules*/
//        RelativeLayout.LayoutParams viewParams = new RelativeLayout.LayoutParams(
//                WindowManager.LayoutParams.WRAP_CONTENT,
//                WindowManager.LayoutParams.WRAP_CONTENT);
//
////        viewParams.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE); // this is to put with the alternate view
//
//        viewParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
//        viewParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
//
//        if (settingButton.isEnable()) {
//            settingButton = new SettingButton(popview, relativeLayout, overlayedButton, paramsInnitializer, windowManager, this);
//            /*adding the popview menu
//             * together with its
//             * viewparams
//             * to the main relative layout*/
//            relativeLayout.removeAllViewsInLayout();
//            windowManager.removeView(relativeLayout);
//            relativeLayout.addView( popview, viewParams);
//            windowManager.addView(relativeLayout, params);
////            windowManager.updateViewLayout(relativeLayout, params);
//            tempoView = popview;
//            settingButton.setEnable(false);
//            settingButton.settingButton();
//            settingButton.setEnable(false);
//
//        } else {
//            params_imageview.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//            relativeLayout.removeView( tempoView);
//            settingButton.setEnable(true);
//        }

//        CustomView customView = new CustomView(overlayedButton, windowManager, relativeLayout, paramsInnitializer);
        val customViewAlternate =
            CustomViewAlternate(
                overplayedButton!!,
                windowManager!!,
                relativeLayout!!,
                paramsInnitializer
            )
        //        customView.Innitialize_CustomView(inflater, params_imageview, settingButton,this);
        customViewAlternate.initializeCustomView(
            inflater,
            paramsImage!!,
            settingButton!!,
            this
        )
    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        val params =
            relativeLayout!!.layoutParams as WindowManager.LayoutParams
        if (motionEvent.action == MotionEvent.ACTION_DOWN) {

            //initial position
            initialX = paramsInnitializer.xCoordinate
            initialY = paramsInnitializer.yCoordinate

            //touch locationn
            initialTouchX = motionEvent.rawX
            initialTouchY = motionEvent.rawY
            return moving
        } else if (motionEvent.action == MotionEvent.ACTION_MOVE) {

            //calculate x and y coordinate of view
            paramsInnitializer.xCoordinate = initialX + (motionEvent.rawX - initialTouchX).toInt()
            paramsInnitializer.yCoordinate = initialY + (motionEvent.rawY - initialTouchY).toInt()
            params.x = paramsInnitializer.xCoordinate
            params.y = paramsInnitializer.yCoordinate


            //update the layout
            windowManager!!.updateViewLayout(relativeLayout, params)
            moving = false
        } else if (motionEvent.action == MotionEvent.ACTION_UP) {

            //position to left | right always
            mWidth / 2
            //val nearestXWall = (if (params.x >= middle) mWidth else 0.toFloat()).toFloat()
            //params.x = nearestXWall.toInt()
            paramsInnitializer.xCoordinate = params.x
            windowManager!!.updateViewLayout(relativeLayout, params)
            return moving
        }
        return false
    }
}
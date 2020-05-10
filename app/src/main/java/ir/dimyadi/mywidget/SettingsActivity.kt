package ir.dimyadi.mywidget

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.ActivityManager
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ir.dimyadi.mywidget.service.MyAssistiveTouchService
import ir.dimyadi.mywidget.util.StringUtil
import ir.dimyadi.mywidget.util.StringUtil.NAME
import ir.dimyadi.mywidget.util.StringUtil.REQUEST_CODE

/**
 * @author MEHDIMYADI
 **/

open class SettingsActivity : AppCompatActivity() {
    private var button: Button? = null
    var sharedPreferences: SharedPreferences? = null
    private var dialog: Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_settings)
        dialog = Dialog(this)
        // checking floating or overlay permission
        checkDrawOverlayPermission()
        button = findViewById(R.id.button)
        button!!.setOnClickListener {
            if (Settings.canDrawOverlays(this@SettingsActivity)) {
                /*create a lifetime running service*/
                createRunningService()
            } else {
                Toast.makeText(this@SettingsActivity, "error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        /* check if the service is on or off on resume and change the text on the button*/
        val getServiceState = isMyServiceRunning
        if (getServiceState) {
            button!!.text = getString(R.string.on)
            startService(Intent(this@SettingsActivity, MyAssistiveTouchService::class.java))
        } else {
            button!!.text = getString(R.string.off)
            stopService(Intent(this@SettingsActivity, MyAssistiveTouchService::class.java))
        }
    }

    //fun restart() {
    //    /*get the intent to be restarted */
    //    val restartServiceIntent = Intent(
    //        this,
    //        MyAssistiveTouchService::class.java
    //    )
    //    restartServiceIntent.setPackage(applicationContext.toString())
    //    val restartServicePendingIntent = PendingIntent.getService(
    //        applicationContext, 1, restartServiceIntent,
    //        PendingIntent.FLAG_ONE_SHOT
    //    )
    //    /*Alam manager to restart the service
    //     * whenever it is closed*/
    //    val alarmService = applicationContext
    //        .getSystemService(Context.ALARM_SERVICE) as AlarmManager
    //    alarmService[AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 1000] =
    //        restartServicePendingIntent
    //}

    /*check if my service is running
     * */
    private val isMyServiceRunning: Boolean
        get() {
            val manager =
                getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            for (service in manager.getRunningServices(Int.MAX_VALUE)) {
                if (MyAssistiveTouchService::class.java.name == service.service.className) {
                    return true
                }
            }
            return false
        }

    /*floating/overlayed permission request
     * */
    private fun checkDrawOverlayPermission() {
        /* check if we already  have permission to draw over other apps */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                /* if not construct intent to request permission */
                val intent =
                    Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                /* request permission via start activity for result */startActivityForResult(
                    intent,
                    REQUEST_CODE
                )
            }
        }
        // createRunningService();
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {

        /*request permission if granted or not*/
        if (requestCode == REQUEST_CODE) {
            //not set
            if (!Settings.canDrawOverlays(this)) {
                finish()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    @SuppressLint("ResourceAsColor")
    protected fun createRunningService() {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences(StringUtil.PREF_NAME, Context.MODE_PRIVATE)
        val getServiceState = isMyServiceRunning
        /*check the state of the service if running or not
         * */if (getServiceState) {
            stopService(Intent(this@SettingsActivity, MyAssistiveTouchService::class.java))
            val editor = sharedPreferences.edit()
            editor.putString(NAME, "off")
            editor.apply()
            button!!.text = getString(R.string.off)
        } else {
            startService(Intent(this@SettingsActivity, MyAssistiveTouchService::class.java))
            val editor = sharedPreferences.edit()
            editor.putString(NAME, "on")
            editor.apply()
            button!!.text = getString(R.string.on)
        }
    }

    //fun howToUse(view: View?) {
//        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    //Objects.requireNonNull(dialog.getWindow())
    //        .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    //dialog.setContentView(R.layout.how_to_use_popup);
    //dialog.setCanceledOnTouchOutside(false);
    //dialog.show();
    //}

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_about -> {
                val intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
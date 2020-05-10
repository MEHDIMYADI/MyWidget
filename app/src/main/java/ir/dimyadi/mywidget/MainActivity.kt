package ir.dimyadi.mywidget

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ir.dimyadi.mywidget.service.MyAssistiveTouchService
import ir.dimyadi.mywidget.util.StringUtil.NAME
import ir.dimyadi.mywidget.util.StringUtil.PREF_NAME
import java.util.*

/**
 * @author MEHDIMYADI
 **/

open class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val configuration: Configuration = resources.configuration
        configuration.setLayoutDirection(Locale("fa"))
        //resources.updateConfiguration(configuration, resources.displayMetrics)

        val checkIfrunningAfterBoot = checkIfrunningAfterBoot()
        val getServiceState = isMyServiceRunning()

        if (!isSystemNotificationPolicyAccess() || !isSystemAlertPermissionGranted(this)) {
            val intent = Intent(this, PermissionActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            //val intent = Intent(this, DialogActivity::class.java)
            ///startActivity(intent)
            //finish()

            if (checkIfrunningAfterBoot) {
                createRunningService()
            }
            if (getServiceState) {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        val getServiceState = isMyServiceRunning()
        if (getServiceState) {
            startService(Intent(this@MainActivity, MyAssistiveTouchService::class.java))
        } else {
            stopService(Intent(this@MainActivity, MyAssistiveTouchService::class.java))
        }
    }

    @SuppressLint("ResourceAsColor")
    protected fun createRunningService() {
        val getServiceState = isMyServiceRunning()
        /*check the state of the service if running or not
         * */
        /*check the state of the service if running or not
         * */if (getServiceState) {
            stopService(Intent(this@MainActivity, MyAssistiveTouchService::class.java))
        } else {
            startService(Intent(this@MainActivity, MyAssistiveTouchService::class.java))
        }
    }

    private fun checkIfrunningAfterBoot(): Boolean {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        //if (sharedPreferences.contains(NAME)) {
        val d = sharedPreferences.getString(NAME, "")
        if (d.equals("on")) {
            val mfloat = Intent(this, MyAssistiveTouchService::class.java)
            startService(mfloat)
        } else if (isSystemNotificationPolicyAccess() && isSystemAlertPermissionGranted(this)) {
            val intent = Intent(this, DialogActivity::class.java)
            startActivity(intent)
        }
        //}
        return false
    }

    open fun isMyServiceRunning(): Boolean {
        val manager =
            getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (MyAssistiveTouchService::class.java.name == service.service.className) {
                return true
            }
        }
        return false
    }

    // Check if Optimization enabled, open optimizations Activity
    fun checkBattery() {
        //if (isBatteryOptimized() && VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP_MR1) {
        if (checkBatteryOptimized()) {
            val name = resources.getString(R.string.app_name)
            Toast.makeText(
                applicationContext,
                "Battery optimization -> All apps -> $name -> Don't optimize",
                Toast.LENGTH_LONG
            ).show()
            val intent = Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS)
            startActivity(intent)
        }
    }

    // Check if optimization is enabled
    private fun checkBatteryOptimized(): Boolean {
        val pwrm = applicationContext.getSystemService(Context.POWER_SERVICE) as PowerManager
        val name = applicationContext.packageName
        //if (VERSION.SDK_INT >= VERSION_CODES.M) {
        return !pwrm.isIgnoringBatteryOptimizations(name)
        //}
        //return false
    }

    // Ringtone status change permission
    private fun isSystemNotificationPolicyAccess(): Boolean {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        return notificationManager.isNotificationPolicyAccessGranted
    }

    // Display Over app
    private fun isSystemAlertPermissionGranted(context: Context?): Boolean {
        return Settings.canDrawOverlays(context)
    }
}

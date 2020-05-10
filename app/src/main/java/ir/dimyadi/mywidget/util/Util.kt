package ir.dimyadi.mywidget.util

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.*
import android.os.Build
import android.util.Log
import io.github.persiancalendar.calendar.CivilDate
import ir.dimyadi.mywidget.service.MyAssistiveJobService
import ir.dimyadi.mywidget.service.MyAssistiveTouchService
import ir.dimyadi.mywidget.util.StringUtil.NAME
import ir.dimyadi.mywidget.util.StringUtil.PREF_NAME
import java.util.*

/**
 * @author MEHDIMYADI
 **/

object Util {
    private const val REFRESH_INTERVAL = 5 * 1000.toLong()

    /*Jobschedule
     * to make a continous
     * running
     * service*/
    fun scheduleJob(context: Context) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val serviceComponent =
                    ComponentName(context, MyAssistiveJobService::class.java)
                val builder = JobInfo.Builder(0, serviceComponent)
                builder.setMinimumLatency(1000) //wait at least 1 sec
                builder.setPeriodic(REFRESH_INTERVAL)
                builder.setOverrideDeadline(7 * 1000.toLong()) // maximum delay 3 sec
                builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED) // require unmetered network
                builder.setRequiresDeviceIdle(false) // device should be idle
                builder.setRequiresCharging(false) // we don't care if the device is charging or not
                val jobScheduler = context.getSystemService(
                    JobScheduler::class.java
                )!!
                jobScheduler.schedule(builder.build())
                checkIfRunningAfterBoot(context)
            }
        } catch (e: Exception) {
            Log.i(ContentValues.TAG, "scheduleJob: argument exception error")
        }
    }

    /*check if the service is running
     * after reboot or boot
     * and restart it if it was enabled */
    private fun checkIfRunningAfterBoot(context: Context) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        if (sharedPreferences.contains(NAME)) {
            val d = sharedPreferences.getString(NAME, "")
            if (d!!.contains("on")) {
                val intent = Intent(context, MyAssistiveTouchService::class.java)
                context.startService(intent)
            } else {
                Log.i(ContentValues.TAG, "scheduleJob: shared preference is OFF")
            }
        }
    }
}

fun Calendar.toCivilDate() = CivilDate(
    get(Calendar.YEAR),
    get(Calendar.MONTH) + 1,
    get(Calendar.DAY_OF_MONTH)
)
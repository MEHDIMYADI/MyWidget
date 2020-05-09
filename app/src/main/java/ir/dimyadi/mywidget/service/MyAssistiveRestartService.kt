package ir.dimyadi.mywidget.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock

/**
 * @author MEHDIMYADI
 **/

class MyAssistiveRestartService(private val context: Context) {
    fun restart() {
        /*get the intent to be restarted */
        val restartServiceIntent = Intent(
            context.applicationContext,
            this.javaClass
        )
        restartServiceIntent.setPackage(context.packageName)
        val restartServicePendingIntent = PendingIntent.getService(
            context.applicationContext, 1, restartServiceIntent,
            PendingIntent.FLAG_ONE_SHOT
        )
        /*Alam manager to restart the service
         * whenever it is closed*/
        val alarmService = context.applicationContext
            .getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmService[AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 1000] =
            restartServicePendingIntent
    }
}
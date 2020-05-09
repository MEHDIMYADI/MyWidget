package ir.dimyadi.mywidget.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import ir.dimyadi.mywidget.service.MyAssistiveTouchService
import ir.dimyadi.mywidget.util.Util

/**
 * @author MEHDIMYADI
 **/

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_BOOT_COMPLETED == intent.action) {
            Util.scheduleJob(context)
        } else {
            context.startService(Intent(context, MyAssistiveTouchService::class.java))
        }
    }
}
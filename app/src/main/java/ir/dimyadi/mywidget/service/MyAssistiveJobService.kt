package ir.dimyadi.mywidget.service

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import ir.dimyadi.mywidget.receiver.BootReceiver
import ir.dimyadi.mywidget.util.Util

/**
 * @author MEHDIMYADI
 **/

class MyAssistiveJobService : JobService() {
    /*on job started */
    override fun onStartJob(jobParameters: JobParameters): Boolean {
        //it has no use....check it once more
        val service = Intent(applicationContext, BootReceiver::class.java)
        applicationContext.startService(service)
        // reschedule the job
        Util.scheduleJob(applicationContext)
        return true
    }

    override fun onStopJob(jobParameters: JobParameters): Boolean {
        return true
    }
}
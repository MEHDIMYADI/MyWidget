package ir.dimyadi.mywidget.service

import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import android.service.quicksettings.TileService
import ir.dimyadi.mywidget.MainActivity

/**
 * @author MEHDIMYADI
 **/

@TargetApi(Build.VERSION_CODES.N)
class TileService : TileService() {
    override fun onClick() = try {
        super.onClick()
        startActivityAndCollapse(
            Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
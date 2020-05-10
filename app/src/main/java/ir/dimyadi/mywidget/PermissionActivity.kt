package ir.dimyadi.mywidget

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.material.snackbar.Snackbar

/**
 * @author MEHDIMYADI
 **/

class PermissionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)

        var snack = ""

        val ringLayout = findViewById<CardView>(R.id.about_app)
        val popLayout = findViewById<CardView>(R.id.pop_per)
        val popLayoutXiaomi = findViewById<CardView>(R.id.pop_per_xiaomi)
        ringLayout.visibility = View.VISIBLE
        popLayout.visibility = View.GONE
        popLayoutXiaomi.visibility = View.GONE
        ringLayout.setOnClickListener { ringPermissions() }
        val tArrow: ImageView = findViewById(R.id.imageViewArrow)
        tArrow.startAnimation(AnimationUtils.loadAnimation(this, R.anim.arrow))
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (notificationManager.isNotificationPolicyAccessGranted) {
            if (Build.MANUFACTURER == "Xiaomi") {
                ringLayout.visibility = View.GONE
                popLayout.visibility = View.GONE
                popLayoutXiaomi.visibility = View.VISIBLE
                val mImageView: ImageView =
                    findViewById(R.id.imageView)
                mImageView.setImageDrawable(resources.getDrawable(R.drawable.per_image_xia))
                val frameAnimation = mImageView.drawable as AnimationDrawable
                frameAnimation.start()
                val popPERXia = findViewById<CardView>(R.id.pop_per_xiaomi)
                popPERXia.setOnClickListener { checkDisplayOAppsBackgroundXiaomi() }
                snack = getString(R.string.per_info_one)
            } else {
                ringLayout.visibility = View.GONE
                popLayout.visibility = View.VISIBLE
                popLayoutXiaomi.visibility = View.GONE
                val popPER = findViewById<CardView>(R.id.pop_per)
                popPER.setOnClickListener { popPermissions() }
                snack = getString(R.string.per_info_one)
            }
        } else {
            snack = getString(R.string.per_info_two)
        }

        val parentLayout = findViewById<LinearLayout>(R.id.mainLayout)
        Snackbar.make(parentLayout, snack, Snackbar.LENGTH_LONG)
            .setAction(R.string.about) {
                val intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)
            }
            .show()
    }

    private fun popPermissions() {
        try {
            if (Build.MANUFACTURER == "Xiaomi") {
                checkDisplayOAppsBackgroundXiaomi()
            } else {
                val intent =
                    Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:$packageName")
                    )
                startActivity(intent)
                finish()
            }
        } catch (e: SecurityException) {

        }
    }

    private fun ringPermissions() {
        try {
            val notificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (!notificationManager.isNotificationPolicyAccessGranted) {
                // Open Setting screen to ask for permission
                val intent =
                    Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
                startActivityForResult(
                    intent,
                    ON_DO_NOT_DISTURB_CALLBACK_CODE
                )
            }
        } catch (e: SecurityException) {

        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ON_DO_NOT_DISTURB_CALLBACK_CODE) {
            val notificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (notificationManager.isNotificationPolicyAccessGranted) {
                finish()
            }
        }
    }

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

    private fun checkDisplayOAppsBackgroundXiaomi() {
        val intent = Intent("miui.intent.action.APP_PERM_EDITOR")
        intent.setClassName(
            "com.miui.securitycenter",
            "com.miui.permcenter.permissions.PermissionsEditorActivity"
        )
        intent.putExtra("extra_pkgname", packageName)
        startActivity(intent)
        //val myAppSettings = Intent(
        //    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        //    Uri.parse("package:$packageName")
        //)
        //myAppSettings.addCategory(Intent.CATEGORY_DEFAULT)
        //myAppSettings.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        //startActivity(myAppSettings)
    }

    companion object {
        private const val ON_DO_NOT_DISTURB_CALLBACK_CODE = 101
    }
}
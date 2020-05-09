package ir.dimyadi.mywidget

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.view.Gravity.CENTER_HORIZONTAL
import android.view.View
import android.view.View.TEXT_ALIGNMENT_CENTER
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.R.id.snackbar_text
import com.google.android.material.snackbar.Snackbar
import ir.dimyadi.mywidget.calendar.*
import ir.dimyadi.mywidget.events.EventsAdapter
import ir.dimyadi.mywidget.events.SimpleDividerItemDecoration
import ir.dimyadi.mywidget.monthweekname.EnMonthWeekN
import ir.dimyadi.mywidget.monthweekname.IslamicMonthWeekN
import ir.dimyadi.mywidget.monthweekname.PersianMonthWeekN
import ir.dimyadi.mywidget.util.RingVolumeSwitchWidgetPreferences
import ir.dimyadi.mywidget.util.VolumeSwitchWidgetPreferences
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.*

/**
 * @author MEHDIMYADI
 **/

@SuppressLint("SimpleDateFormat")
class DialogActivity : AppCompatActivity() {

    private val gCalendar = EnMonthWeekN()
    private val shCalendar =
        PersianMonthWeekN()
    private val hCalendar = IslamicMonthWeekN()

    private val civilDate = CivilDate(Calendar.getInstance())
    private var civilYear = civilDate.year
    private var civilMonth = civilDate.month
    private var civilDay = civilDate.dayOfMonth
    private val today: String = "$civilYear/$civilMonth/$civilDay"

    private val persianDate = PersianDate(CivilDate(civilYear,civilMonth,civilDay))
    private val yearSH: Int = persianDate.year
    private val monthSH: Int = persianDate.month
    private val dayOfMonthSH: Int = persianDate.dayOfMonth
    private val todaySH: String = "$yearSH/$monthSH/$dayOfMonthSH"

    private val islamicDate = IslamicDate(CivilDate(civilYear,civilMonth,civilDay))
    private val yearH: Int = islamicDate.year
    private val monthH: Int = islamicDate.month
    private val dayOfMonthH: Int = islamicDate.dayOfMonth
    private val todayH: String = "$yearH/$monthH/$dayOfMonthH"

    private var titleNames = ArrayList<String>()

    //val duration = 10
    //val pixelsToMove = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)

        showAnimation()

        showCalendar()
        showEvent()

        tRingMute()
        tMusicMute()
        tVolPercent()
    }

    private fun showAnimation() {
        Handler().postDelayed({
            val exitButton = findViewById<Button>(R.id.exit)
            exitButton.startAnimation(AnimationUtils.loadAnimation(this,R.anim.slide_in_up))
        }, 5000)
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun showCalendar() {
        //val pCalendar = SolarCalendar()

        //min api level 24 the code help you get persian calendar without solar math class.
        //configuration
        //ULocale locale = new ULocale("@calendar=persian");
        //Calendar calendar = Calendar.getInstance(locale);
        //calendar.setFirstDayOfWeek(7); //Make Saturdays first day of the week.
        //usage
        //calendar.setTime(new Date());
        //int year = calendar.get(Calendar.YEAR);
        //int weekOfYear = (calendar.get(Calendar.YEAR_WOY) == year)? calendar.get(Calendar.WEEK_OF_YEAR) : 53;
        //int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);

        //val iso: Chronology = ISOChronology.getInstanceUTC()
        //val hijri: Chronology = IslamicChronology.getInstanceUTC()
        //val date = Date() // Gregorian date
        //val cl = Calendar.getInstance()
        //cl.time = date
        //val todayIso = LocalDate(cl.get(Calendar.YEAR), cl.get(Calendar.MONTH), cl.get(Calendar.DAY_OF_MONTH), iso)
        //val todayHijri = LocalDate(
        //    todayIso.toDateTimeAtStartOfDay(),
        //    hijri
        //)
        //System.out.println(todayHijri) // 1434-05-19

        //val daysInMonth = DateUtils.getMonthLength(
        //    CalendarType.SHAMSI,
        //    persianDate.year,
        //    persianDate.month
        //)


        //
        val persianCalendar = findViewById<TextView>(R.id.persianCalendar)
        persianCalendar.text =  FaNumber.convert(todaySH) + "\n" + today + "\n" + FaNumber.convert(todayH)
        TextViewCompat.setAutoSizeTextTypeWithDefaults(persianCalendar,
            TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM)

        //
        val persianMonthName = findViewById<TextView>(R.id.persianMonthName)
        persianMonthName.text =  shCalendar.monthName + "\n" + gCalendar.monthName + "\n" + hCalendar.monthName
        TextViewCompat.setAutoSizeTextTypeWithDefaults(persianMonthName,
            TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM)

        //
        val persianWeekName = findViewById<TextView>(R.id.persianWeekName)
        persianWeekName.text =  shCalendar.weekName + "\n" + gCalendar.weekName + "\n" + hCalendar.weekName
        TextViewCompat.setAutoSizeTextTypeWithDefaults(persianWeekName,
            TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM)

        val yAstronomic = findViewById<LinearLayout>(R.id.onYAstronomic)
        yAstronomic.setOnClickListener {
            val snack = Snackbar.make(
                findViewById(R.id.mainLayout),
                getString(R.string.year) + " " + getString(YEARS_NAME.getOrNull(yearSH % 12) ?: R.string.empty),
                Snackbar.LENGTH_LONG
            )
            val view = snack.view
            val tv =
                view.findViewById<View>(snackbar_text) as TextView
            tv.setTextColor(ContextCompat.getColor(this, android.R.color.white))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) tv.textAlignment =
                TEXT_ALIGNMENT_CENTER else tv.gravity = CENTER_HORIZONTAL
            snack.show()
        }

        val mAstronomic = findViewById<LinearLayout>(R.id.onMAstronomic)
        mAstronomic.setOnClickListener {
            val snack = Snackbar.make(
                findViewById(R.id.mainLayout),
                getString(R.string.zodiac) + " " + getString(ZODIAC_MONTHS.getOrNull(monthSH) ?: R.string.empty)
                        + " " + getString(ZODIAC_MONTHS_EMOJI.getOrNull(monthSH) ?: R.string.empty),
                Snackbar.LENGTH_LONG
            )
            val view = snack.view
            val tv =
                view.findViewById<View>(snackbar_text) as TextView
            tv.setTextColor(ContextCompat.getColor(this, android.R.color.white))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) tv.textAlignment = TEXT_ALIGNMENT_CENTER else tv.gravity = CENTER_HORIZONTAL
            snack.show()
        }
    }

    private fun showEvent() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val linearLayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = linearLayoutManager

        try {
            val obj = JSONObject(loadJSONFromAsset())
            val iranArray = obj.getJSONArray("Persian Calendar")
            for (i in 0 until iranArray.length()) {
                val userDetail = iranArray.getJSONObject(i)
                if (userDetail.getString("type") == "Iran" && userDetail.getString("type") == "Ancient Iran" && userDetail.getString(
                        "type"
                    ) == "Islamic Iran" ||
                    userDetail.getString("month") == monthSH.toString() && userDetail.getString("day") == dayOfMonthSH.toString()
                ) {
                    titleNames.add(userDetail.getString("title"))
                }
            }
            val islamArray = obj.getJSONArray("Hijri Calendar")
            for (i in 0 until islamArray.length()) {
                val userDetail = islamArray.getJSONObject(i)
                if (userDetail.getString("type") == "Islamic Iran" && userDetail.getString("month") == monthH.toString() && userDetail.getString(
                        "day"
                    ) == dayOfMonthH.toString()
                ) {
                    titleNames.add(userDetail.getString("title"))
                }
            }

            // old Method
            //val mDay = SimpleDateFormat("d")
            //val mMonth = SimpleDateFormat("M")
            //mDay.format(Date())
            val gregorianArray = obj.getJSONArray("Gregorian Calendar")
            for (i in 0 until gregorianArray.length()) {
                val userDetail = gregorianArray.getJSONObject(i)
                if (userDetail.getString("month") == civilMonth.toString() && userDetail.getString("day") == civilDay.toString()) {
                    titleNames.add(userDetail.getString("title"))
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val eventsAdapter = EventsAdapter(
            titleNames
        )

        if (titleNames.isEmpty()) {
            titleNames.add(getString(R.string.no_event))
        }

        recyclerView.addItemDecoration(SimpleDividerItemDecoration(this))
        recyclerView.adapter = eventsAdapter

        //recyclerView.smoothScrollToPosition(recyclerView.getAdapter()!!.getItemCount()-1);

        // Auto scroll top first item
        //    recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        //        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        //            super.onScrolled(recyclerView, dx, dy)
        //            val lastItem: Int = linearLayoutManager.findLastCompletelyVisibleItemPosition()
        //            if (lastItem == linearLayoutManager.getItemCount() - 1) {
        //                mHandler.removeCallbacks(SCROLLING_RUNNABLE)
        //                val postHandler = Handler()
        //                postHandler.postDelayed({
        //                    recyclerView.adapter = null
        //                    recyclerView.adapter = customAdapter
        //                    mHandler.postDelayed(SCROLLING_RUNNABLE, 1000)
        //                }, 1000)
        //            }
        //        }
        //    })
        //    mHandler.postDelayed(SCROLLING_RUNNABLE, 1000)
        //}

        //private val mHandler: Handler = Handler(Looper.getMainLooper())
        //private val SCROLLING_RUNNABLE: Runnable = object : Runnable {
        //    override fun run() {
        //        recyclerView.smoothScrollBy(pixelsToMove, 0)
        //        mHandler.postDelayed(this, duration.toLong())
        //    }
        //}

    }

    fun nextDay(view: View) {
    }

    fun previewDay(view: View) {
    }

    //fun onMusicVolPanel(view: View) {
    //    val audioManager =
    //    getSystemService(Context.AUDIO_SERVICE) as AudioManager
    //    audioManager.adjustStreamVolume(3, 0, 1)
    //    finish()
    //}

    fun onRingMutePanel(view: View) {
        val audioManager =
            getSystemService(Context.AUDIO_SERVICE) as AudioManager

        when (audioManager.ringerMode) {
            AudioManager.RINGER_MODE_NORMAL -> {
                audioManager.ringerMode = AudioManager.RINGER_MODE_VIBRATE
                tRingMute()
                finish()
            }
            AudioManager.RINGER_MODE_VIBRATE -> {
                audioManager.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_UNMUTE, 0)
                audioManager.ringerMode = AudioManager.RINGER_MODE_NORMAL
                tRingMute()
                finish()
            }
            AudioManager.RINGER_MODE_SILENT -> {
                audioManager.ringerMode = AudioManager.RINGER_MODE_NORMAL
                tRingMute()
                finish()
            }
        }
    }

    fun onMusicMuteVol(view: View) {
        val audioManager =
            getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.adjustStreamVolume(3, 0, 1)

        if (VolumeSwitchWidgetPreferences.isMuted(this)) {
            audioManager.setStreamVolume(
                AudioManager.STREAM_MUSIC,
                VolumeSwitchWidgetPreferences.loadLastVolumeIndex(this),
                AudioManager.FLAG_SHOW_UI
            )
            tMusicMute()
            finish()
        } else {
            VolumeSwitchWidgetPreferences.saveLastVolumeIndex(
                this,
                audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
            )
            audioManager.setStreamVolume(
                AudioManager.STREAM_MUSIC,
                0,
                AudioManager.FLAG_SHOW_UI
            )
            tMusicMute()
            finish()
        }
    }

    fun onMusicVolInc(view: View) {
        val audioManager =
            getSystemService(Context.AUDIO_SERVICE) as AudioManager
        //audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND)
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) + 1, 0)
        tVolPercent()
    }

    fun onMusicVolDec(view: View) {
        val audioManager =
            getSystemService(Context.AUDIO_SERVICE) as AudioManager
        //audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND)
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) - 1, 0)
        tVolPercent()
    }

    fun onVpnShortcut(view: View) {
        try {
            if (Build.VERSION.SDK_INT < 24) {
                val intent = Intent("android.net.vpn.SETTINGS")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(Settings.ACTION_VPN_SETTINGS)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            }
        } catch (e: Exception) {
            Toast.makeText(this, getString(R.string.not_compatible_device), Toast.LENGTH_SHORT).show()
        }
    }

    fun onDataUsage(view: View) {
        val manufacturer = "Xiaomi"
        try {
            when {
                manufacturer == Build.MANUFACTURER -> {
                    val intent = Intent()
                    intent.component = ComponentName(
                        "com.miui.securitycenter",
                        "com.miui.networkassistant.ui.NetworkAssistantActivity"
                    )
                    startActivity(intent)
                    finish()
                }
                Build.VERSION.SDK_INT < 28 -> {
                    val intent = Intent(Intent.ACTION_MAIN)
                    intent.component = ComponentName(
                        "com.android.settings",
                        "com.android.settings.Settings\$DataUsageSummaryActivity"
                    )
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                }
                else -> {
                    val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        Intent(Settings.ACTION_DATA_USAGE_SETTINGS)
                    } else {
                        TODO(reason = "VERSION.SDK_INT < P")
                    }
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, getString(R.string.not_compatible_device), Toast.LENGTH_SHORT).show()
        }
    }

    fun onSettings(view: View) {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun onClose(view: View) {
        finish()
    }

    private fun tRingMute() {
        val iRingMute: Button = findViewById(R.id.muteRing)
        when (RingVolumeSwitchWidgetPreferences.getRingerMode(this)) {
            AudioManager.RINGER_MODE_NORMAL -> {
                iRingMute.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_notifications_active,0)
            }
            AudioManager.RINGER_MODE_VIBRATE -> {
                iRingMute.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_vibration,0)
            }
            AudioManager.RINGER_MODE_SILENT -> {
                iRingMute.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_notifications_off,0)
            }
        }
    }

    private fun tMusicMute() {
        val iMuteVol: Button = findViewById(R.id.muteVol)
        if(VolumeSwitchWidgetPreferences.isMuted(this)){
            iMuteVol.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_volume_off,0)
        } else {
            iMuteVol.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_volume_up,0)
        }
    }

    private fun tVolPercent() {
        Handler().postDelayed({
            val tVolPercent: TextView = findViewById(R.id.volPercent)
            tVolPercent.startAnimation(AnimationUtils.loadAnimation(this,R.anim.fade))
            val audioManager =
                getSystemService(Context.AUDIO_SERVICE) as AudioManager

            val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
            val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
            val currentVolumePercentage: Int = 100 * currentVolume / maxVolume

            tVolPercent.text = FaNumber.convert("%$currentVolumePercentage")
            TextViewCompat.setAutoSizeTextTypeWithDefaults(tVolPercent,
                TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM)
        }, 100)
    }

    private fun loadJSONFromAsset(): String {
        val json: String
        json = try {
            val `is` = assets.open("events.json")
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, StandardCharsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null.toString()
        }
        return json
    }

    object FaNumber {
        fun convert(faNumbers: String): String {
            var numbers = faNumbers
            val mChars =
                arrayOf(
                    arrayOf("0", "۰"),
                    arrayOf("1", "۱"),
                    arrayOf("2", "۲"),
                    arrayOf("3", "۳"),
                    arrayOf("4", "۴"),
                    arrayOf("5", "۵"),
                    arrayOf("6", "۶"),
                    arrayOf("7", "۷"),
                    arrayOf("8", "۸"),
                    arrayOf("9", "۹")
                )
            for (num in mChars) {
                numbers = numbers.replace(num[0], num[1])
            }
            return numbers
        }
    }
}
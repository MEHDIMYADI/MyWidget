package ir.dimyadi.mywidget.util

import android.content.Context
import android.media.AudioManager

/**
 * @author MEHDIMYADI
 **/

object RingVolumeSwitchWidgetPreferences {

    private const val SHARED_PREF_NAME = "RingSwitchWidgetPreferences"
    private const val SHARED_PREF_LAST_VOLUME = "LastVolume"
    private const val SHARED_PREF_CONFIGURED = "Configured"
    fun isConfigured(context: Context): Boolean {
        val prefs = context.getSharedPreferences(
            SHARED_PREF_NAME,
            Context.MODE_PRIVATE
        )
        return prefs.getBoolean(this.SHARED_PREF_CONFIGURED, false)
    }

    fun setConfigured(context: Context, configured: Boolean) {
        val prefs = context.getSharedPreferences(
            SHARED_PREF_NAME,
            Context.MODE_PRIVATE
        )
        prefs.edit()
            .putBoolean(SHARED_PREF_CONFIGURED, configured)
            .apply()
    }

    fun getRingerMode(context: Context): Int {
        val audioManager =
            context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        return audioManager.ringerMode
    }

    fun loadLastState(context: Context): Int {
        val prefs = context.getSharedPreferences(
            SHARED_PREF_NAME,
            Context.MODE_PRIVATE
        )
        if (!prefs.contains(SHARED_PREF_LAST_VOLUME)) {
            val audioManager =
                context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            val volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
            return if (volume > 0) volume else 5
        }
        return prefs.getInt(SHARED_PREF_LAST_VOLUME, 5)
    }

    fun saveLastVolumeIndex(context: Context, volume: Int) {
        val prefs = context.getSharedPreferences(
            SHARED_PREF_NAME,
            Context.MODE_PRIVATE
        )
        prefs.edit().putInt(SHARED_PREF_LAST_VOLUME, volume)
            .apply()
    }

    enum class RingState {
        Mute, Vibrate, Normal
    }
}
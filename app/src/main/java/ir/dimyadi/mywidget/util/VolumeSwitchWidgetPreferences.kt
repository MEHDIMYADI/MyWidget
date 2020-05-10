package ir.dimyadi.mywidget.util

import android.content.Context
import android.media.AudioManager

/**
 * @author MEHDIMYADI
 **/

object VolumeSwitchWidgetPreferences {

    private const val SHARED_PREF_NAME = "VolumeSwitchWidgetPreferences"
    private const val SHARED_PREF_LAST_VOLUME = "LastVolume"

    fun isMuted(context: Context): Boolean {
        val audioManager =
            context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        return audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) == 0
    }

    fun loadLastVolumeIndex(context: Context): Int {
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
        prefs.edit().putInt(SHARED_PREF_LAST_VOLUME, volume).apply()
    }
}
package com.example.hahalolofake.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.hahalolofake.utils.DeviceUtil.KEY_INSERT_EFFECT
import com.example.hahalolofake.utils.DeviceUtil.KEY_SHOW_RATING
import com.example.hahalolofake.utils.DeviceUtil.NAME_EFFECT
import com.example.hahalolofake.utils.DeviceUtil.SOUND_POSITION
import javax.inject.Inject

class SharePrefs @Inject constructor(private val context: Context) {

    private fun getPref(context : Context): SharedPreferences {
        return context.getSharedPreferences(
            context.packageName,
            Context.MODE_PRIVATE
        )
    }

    var numRate: Int
        get() = getPref(context).getInt(KEY_SHOW_RATING, 0)
        set(numRate) = getPref(context).edit().putInt(KEY_SHOW_RATING, numRate).apply()

    var isInsertEffectSound: Int
        get() = getPref(context).getInt(KEY_INSERT_EFFECT, 0)
        set(isInsertEffectSound) = getPref(context).edit().putInt(KEY_INSERT_EFFECT, isInsertEffectSound).apply()

    var addSoundPosition: Int
        get() = getPref(context).getInt(SOUND_POSITION, 0)
        set(addSoundPosition) = getPref(context).edit().putInt(SOUND_POSITION, addSoundPosition).apply()

    var addSoundName: String
        get() = getPref(context).getString(NAME_EFFECT, "")?: ""
        set(value) = getPref(context).edit().putString(NAME_EFFECT, value).apply()

}
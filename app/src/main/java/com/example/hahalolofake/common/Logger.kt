package com.example.hahalolofake.common

import android.util.Log
import androidx.databinding.ktx.BuildConfig

object Logger {

    fun log(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message)
        }
    }

}
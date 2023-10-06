package com.example.hahalolofake.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.media.MediaMetadataRetriever
import android.net.ConnectivityManager
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.Locale

object SystemUtil {
    private var myLocale: Locale? = null
    fun haveNetworkConnection(context: Context): Boolean {
        var haveConnectedWifi = false
        var haveConnectedMobile = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.allNetworkInfo
        for (ni in netInfo) {
            if (ni.typeName.equals(
                    "WIFI",
                    ignoreCase = true
                )
            ) if (ni.isConnected) haveConnectedWifi = true
            if (ni.typeName.equals(
                    "MOBILE",
                    ignoreCase = true
                )
            ) if (ni.isConnected) haveConnectedMobile = true
        }
        return haveConnectedWifi || haveConnectedMobile
    }

    // Load lại ngôn ngữ đã lưu và thay đổi chúng

    fun setLocale(context: Context) {
        val language = getPreLanguage(context)
        if (language == "") {
            val config = Configuration()
            val locale = Locale.getDefault()
            Locale.setDefault(locale)
            config.locale = locale
            context.resources
                .updateConfiguration(config, context.resources.displayMetrics)
        } else {
            changeLang(language, context)
        }
    }

    // method phục vụ cho việc thay đổi ngôn ngữ.
    fun changeLang(lang: String?, context: Context) {
        if (lang.equals("", ignoreCase = true)) return
        myLocale = lang?.let { Locale(it) }
        saveLocale(context, lang)
        myLocale?.let { Locale.setDefault(it) }
        val config = Configuration()
        config.locale = myLocale
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    fun saveLocale(context: Context, lang: String?) {
        setPreLanguage(context, lang)
    }

    @SuppressLint("NewApi")
    fun getPreLanguage(mContext: Context): String? {
        val preferences = mContext.getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
        Locale.getDefault().displayLanguage
        val lang: String = Resources.getSystem().configuration.locales[0].language
        return if (!getLanguageApp(mContext).contains(lang)) {
            preferences.getString("KEY_LANGUAGE", "en")
        } else {
            preferences.getString("KEY_LANGUAGE", lang)
        }
    }

    fun setPreLanguage(context: Context, language: String?) {
        if (language == null || language == "") {
            return
        } else {
            val preferences = context.getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
            preferences.edit().putString("KEY_LANGUAGE", language).apply()
        }
    }

    fun getLanguageApp(context: Context): List<String> {
        val sharedPreferences = context.getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
        // boolean openLanguage = sharedPreferences.getBoolean("openLanguage", false);
        val languages: MutableList<String> = ArrayList()
        languages.add("en")
        languages.add("fr")
        languages.add("pt")
        languages.add("es")
        languages.add("hi")
        return languages
    }
    fun getWavFileDuration(filePath: String,context: Context): Long {
        val retriever = MediaMetadataRetriever()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // Request the permission here
            }else{
                try {
                    retriever.setDataSource(filePath)
                } catch (e: Exception) {
                    // Handle the exception here, and log or display any error messages.
                }
            }
        }
        // Get the duration in milliseconds
        val durationStr = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)

        // Convert the duration from string to long
        return durationStr?.toLongOrNull() ?: 0
    }
    fun formatDurationInSeconds(durationInSeconds: Long): String {
        val minutes = durationInSeconds / 60
        val seconds = durationInSeconds % 60
        return String.format("%02d:%02d", minutes, seconds)
    }


}
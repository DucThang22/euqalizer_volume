package com.example.hahalolofake.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.text.format.DateFormat
import androidx.core.content.ContextCompat
import java.io.File
import kotlin.math.ln
import kotlin.math.pow

object DeviceUtil {
    fun hasStoragePermission(context: Context) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED
        else
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED

    fun hasCameraPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun checkTime(inputPath: String): String {
        val lastModifiedTime = getFileLastModifiedTime(inputPath)
        val formattedTime = DateFormat.format("yyyy/MM/dd hh:mm a", lastModifiedTime)
        return formattedTime.toString()
    }

    private fun getFileLastModifiedTime(filePath: String): Long {
        val file = File(filePath)
        return file.lastModified()
    }

    fun formatFileSize(fileSize: Long): String {
        val unit = 1024
        if (fileSize < unit) return "$fileSize B"
        val exp = (ln(fileSize.toDouble()) / ln(unit.toDouble())).toInt()
        val pre = "KMGTPE"[exp - 1]
        return String.format("%.1f %sB", fileSize / unit.toDouble().pow(exp.toDouble()), pre)
    }

    const val KEY_SHOW_RATING = "key_show_rating"
    const val KEY_INSERT_EFFECT = "key_insert_effect"
    const val SOUND_POSITION = "sound_position"
    const val NAME_EFFECT = "name_effect"
}
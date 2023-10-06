package com.example.hahalolofake.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.hahalolofake.data.dao.EffectSoundDao
import com.example.hahalolofake.data.models.EffectSound

class EffectRepository(private val effectSoundDao: EffectSoundDao) {
    val allItems: LiveData<MutableList<EffectSound>> = effectSoundDao.getAllCourses()

    suspend fun insert(item: EffectSound) {
        Log.d("Thang97 9999", "EffectRepository insert" + item.name)
        effectSoundDao.insert(item)
    }

    suspend fun delete(item: EffectSound) {
        effectSoundDao.delete(item)
    }

    suspend fun update(item: EffectSound) {
        effectSoundDao.update(item)
    }

}
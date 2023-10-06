//package com.example.hahalolofake.data.repository
//
//import android.util.Log
//import androidx.lifecycle.LiveData
//import com.example.hahalolofake.data.dao.EffectSoundDao
//import com.example.hahalolofake.data.database.EffectSoundDatabase
//import com.example.hahalolofake.data.models.EffectSound
//import javax.inject.Inject
//
//class EffectRepositoryImpl @Inject constructor(
//    private val effectSoundDao: EffectSoundDao,
//    private val database: EffectSoundDatabase): EffectRepository {
//    override suspend fun onInsert(effectSound: EffectSound) {
//        database.effectDao().insert(effectSound)
//    }
//
//    override fun getAll(): LiveData<MutableList<EffectSound>> {
//        return database.effectDao().getAllCourses()
//    }
//
//    override suspend fun onDelete(effectSound: EffectSound) {
//        database.effectDao().delete(effectSound)
//    }
//
//    override suspend fun onUpdate(effectSound: EffectSound) {
//        database.effectDao().update(effectSound)
//    }
//
//}
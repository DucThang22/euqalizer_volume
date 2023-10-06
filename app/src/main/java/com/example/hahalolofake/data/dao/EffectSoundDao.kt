package com.example.hahalolofake.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.hahalolofake.data.models.EffectSound

@Dao
interface EffectSoundDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(model: EffectSound?)

    @Query("SELECT * FROM effect_table ORDER BY id DESC")
    fun getAllCourses(): LiveData<MutableList<EffectSound>>

    @Delete
    suspend fun delete(model: EffectSound?)

    @Update
    suspend fun update(model: EffectSound?)

}
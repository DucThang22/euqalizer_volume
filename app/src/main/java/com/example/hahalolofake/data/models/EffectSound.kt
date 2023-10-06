package com.example.hahalolofake.data.models

import android.widget.SeekBar
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "effect_table")
data class EffectSound(
    @PrimaryKey(autoGenerate = true)
    val id: Long?= null,
    val name: String?= null,
    val image: Int?= null,
    var isSelected: Boolean = false
//    val seekbar: List<SeekBar> ?= null
)
package com.example.hahalolofake.data.models

import androidx.appcompat.widget.AppCompatSeekBar

data class BandLevel (
    val seekBar: AppCompatSeekBar,
    val index: Short,
    val lowestBandLevel: Short,
    val maxBandLevel: Short,
)
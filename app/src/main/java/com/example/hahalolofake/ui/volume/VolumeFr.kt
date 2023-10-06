package com.example.hahalolofake.ui.volume

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioManager
import android.os.Bundle
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.example.hahalolofake.R
import com.example.hahalolofake.base.fragment.BaseFragment
import com.example.hahalolofake.databinding.VolumeFrBinding
import kotlin.math.roundToInt

class VolumeFr : BaseFragment() {
    private lateinit var binding: VolumeFrBinding
    private lateinit var audioManager: AudioManager
    private lateinit var volumeChangeReceiver: VolumeChangeReceiver

    private var selectedSoundButton: View? = null
    private var currentSeekBarProgress = 0
    private var vibrator: Vibrator? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = VolumeFrBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initView()
        initAction()
    }

    private fun initData() {
        audioManager = activity?.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        binding.seekBar.max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        binding.seekBar.progress = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    currentSeekBarProgress = progress
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0)
                    updateButtonState(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit

            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
        })

        volumeChangeReceiver = VolumeChangeReceiver()
        val intentFilter = IntentFilter()
        intentFilter.addAction("android.media.VOLUME_CHANGED_ACTION")
        requireContext().registerReceiver(volumeChangeReceiver, intentFilter)
    }

    private fun unMuteBackground() {
        binding.apply {
            mute.setBackgroundResource(R.drawable.bg_sound_select)
            sound30.setBackgroundResource(R.drawable.bg_sound_select)
            sound60.setBackgroundResource(R.drawable.bg_sound_select)
            sound100.setBackgroundResource(R.drawable.bg_sound_select)
            sound125.setBackgroundResource(R.drawable.bg_sound_select)
            sound150.setBackgroundResource(R.drawable.bg_sound_select)
            sound175.setBackgroundResource(R.drawable.bg_sound_select)
            max.setBackgroundResource(R.drawable.bg_sound_select)
        }
    }

    private fun updateButtonState(progress: Int) {
        unMuteBackground()
        binding.apply {
            mute.setBackgroundResource(
                if (progress == 0) {
                    R.drawable.bg_item_list_sound_select_v2
                } else {
                    R.drawable.bg_sound_select
                }
            )

            sound30.setBackgroundResource(
                if (progress in 5..7) {
                    R.drawable.bg_item_list_sound_select_v2
                } else {
                    R.drawable.bg_sound_select
                }
            )

            sound60.setBackgroundResource(
                if (progress in 9..11) {
                    R.drawable.bg_item_list_sound_select_v2
                } else {
                    R.drawable.bg_sound_select
                }
            )

            sound100.setBackgroundResource(
                if (progress in 13..15) {
                    R.drawable.bg_item_list_sound_select_v2
                } else {
                    R.drawable.bg_sound_select
                }
            )
        }

//        binding.apply {
//            unMuteBackground()
//            val soundButtons = listOf(mute, sound30, sound60, sound100)
//
//            for ((index, button) in soundButtons.withIndex()) {
//                val isSelected = when (index) {
//                    0 -> progress == 0
//                    1 -> progress in 5..7
//                    2 -> progress in 9..11
//                    3 -> progress in 13..15
//                    else -> false
//                }
//                setButtonBackground(button, isSelected)
//            }
//        }
    }

    private fun setButtonBackground(button: View, isSelected: Boolean) {
        button.setBackgroundResource(
            if (isSelected) {
                R.drawable.bg_item_list_sound_select_v2
            } else {
                R.drawable.bg_sound_select
            }
        )
    }

    inner class VolumeChangeReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == "android.media.VOLUME_CHANGED_ACTION") {
                binding.seekBar.progress = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        requireContext().unregisterReceiver(volumeChangeReceiver)
    }

    private fun initView() {
//        binding.crollerBtn.indicatorWidth = 10f
//        binding.crollerBtn.backCircleColor = Color.parseColor("#EDEDED")
//        binding.crollerBtn.mainCircleColor = Color.parseColor("#606D9F")
//        binding.crollerBtn.max = 50
//        binding.crollerBtn.startOffset = 45
//        binding.crollerBtn.setIsContinuous(false)
//        binding.crollerBtn.labelColor = Color.BLACK
//        binding.crollerBtn.progressPrimaryColor = Color.parseColor("#FF03DAC5")
//        binding.crollerBtn.setOnCrollerChangeListener(object : OnCrollerChangeListener {
//            override fun onProgressChanged(croller: Croller, progress: Int) {
//                if (vibrator?.hasVibrator() == true) {
//                    vibrator?.vibrate(100)
//                }
//            }
//
//            override fun onStartTrackingTouch(croller: Croller) {
//
//            }
//
//            override fun onStopTrackingTouch(croller: Croller) {
//
//            }
//        })

        vibrator = activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        binding.controllerBass.setOnProgressChangedListener {
            if (vibrator?.hasVibrator() == true) {
                vibrator?.vibrate(100)
            }
        }
    }

    private fun initAction() {
        binding.apply {
            val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)

            val soundButtons = listOf(
                mute, sound30, sound60, sound100, sound125, sound150, sound175, max
            )

            for (button in soundButtons) {
                button.setOnClickListener {
                    unMuteBackground()
                    // Checks if that item is currently selected
                    if (button == selectedSoundButton) {
                        // Deselect that item
                        selectedSoundButton?.setBackgroundResource(R.drawable.bg_sound_select)
                        selectedSoundButton = null
                    } else {
                        selectedSoundButton?.setBackgroundResource(R.drawable.bg_sound_select)
                        selectedSoundButton = button

                        button.setBackgroundResource(R.drawable.bg_item_list_sound_select_v2)

                        when (button) {
                            mute -> {
                                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0)
                                binding.seekBar.progress = 0
                            }

                            sound30 -> setAdjustVolume(maxVolume, 0.3)
                            sound60 -> setAdjustVolume(maxVolume, 0.6)
                            sound100 -> {
                                audioManager.setStreamVolume(
                                    AudioManager.STREAM_MUSIC,
                                    maxVolume,
                                    0
                                )
                                binding.seekBar.progress = maxVolume
                            }

                            sound125 -> {
                                audioManager.setStreamVolume(
                                    AudioManager.STREAM_MUSIC,
                                    maxVolume,
                                    0
                                )
                                binding.seekBar.progress = maxVolume
                                setAdjustAnalogController(0.25)
                                binding.controllerBass.progress = 0.25.toInt()
                            }
                            sound150 -> {
                                audioManager.setStreamVolume(
                                    AudioManager.STREAM_MUSIC,
                                    maxVolume,
                                    0
                                )
                                binding.seekBar.progress = maxVolume
                                setAdjustAnalogController(0.5)
                            }
                            sound175 -> {
                                audioManager.setStreamVolume(
                                    AudioManager.STREAM_MUSIC,
                                    maxVolume,
                                    0
                                )
                                binding.seekBar.progress = maxVolume
                                setAdjustAnalogController(0.75)
                            }
                            max -> {
                                audioManager.setStreamVolume(
                                    AudioManager.STREAM_MUSIC,
                                    maxVolume,
                                    0
                                )
                                binding.seekBar.progress = maxVolume
                                setAdjustAnalogController(1.0)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setAdjustVolume(maxVolume: Int, percent: Double) {
        val volumePercent = (percent * maxVolume).toInt()
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volumePercent, 0)
        binding.seekBar.progress = volumePercent
    }

    private fun setAdjustAnalogController(percent: Double) {
        binding.controllerBass.progress = 0.25.roundToInt()
    }
}
package com.example.hahalolofake.ui.equalizer

import com.example.hahalolofake.base.fragment.BaseFragment

class EqualizerFr : BaseFragment() {
//    private lateinit var binding: EqulizerFrBinding
//
//    private lateinit var mediaPlayer: MediaPlayer
//    private var vibrator: Vibrator? = null
//    private var audioManager: AudioManager?= null
//    private lateinit var equalizer: Equalizer
//
//    private val seekBars: MutableMap<Short, BandLevel> = HashMap()
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
//    ): View {
//        super.onCreateView(inflater, container, savedInstanceState)
//        binding = EqulizerFrBinding.inflate(layoutInflater)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        initView()
//        initAction()
//    }
//
//    private fun initView() {
////        audioSessionId = setupMediaPlayer()
////        audioEffectManager = AudioEffectManager(audioSessionId)
////
////        audioEffectManager?.equalizer?.addPropertiesChangeListener {
////            println()
////        }
////        audioEffectManager?.equalizer?.addEnableStatusChangeListener {
////            println()
////        }
////        equalizer = audioEffectManager?.equalizer
//
//        vibrator = activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
//
//        // crollerBass
//        binding.crollerBass.indicatorWidth = 10f
//        binding.crollerBass.backCircleColor = Color.parseColor("#EDEDED")
//        binding.crollerBass.mainCircleColor = Color.parseColor("#606D9F")
//        binding.crollerBass.max = 50
//        binding.crollerBass.startOffset = 45
//        binding.crollerBass.setIsContinuous(false)
//        binding.crollerBass.labelColor = Color.BLACK
//        binding.crollerBass.progressPrimaryColor = Color.parseColor("#FF03DAC5")
//
//        binding.crollerBass.setOnCrollerChangeListener(object : OnCrollerChangeListener {
//            override fun onProgressChanged(croller: Croller, progress: Int) {
//                if (vibrator?.hasVibrator() == true) {
//                    vibrator?.vibrate(100)
//                }
//            }
//
//            override fun onStartTrackingTouch(croller: Croller) {}
//
//            override fun onStopTrackingTouch(croller: Croller) {}
//        })
//
//        //crollerVisualizer
//        binding.crollerVisualizer.indicatorWidth = 10f
//        binding.crollerVisualizer.backCircleColor = Color.parseColor("#EDEDED")
//        binding.crollerVisualizer.mainCircleColor = Color.parseColor("#606D9F")
//        binding.crollerVisualizer.max = 50
//        binding.crollerVisualizer.startOffset = 45
//        binding.crollerVisualizer.setIsContinuous(false)
//        binding.crollerVisualizer.labelColor = Color.BLACK
//        binding.crollerVisualizer.progressPrimaryColor = Color.parseColor("#FF03DAC5")
//
//        binding.crollerVisualizer.setOnCrollerChangeListener(object : OnCrollerChangeListener {
//            override fun onProgressChanged(croller: Croller, progress: Int) {
//                if (vibrator?.hasVibrator() == true) {
//                    vibrator?.vibrate(100)
//                }
//            }
//
//            override fun onStartTrackingTouch(croller: Croller) {}
//
//            override fun onStopTrackingTouch(croller: Croller) {}
//        })
//
//        initAdapter()
//        initEffectSound()
//    }
//
//    @SuppressLint("NewApi")
//    private fun initEffectSound() {
//
//    }
//
//    private fun setupBandLevelSeekBar(
//        seekBar: AppCompatSeekBar,
//        bandNumber: Short,
//        lowerBandLevelMilliBel: Short,
//        upperBandLevelMilliBel: Short
//    ) {
//        val maxValue = upperBandLevelMilliBel - lowerBandLevelMilliBel
//        seekBar.max = maxValue
//
//        val bandLevel = BandLevel(
//            seekBar,
//            bandNumber,
//            lowerBandLevelMilliBel,
//            upperBandLevelMilliBel
//        )
//
//        seekBars[bandNumber] = bandLevel
////        setProgress(bandLevel)
//
//        seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
//            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
//                equalizer?.setBandLevel(
//                    bandNumber, (progress + lowerBandLevelMilliBel).toShort()
//                )
//            }
//
//            override fun onStartTrackingTouch(p0: SeekBar?) {}
//
//            override fun onStopTrackingTouch(p0: SeekBar?) {}
//        })
//    }
//
//    private fun setProgress(bandLevel: BandLevel) {
//        val level = equalizer?.getBandLevel(bandLevel.index)?.toInt()
//        if (level != null) {
//            bandLevel.seekBar.progress = level + bandLevel.maxBandLevel
//        }
//    }
//
//    private val listener = object : EqualizerListener {
//        override fun onClickEffect(position: Int, item: Effect) {
//
//        }
//
//    }
//
//    private fun initAdapter() {
//        binding.effectRecycler.layoutManager = LinearLayoutManager(
//            requireContext(),
//            LinearLayoutManager.HORIZONTAL,
//            false
//        )
//        binding.effectRecycler.adapter = getPosition()?.let {
//            EqualizerAdapter(
//                EqualizerAdapter.dummyData,
//                requireContext(),
//                it,
//                listener
//            )
//        }
//    }
//
//    @SuppressLint("MissingPermission")
//    private fun effectSoundPop() {
//        audioManager =  activity?.getSystemService(Context.AUDIO_SERVICE) as AudioManager
//    }
//
//    private fun setupMediaPlayer(): Int {
//        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.tu_su)
//        mediaPlayer.start()
//        mediaPlayer.setVolume(.1F, .1F)
//        return mediaPlayer.audioSessionId
//    }
//
//    private fun getPosition(): Int? {
//        val pref = activity?.applicationContext?.getSharedPreferences("myPref", MODE_PRIVATE)
//        return pref?.getInt("positionEffect", 0)
//    }
//
//    private fun initAction() {
//
//    }
}
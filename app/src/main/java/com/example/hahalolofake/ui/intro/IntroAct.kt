package com.example.hahalolofake.ui.intro

import android.content.Intent
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.hahalolofake.R
import com.example.hahalolofake.base.activities.BaseActivity
import com.example.hahalolofake.data.models.Intro
import com.example.hahalolofake.databinding.ActivityIntroBinding
import com.example.hahalolofake.ui.intro.adapter.IntroAdapter
import com.example.hahalolofake.ui.permission.PermissionAct

@Suppress("DEPRECATION")
class IntroAct : BaseActivity() {
    private lateinit var introAdapter: IntroAdapter
    private lateinit var binding: ActivityIntroBinding
    var position = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intro = listOf(
            Intro(
                getString(R.string.sculpt_your_sound),
                getString(R.string.fine_tune_your_audio_experience_with_precision),
                R.drawable.intro_img_1
            ),
            Intro(
                getString(R.string.elevate_your_audio),
                getString(R.string.power_up_the_volume_when_you_need_it_most),
                R.drawable.intro_img_2
            ),
            Intro(
                getString(R.string.adaptive_sound),
                getString(R.string.let_your_environment_set_the_perfect_volume),
                R.drawable.intro_img_3
            )
        )

        introAdapter = IntroAdapter(intro)
        binding.viewPager.adapter = introAdapter
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
        binding.dotsIndicator.setViewPager2(binding.viewPager)
        initAction(intro)


    }
    private fun initAction(intro:List<Intro>){
        binding.buttonStartIntro.setOnClickListener {
            position = binding.viewPager.currentItem
            if (position < intro.size) {
                position++
                binding.viewPager.currentItem = position
            }

            if (position == intro.size) {
                startActivity(Intent(this, PermissionAct::class.java))
                finish()
            }
        }
    }
}
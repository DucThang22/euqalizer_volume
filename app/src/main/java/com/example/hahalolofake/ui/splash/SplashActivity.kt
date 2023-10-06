package com.example.hahalolofake.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import com.example.hahalolofake.base.activities.BaseActivity
import com.example.hahalolofake.databinding.ActivitySplashBinding
import com.example.hahalolofake.ui.MainActivity
import com.example.hahalolofake.ui.main_v2.MainV2Act
import com.example.hahalolofake.ui.multi_lang.MultiLangAct
import com.example.hahalolofake.utils.SystemUtil
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashBinding

    @Inject
    lateinit var preferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SystemUtil.setPreLanguage(this, SystemUtil.getPreLanguage(this))
        SystemUtil.setLocale(this)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.textView2.postDelayed({
            SystemUtil.setPreLanguage(this, SystemUtil.getPreLanguage(this))
            SystemUtil.setLocale(this)
            openMainActivity()
        }, 3000)
    }

    private fun restorePrefData(): Boolean {
        val pref = applicationContext.getSharedPreferences("myPref", MODE_PRIVATE)
        return pref.getBoolean("isIntroOpened", false)
    }

    private fun openMainActivity() {
        if (restorePrefData()) {
            startActivity(Intent(this, MainV2Act::class.java))
            finish()
        } else {
            startActivity(MultiLangAct.getIntent(this, 1))
            finish()
        }
    }
}
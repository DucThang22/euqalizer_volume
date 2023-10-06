package com.example.hahalolofake.ui.multi_lang

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.hahalolofake.ui.intro.IntroAct
import com.example.hahalolofake.base.activities.BaseActivity
import com.example.hahalolofake.databinding.ActivityMultiLangBinding
import com.example.hahalolofake.ui.main_v2.MainV2Act
import com.example.hahalolofake.ui.multi_lang.adapter.ItemMultiLangAdapter
import com.example.hahalolofake.utils.SystemUtil

@Suppress("DEPRECATION")
class MultiLangAct : BaseActivity() {
    private lateinit var binding: ActivityMultiLangBinding
    var type: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMultiLangBinding.inflate(layoutInflater)
        setContentView(binding.root)
        type = intent.getIntExtra(TYPE_LANG, 0)
        bindRv()
        checkType()
        binding.imgBack.setOnClickListener {
            val intent = Intent(this, MainV2Act::class.java)
            intent.putExtra("back_setting", true)
            startActivity(intent)
            finish()
        }
    }

    private fun checkType() {
        when (type) {
            1 -> {
                binding.typeLang1.visibility = View.VISIBLE
                binding.typeLang2.visibility = View.GONE
                binding.btnChooseLang.setOnClickListener {
                    startActivity(Intent(this, IntroAct::class.java))
                    finish()
                }
            }

            2 -> {
                binding.typeLang1.visibility = View.GONE
                binding.typeLang2.visibility = View.VISIBLE
                binding.imgBack.setOnClickListener {
                    val intent = Intent(this, MainV2Act::class.java)
                    intent.putExtra("back_setting", true)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun bindRv() {
        binding.rcvLangs.adapter = ItemMultiLangAdapter(
            ItemMultiLangAdapter.dummyData,
            this,
            getPosition(),
        ) { position, item ->
            SystemUtil.setPreLanguage(this, item.code)
            SystemUtil.setLocale(this)
        }
    }

    companion object {
        private const val TYPE_LANG = "MultiLangAct_Lang"
        fun getIntent(context: Context, type: Int): Intent {
            val intent = Intent(context, MultiLangAct::class.java)
            intent.putExtra(TYPE_LANG, type)
            return intent
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (type == 2) {
            startActivity(Intent(this, MainV2Act::class.java))
        } else {
            finish()
        }
    }

    private fun getPosition(): Int {
        val pref = applicationContext.getSharedPreferences("myPref", MODE_PRIVATE)
        return pref.getInt("positionLang", 0)
    }
}
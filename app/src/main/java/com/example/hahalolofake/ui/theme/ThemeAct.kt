package com.example.hahalolofake.ui.theme

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.hahalolofake.R
import com.example.hahalolofake.base.activities.BaseActivity
import com.example.hahalolofake.databinding.ThemeActBinding
import com.example.hahalolofake.ui.main_v2.MainV2Act


class ThemeAct : BaseActivity() {

    private lateinit var binding: ThemeActBinding
    private val isSelectThemeOne: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ThemeActBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initAction()
    }

    private fun initAction() {
        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.saveBtn.setOnClickListener {
           if(isSelectThemeOne) {
               savePositionTheme(0)
               finish()
               startActivity(MainV2Act.getIntent(this))
           } else {
               savePositionTheme(1)
               finish()
               startActivity(MainV2Act.getIntent(this))
           }
        }
        binding.themeOne.setOnClickListener {
            isSelectThemeOne
            setBackgroundThemeOne()
        }
        binding.themeTwo.setOnClickListener {
            !isSelectThemeOne
            setBackgroundThemeTwo()
        }
    }

    private fun setBackgroundThemeOne() {
        binding.themeOne.setBackgroundResource(R.drawable.theme_one_selected)
        binding.themeTwo.setBackgroundResource(R.drawable.theme_two)
    }

    private fun setBackgroundThemeTwo() {
        binding.themeTwo.setBackgroundResource(R.drawable.theme_two_selected)
        binding.themeOne.setBackgroundResource(R.drawable.themev2)
    }

    private fun savePositionTheme(position: Int) {
        val pref = this.getSharedPreferences(
            "myPref", AppCompatActivity.MODE_PRIVATE
        )
        val editor = pref.edit()
        editor.putInt("positionTheme", position)
        editor.commit()
    }

    companion object {
        private const val TYPE_THEME = "MultiLangAct_theme"
        fun getIntent(context: Context, type: Int): Intent {
            val intent = Intent(context, ThemeAct::class.java)
            intent.putExtra(TYPE_THEME, type)
            return intent
        }
    }

}
package com.example.hahalolofake.ui.setting

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.all.language.translate.voice.translator.ui.dialog_rating.SharePrefUtils
import com.example.hahalolofake.R
import com.example.hahalolofake.base.activities.BaseActivity
import com.example.hahalolofake.databinding.FragmentSettingBinding
import com.example.hahalolofake.ui.dialog_rating.DialogRating
import com.example.hahalolofake.ui.multi_lang.MultiLangAct
import com.example.hahalolofake.ui.theme.ThemeAct
import com.example.hahalolofake.utils.SharePrefs
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.tasks.Task

class SettingsAct : BaseActivity() {
    private lateinit var binding: FragmentSettingBinding
    //Rate
    private var manager: ReviewManager? = null
    private var reviewInfo: ReviewInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharePrefs = SharePrefs(this)
        initAction()
    }


    private fun initAction() {
        binding.backBtn.setOnClickListener {
           finish()
        }

        binding.llPrivacyPolicy.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://biuustudio.netlify.app/policy"))
            startActivity(intent)
        }

        binding.llRateUs.setOnClickListener {
            showDialogRate()
        }
        binding.llLanguage.setOnClickListener {
            startActivity(MultiLangAct.getIntent(this, 2))
            finish()
        }

        binding.llTheme.setOnClickListener {
            startActivity(ThemeAct.getIntent(this, 1))
        }
    }

    private fun showDialogRate() {
        val ratingDialog = DialogRating(this)

        ratingDialog.init(
           this,
            object : DialogRating.OnPress {
                override fun sendThank() {
                    SharePrefUtils.forceRated(
                        this@SettingsAct
                    )
                    Toast.makeText(
                        this@SettingsAct,
                        getString(R.string.string_thank_you_for_rating_the_app),
                        Toast.LENGTH_SHORT
                    ).show()
                    ratingDialog.dismiss()
                }

                override fun rating() {
                    manager = ReviewManagerFactory.create(this@SettingsAct)
                    val request: Task<ReviewInfo> = manager!!.requestReviewFlow()
                    request.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            reviewInfo = task.result
                            val flow: Task<Void> =
                                manager!!.launchReviewFlow(this@SettingsAct, reviewInfo!!)
                            flow.addOnSuccessListener {
                                SharePrefUtils.forceRated(
                                    this@SettingsAct
                                )
                                ratingDialog.dismiss()
                                Toast.makeText(
                                    this@SettingsAct,
                                    getString(R.string.string_thank_you_for_rating_the_app),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            ratingDialog.dismiss()
                        }
                    }
                }

                override fun cancel() {}
                override fun later() {
                    ratingDialog.dismiss()
                }
            }
        )
        try {
            ratingDialog.show()
        } catch (e: WindowManager.BadTokenException) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val CURRENT_ITEM = "SettingsAct-Current_Item"
        fun getIntent(context: Context): Intent {
            return Intent(context, SettingsAct::class.java)
        }
    }
}
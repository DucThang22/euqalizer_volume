package com.example.hahalolofake.ui.main_v2

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.hahalolofake.R
import com.example.hahalolofake.base.activities.AbsActivity
import com.example.hahalolofake.common.bindIsVisible
import com.example.hahalolofake.common.executeAfter
import com.example.hahalolofake.data.models.EffectSound
import com.example.hahalolofake.databinding.ActivityMainV2Binding
import com.example.hahalolofake.databinding.DialogSaveBinding
import com.example.hahalolofake.ui.equalizer.EqualizerFrV2
import com.example.hahalolofake.ui.service.MediaBrowserService
import com.example.hahalolofake.ui.setting.SettingsAct
import com.example.hahalolofake.ui.volume.VolumeFr
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import javax.inject.Inject


class MainV2Act @Inject constructor() : AbsActivity<ActivityMainV2Binding>() {

    private val viewModel by viewModels<MainActV2ViewModel>()

    var effectName: String? = null
    private lateinit var mediaBrowser: MediaBrowserCompat

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initView() {
        initService()
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.saveBtn.bindIsVisible(position == 1)
            }
        })
        initTabLayout()
        initViewPager()
        savePrefData()
    }

    private fun initTabLayout() {

    }

    override fun initAction() {
        binding.saveBtn.setOnClickListener { showDialogSave() }
        binding.settingBtn.setOnClickListener {
            startActivity(SettingsAct.getIntent(this))
        }
    }

    override fun getContentView(): Int {
        return R.layout.activity_main_v2
    }

    override fun bindViewModel() {

    }

    private fun savePrefData() {
        val pref = applicationContext.getSharedPreferences("myPref", MODE_PRIVATE)
        val editor = pref.edit()
        editor.putBoolean("isIntroOpened", true)
        editor.commit()
    }

    private fun initViewPager() {
        binding.tabV2.tabMode = TabLayout.MODE_SCROLLABLE
        binding.tabV2.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                switchTextStyle(tab)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                switchTextStyle(tab)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        binding.executeAfter {
            if (viewPager.adapter == null) {
                viewPager.isUserInputEnabled = false //Adjust horizontal swipe
                viewPager.isSaveEnabled = false
                viewPager.offscreenPageLimit = 2
                viewPager.adapter = object : FragmentStateAdapter(this@MainV2Act) {

                    override fun getItemCount(): Int {
                        return 2
                    }

                    override fun createFragment(position: Int): Fragment {
                        return when (position) {
                            0 -> VolumeFr()
                            1 -> EqualizerFrV2()
                            else -> throw IllegalStateException(
                                "ViewPage position $position"
                            )
                        }
                    }
                }
                TabLayoutMediator(
                    binding.tabV2,
                    binding.viewPager
                ) { tab, position ->
                    tab.text = when (position) {
                        0 -> getString(R.string.volume)
                        1 -> getString(R.string.equalizer)
                        else -> ""
                    }
                }.attach()


                // tablayout v2
                val str = arrayOf(
                    getString(R.string.volume),
                    getString(R.string.equalizer),
                )
                val titles = listOf(*str)
                binding.tabV2.setTitles(titles)

                binding.tabV2.setOnTabSelectedListener(object :OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        if(tab?.text == getString(R.string.volume)) {
                            Log.d("ThangND", "1111: ")
                            VolumeFr()
                        } else {
                            Log.d("ThangND", "2222: ")
                            EqualizerFrV2()
                        }
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {

                    }

                    override fun onTabReselected(tab: TabLayout.Tab?) {

                    }

                })


                intent?.apply {
                    viewPager.currentItem = this.getIntExtra(CURRENT_ITEM, 0)
                }
            }
        }
    }

    private fun switchTextStyle(tab: TabLayout.Tab?) {
        tab?.let {
            val textView = it.view.getChildAt(1) as TextView
            textView.typeface =
                Typeface.defaultFromStyle(if (it.isSelected) Typeface.BOLD else Typeface.NORMAL)
        }
    }

    private fun initService() {
        mediaBrowser = MediaBrowserCompat(
            this,
            ComponentName(this, MediaBrowserService::class.java),
            mediaBrowserConnectionCallback,
            null
        )

        mediaBrowser.connect()
        Log.d("ThangND", "isConnected: " + mediaBrowser.isConnected)
    }

    private val mediaBrowserConnectionCallback = object : MediaBrowserCompat.ConnectionCallback() {
        override fun onConnected() {
            if (mediaBrowser.isConnected) {
                val mediaController =
                    MediaControllerCompat(this@MainV2Act, mediaBrowser.sessionToken)
                val metadata = mediaController.metadata
                val songTitle = metadata?.getString(MediaMetadataCompat.METADATA_KEY_TITLE)
                if (songTitle != null) {
                    Log.d("ThangND", "songTitle: $songTitle")
                }

                mediaController.transportControls.play()
            } else {
                Toast.makeText(this@MainV2Act, "MediaBrowser is not connected", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun showDialogSave() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        val bindingRename: DialogSaveBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.dialog_save,
            null,
            false
        )
        alertDialogBuilder.setView(bindingRename.root)
        bindingRename.input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            @SuppressLint("ResourceAsColor")
            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                if (text.toString().isNotEmpty()) {
                    Log.d("Thang97", "onTextChanged: ${text.toString()}")
                    effectName = text.toString()
                    Log.d("Thang97", "onTextChanged effectName: $effectName")
                    bindingRename.saveBtn.setBackgroundResource(R.drawable.bg_save_select_btn)
                    bindingRename.saveBtn.setTextColor(Color.parseColor("#FFFFFF"))
                    bindingRename.input.setBackgroundResource(R.drawable.bg_editext_select_v2)
                } else {
                    bindingRename.saveBtn.setBackgroundResource(R.drawable.bg_save_unselect_btn)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        val alertDialog = alertDialogBuilder.create()
        bindingRename.saveBtn.setOnClickListener {
            val effectSound = EffectSound(null, name = effectName)
            viewModel.insert(effectSound)
            alertDialog.dismiss()
        }
        bindingRename.cancelBtn.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
    }

    companion object {
        private const val CURRENT_ITEM = "MainActivity-Current_Item"
        fun getIntent(context: Context): Intent {
            return Intent(context, MainV2Act::class.java)
        }
    }
}
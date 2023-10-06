package com.example.hahalolofake.ui.preset

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.hahalolofake.R
import com.example.hahalolofake.base.activities.BaseActivity
import com.example.hahalolofake.data.models.EffectSound
import com.example.hahalolofake.databinding.DialogDeleteBinding
import com.example.hahalolofake.databinding.PresetActBinding
import com.example.hahalolofake.ui.preset.adapter.PresetAdapter
import com.example.hahalolofake.ui.preset.adapter.PresetListener


class PresetAct : BaseActivity() {

    private lateinit var binding: PresetActBinding
    private lateinit var adapter: PresetAdapter

    private val viewModel by viewModels<PresentViewModel>()

    private val listener = object : PresetListener {
        override fun onClickSoundEffect(position: Int, item: EffectSound) {
            sharePrefs.addSoundPosition = position
            sharePrefs.addSoundName = item.name ?: ""
        }

        override fun onDeleteEffect(position: Int, item: EffectSound) {
            showDialogSave(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PresetActBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initAdapter()
        initAction()
    }

    private fun initAdapter() {
        viewModel.allItems.observe(this) { data ->
            if (data.isEmpty()) {
                PresetAdapter.dummyData.map {
                    viewModel.insert(it)
                }
            }
            adapter = PresetAdapter(data, this, getPosition(), listener)
            binding.presetRecycler.adapter = adapter
        }
    }

    private fun getPosition(): Int {
        val pref = applicationContext?.getSharedPreferences("myPref", MODE_PRIVATE)
        return pref?.getInt("positionEffect", 0) ?: 0
    }

    private fun initAction() {
        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.saveBtn.setOnClickListener {
            finish()
        }
    }

    private fun showDialogSave(item: EffectSound) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        val bindingRename: DialogDeleteBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.dialog_delete,
            null,
            false
        )
        alertDialogBuilder.setView(bindingRename.root)
        val alertDialog = alertDialogBuilder.create()
        bindingRename.questionTxt.text = getString(R.string.delete_preset_question, item.name)
        bindingRename.saveBtn.setOnClickListener {
            viewModel.delete(item)
            alertDialog.dismiss()
        }
        bindingRename.cancelBtn.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
    }

    companion object {
        const val REQUEST_CODE = 111

        fun getIntent(context: Context): Intent {
            return Intent(context, PresetAct::class.java)
        }
    }
}
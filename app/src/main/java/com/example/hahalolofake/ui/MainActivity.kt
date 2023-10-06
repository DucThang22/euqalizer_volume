package com.example.hahalolofake.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hahalolofake.R
import com.example.hahalolofake.component.ViewModelFactory
import com.example.hahalolofake.databinding.ActivityMainBinding
import com.example.hahalolofake.ui.adapter.CharacterAdapter
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity @Inject constructor() : DaggerAppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: MainActivityViewModel by viewModels {
        viewModelFactory
    }

    private val characterAdapter by lazy {
        CharacterAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initView()
        initData()
    }

    private fun initView() {
        binding.recycleView.layoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        binding.recycleView.adapter = characterAdapter

    }

    private fun initData() {
        viewModel.page.value = 1
        lifecycleScope.launch {
            viewModel.results.collectLatest {
                characterAdapter.submitData(it)
            }
        }
    }
}
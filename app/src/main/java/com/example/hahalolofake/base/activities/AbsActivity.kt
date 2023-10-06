package com.example.hahalolofake.base.activities

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.hahalolofake.component.ViewModelFactory
import com.example.hahalolofake.utils.SharePrefs
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class AbsActivity <DB : ViewDataBinding> : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory


    lateinit var binding: DB

    protected lateinit var sharePrefs: SharePrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getContentView())
        bindViewModel()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        sharePrefs = SharePrefs(this)
        initAction()
        initView()
    }

    abstract fun initView()

    abstract fun initAction()


    abstract fun getContentView(): Int
    abstract fun bindViewModel()
    override fun onStop() {
        super.onStop()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    }
}
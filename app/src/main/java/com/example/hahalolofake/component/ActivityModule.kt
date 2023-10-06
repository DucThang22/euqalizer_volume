package com.example.hahalolofake.component

import com.example.hahalolofake.ui.MainActivity
import com.example.hahalolofake.ui.main_v2.MainV2Act
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity


    @ContributesAndroidInjector
    abstract fun contributeMainV2Act(): MainV2Act

}
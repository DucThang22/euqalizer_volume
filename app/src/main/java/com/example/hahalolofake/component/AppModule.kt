package com.example.hahalolofake.component

import com.example.hahalolofake.data.api.ApiModule
import com.example.hahalolofake.data.repository.RepositoryModule
import dagger.Module

@Module(
    includes = [
        // module of Activity && ViewModel
        ActivityModule::class,
        ViewModelModule::class,

        // module of data
        ApiModule::class,
        RepositoryModule::class,
//        DataBaseModule::class
    ]
)
class AppModule
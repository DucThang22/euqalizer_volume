package com.example.hahalolofake.ui.preset

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.hahalolofake.data.database.EffectSoundDatabase
import com.example.hahalolofake.data.models.EffectSound
import com.example.hahalolofake.data.repository.EffectRepository
import kotlinx.coroutines.launch

class PresentViewModel(application: Application) : AndroidViewModel(application) {
    private val effectRepository: EffectRepository
    val allItems: LiveData<MutableList<EffectSound>>

    init {
        val dao = EffectSoundDatabase.getDatabase(application).effectDao()
        effectRepository = EffectRepository(dao)
        allItems = effectRepository.allItems
    }

    fun delete(item: EffectSound) {
        viewModelScope.launch {
            effectRepository.delete(item)
        }
    }

    fun insert(item: EffectSound) {
        Log.d("Thang97 8888", "MainActivityViewModel insert" + item.name)
        viewModelScope.launch {
            effectRepository.insert(item)
        }
    }
}
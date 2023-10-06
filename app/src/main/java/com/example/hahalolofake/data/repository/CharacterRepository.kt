package com.example.hahalolofake.data.repository

import androidx.paging.PagingData
import com.example.hahalolofake.data.models.ResultEntity
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getCharacter(page: Int): Flow<PagingData<ResultEntity>>
}
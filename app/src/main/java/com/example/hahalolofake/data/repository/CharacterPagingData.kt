package com.example.hahalolofake.data.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.hahalolofake.data.api.service.APIService
import com.example.hahalolofake.data.models.ResultEntity
import retrofit2.HttpException
import java.io.IOException

internal class CharacterPagingData(
    private val apiService: APIService
) : PagingSource<Int, ResultEntity>() {
    override fun getRefreshKey(state: PagingState<Int, ResultEntity>): Int {
        return 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultEntity> {
        val page = params.key ?: 1
        return try {
            val response = apiService.getCharacter(page)
            LoadResult.Page(data = response.results ?: mutableListOf(),
                prevKey = page.minus(1).takeIf { it > 0 },
                // plus nối chuỗi x với chuỗi cũ
                nextKey = page.plus(1).takeIf {
                    response.results!!.isNotEmpty()
                })
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}
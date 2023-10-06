package com.example.hahalolofake.data.api.service

import com.example.hahalolofake.data.models.ResponseApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("character")
    suspend fun getCharacter(@Query("page") page: Int) : ResponseApi
}
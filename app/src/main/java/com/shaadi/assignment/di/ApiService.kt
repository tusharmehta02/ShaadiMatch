package com.shaadi.assignment.di

import com.shaadi.assignment.data.model.remote.ShaadiResultResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/api/")
    suspend fun getShaadiMatches(
        @Query("results") result: Int = 50
    ): Response<ShaadiResultResponse>
}
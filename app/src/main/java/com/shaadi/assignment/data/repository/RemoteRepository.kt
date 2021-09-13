package com.shaadi.assignment.data.repository

import com.shaadi.assignment.data.model.remote.ShaadiResultResponse
import com.shaadi.assignment.di.ApiService
import com.shaadi.assignment.utils.ResponseWrapper
import javax.inject.Inject

class RemoteRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getShaadiMatches(): ResponseWrapper<ShaadiResultResponse> {
        val res = apiService.getShaadiMatches()
        return if (res.isSuccessful && res.body() != null) {
            ResponseWrapper.Success(res.body()!!)
        } else {
            ResponseWrapper.Error(res.code(), res.message())
        }
    }
}

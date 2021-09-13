package com.shaadi.assignment.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shaadi.assignment.data.model.local.ShaadiMatch
import com.shaadi.assignment.data.model.remote.ShaadiResultResponse
import com.shaadi.assignment.data.repository.LocalRepository
import com.shaadi.assignment.data.repository.RemoteRepository
import com.shaadi.assignment.di.module.StorageModule.Companion.IO_DISPATCHER
import com.shaadi.assignment.di.module.StorageModule.Companion.MAIN_DISPATCHER
import com.shaadi.assignment.utils.ResponseWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class ShaadiMatchViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository,
    @Named(IO_DISPATCHER) private val ioDispatcher: CoroutineDispatcher,
    @Named(MAIN_DISPATCHER) private val mainDispatcher: CoroutineDispatcher,
): ViewModel() {

    private val _shaadiMatchList = MutableLiveData<ArrayList<ShaadiResultResponse.Match>>()
    val shaadiMatchList: LiveData<ArrayList<ShaadiResultResponse.Match>> = _shaadiMatchList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun getShaadiMatches(){
        viewModelScope.launch(ioDispatcher) {
            _isLoading.postValue(true)
            val res = remoteRepository.getShaadiMatches()
            when(res){
                is ResponseWrapper.Success -> {
                    val list = res.data.results
                    getShaadiStatus(list)
                    _shaadiMatchList.postValue(res.data.results)
                }
                is ResponseWrapper.Error -> _errorMessage.postValue(res.message)
            }
            _isLoading.postValue(false)
        }

    }

    private suspend fun getShaadiStatus(list: java.util.ArrayList<ShaadiResultResponse.Match>) {
        list.forEach {
            it.matchStatus = localRepository.getMatchById(it.login.uuid)
        }
    }

    fun updateMatchStatus(shaadiMatch: ShaadiMatch){
        viewModelScope.launch(ioDispatcher) {
            localRepository.updateMatchStatus(shaadiMatch)
        }
    }
}
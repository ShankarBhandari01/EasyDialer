package com.example.easydialer.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    val isFirstLaunch: LiveData<Boolean> = dataStoreRepository.getFirstLaunch().asLiveData()
    fun setFirstLaunch(isFirstLaunch: Boolean) =
        viewModelScope.launch {
            dataStoreRepository.saveFirstLaunch(isFirstLaunch)
        }
}
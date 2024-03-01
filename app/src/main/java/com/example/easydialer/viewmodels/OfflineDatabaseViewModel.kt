package com.example.easydialer.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.easydialer.repository.OfflineStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfflineDatabaseViewModel @Inject constructor(
    private val offlineStoreRepository: OfflineStoreRepository
) : ViewModel() {

    val isFirstLaunch: LiveData<Boolean> = offlineStoreRepository.getFirstLaunch().asLiveData()
    fun setFirstLaunch(isFirstLaunch: Boolean) =
        viewModelScope.launch {
            offlineStoreRepository.saveFirstLaunch(isFirstLaunch)
        }

    fun getNoteEntity() {
        val note = offlineStoreRepository.getNote()
    }
}
package com.example.easydialer.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.easydialer.base.BaseViewModel
import com.example.easydialer.models.CampaignResponse
import com.example.easydialer.repository.CampaignRepository
import com.example.easydialer.utils.NetWorkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CampaignViewModel @Inject constructor(
    private val repository: CampaignRepository, application: Application
) : BaseViewModel(application) {

    private val _response: MutableLiveData<NetWorkResult<CampaignResponse>> = MutableLiveData()
    val response: LiveData<NetWorkResult<CampaignResponse>> = _response

    fun getCampaign() {
        viewModelScope.launch {
            repository.getCampaign(context).collect { campaign -> _response.value = campaign }
        }
    }
}
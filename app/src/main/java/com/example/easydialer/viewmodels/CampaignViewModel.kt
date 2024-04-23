package com.example.easydialer.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.easydialer.base.BaseViewModel
import com.example.easydialer.models.AgentList
import com.example.easydialer.models.CampaignResponse
import com.example.easydialer.models.CampaignSummary
import com.example.easydialer.models.DispositionList
import com.example.easydialer.models.DispositionUpdate
import com.example.easydialer.models.FollowUPStatus
import com.example.easydialer.models.MobileList
import com.example.easydialer.models.MobileListItem
import com.example.easydialer.repository.CampaignRepository
import com.example.easydialer.utils.NetWorkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CampaignViewModel @Inject constructor(
    private val repository: CampaignRepository, application: Application
) : BaseViewModel(application) {

    private val _responseCampaign: MutableLiveData<NetWorkResult<CampaignResponse>> =
        MutableLiveData()
    val responseCampaign: LiveData<NetWorkResult<CampaignResponse>> = _responseCampaign


    private val _responseCampaignAgent: MutableLiveData<NetWorkResult<AgentList>> =
        MutableLiveData()
    val responseCampaignAgent: LiveData<NetWorkResult<AgentList>> = _responseCampaignAgent


    private val _responseCampaignDisposition: MutableLiveData<NetWorkResult<DispositionList>> =
        MutableLiveData()
    val responseCampaignDisposition: LiveData<NetWorkResult<DispositionList>> =
        _responseCampaignDisposition

    private val _responseCampaignMobile: MutableLiveData<NetWorkResult<MobileList>> =
        MutableLiveData()

    val responseCampaignMobile: LiveData<NetWorkResult<MobileList>> = _responseCampaignMobile

    private val _responseCampaignSummary: MutableLiveData<NetWorkResult<CampaignSummary>> =
        MutableLiveData()

    val responseCampaignSummary: LiveData<NetWorkResult<CampaignSummary>> = _responseCampaignSummary

    private val _updateCampaignMobile: MutableLiveData<NetWorkResult<Any>> = MutableLiveData()

    val updateCampaignMobileState: LiveData<NetWorkResult<Any>> = _updateCampaignMobile


    fun getCampaign() {
        viewModelScope.launch {
            repository.getCampaign(context)
                .collect { campaign -> _responseCampaign.value = campaign }
        }
    }

    fun getCampaignAgent(id: Int) {
        viewModelScope.launch {
            repository.getCampaignAgent(context, id).collect { _responseCampaignAgent.value = it }
        }
    }

    fun getCampaignDisposition(id: Int) {
        viewModelScope.launch {
            repository.getCampaignDisposition(context, id)
                .collect { _responseCampaignDisposition.value = it }
        }
    }

    fun getCampaignMobile(id: Int) {
        viewModelScope.launch {
            repository.getCampaignMobile(context, id).collect { _responseCampaignMobile.value = it }
        }
    }

    fun getCampaignSummary(id: Int) {
        viewModelScope.launch {
            repository.getCampaignSummary(context, id).collect {
                _responseCampaignSummary.value = it
            }
        }
    }


    fun updateCampaignMobile(
        followUPStatus: FollowUPStatus,
        selectedMobileListItem: MobileListItem
    ) {
        val dispositionUpdate = DispositionUpdate()
        dispositionUpdate.campaignId = selectedMobileListItem.campaign_id
        dispositionUpdate.mobile = selectedMobileListItem.mobile
        dispositionUpdate.datetime = selectedMobileListItem.dialed_at
        dispositionUpdate.type = followUPStatus.type
        dispositionUpdate.remarks = ""
        viewModelScope.launch {
            repository.updateCampaignMobile(context, dispositionUpdate).collect {
                _updateCampaignMobile.value = it
            }
        }
    }

}
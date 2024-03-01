package com.example.easydialer.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.easydialer.base.BaseViewModel
import com.example.easydialer.models.DataModel
import com.example.easydialer.repository.LoginRepo
import com.example.easydialer.utils.NetWorkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepo, application: Application
) : BaseViewModel(application) {
    private val _response: MutableLiveData<NetWorkResult<List<DataModel>>> = MutableLiveData()
    val response: LiveData<NetWorkResult<List<DataModel>>> = _response


    fun getAgents() = viewModelScope.launch {
        repository.getAgents(context).collect { values ->
            _response.value = values
        }
    }

}
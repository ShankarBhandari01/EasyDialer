package com.example.easydialer.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.easydialer.base.BaseViewModel
import com.example.easydialer.models.Agent
import com.example.easydialer.models.Login
import com.example.easydialer.models.LoginResponse
import com.example.easydialer.repository.LoginRepo
import com.example.easydialer.utils.NetWorkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepo,
    application: Application
): BaseViewModel(application) {
    private val _response: MutableLiveData<NetWorkResult<List<Agent>>> = MutableLiveData()
    val response: LiveData<NetWorkResult<List<Agent>>> = _response


    private val _responseLogin: MutableLiveData<NetWorkResult<LoginResponse>> = MutableLiveData()
    val responseLogin: LiveData<NetWorkResult<LoginResponse>> = _responseLogin


    fun getAgents() = viewModelScope.launch {
        repository.getAgents(context).collect { values ->
            _response.value = values
        }
    }

    fun login(login: Login) = viewModelScope.launch {
        repository.login(context, login = login).collect {
            _responseLogin.value = it
        }
    }

    fun updatePassword() {

    }


}
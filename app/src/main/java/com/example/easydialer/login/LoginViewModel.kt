package com.example.easydialer.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.easydialer.data.repository.LoginRepo
import com.example.easydialer.base.BaseViewModel
import com.example.easydialer.data.ApiResponseData
import com.example.easydialer.data.Post
import com.example.easydialer.utils.NetWorkResult
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepo, application: Application
) : BaseViewModel(application) {
    private val _response: MutableLiveData<NetWorkResult<ApiResponseData>> = MutableLiveData()
    val response: LiveData<NetWorkResult<ApiResponseData>> = _response

    private val _responseposts: MutableLiveData<NetWorkResult<List<Post>>> = MutableLiveData()
    val responseposts: LiveData<NetWorkResult<List<Post>>> = _responseposts

    fun getProductsList(jsonObject: JsonObject) = viewModelScope.launch {
        repository.getProductList(context, jsonObject).collect { values ->
            _response.value = values
        }
    }

    fun getPostsList() = viewModelScope.launch {
        repository.getPostList(context).collect { values ->
            _responseposts.value = values
        }
    }
}
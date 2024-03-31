package com.example.easydialer.utils

import android.content.Context
import com.example.easydialer.utils.Constants.Companion.API_FAILURE_CODE
import com.example.easydialer.utils.Constants.Companion.API_INTERNET_CODE
import com.example.easydialer.utils.Constants.Companion.API_INTERNET_MESSAGE
import com.example.easydialer.utils.Constants.Companion.API_SOMETHING_WENT_WRONG_MESSAGE
import kotlin.reflect.full.memberProperties


class ApiResultHandler<T>(private val context: Context,  private val onLoading: () -> Unit, private val onSuccess: (T?) -> Unit, private val onFailure: (T?) -> Unit) {

    fun handleApiResult(result: NetWorkResult<T?>) {
        when (result.status) {
            ApiStatus.LOADING -> {
               onLoading()
            }
            ApiStatus.SUCCESS -> {
                onSuccess(result.data)
            }

            ApiStatus.ERROR -> {
                onFailure(result.data)
                Utils.showAlertDialog(context, result.message)
            }
        }
    }


}

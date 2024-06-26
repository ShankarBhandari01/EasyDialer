package com.example.easydialer.utils


import android.content.Context
import com.example.easydialer.utils.Constants.Companion.API_FAILED_CODE
import com.example.easydialer.utils.Constants.Companion.API_INTERNET_CODE
import com.example.easydialer.utils.Constants.Companion.API_INTERNET_MESSAGE
import com.example.easydialer.utils.Utils.isOnline
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.memberProperties


inline fun <reified T> toResultFlow(
    context: Context, crossinline call: suspend () -> Response<T>?
): Flow<NetWorkResult<T>> {
    return flow {
        val isInternetConnected = Utils.hasInternetConnection(context) && isOnline()
        if (isInternetConnected) {
            emit(NetWorkResult.Loading(true))
            val c = call()
            c?.let { response ->
                try {
                    if (c.isSuccessful && c.body() != null) {
                        c.body()?.let {
                            emit(NetWorkResult.Success(it))
                        }
                    } else {
                        if (c.code() != 500) {
                            val model = setResponseStatus<T>(
                                T::class.java.getDeclaredConstructor().newInstance(),
                                response.code().toString(),
                                response.message()
                            )
                            emit(NetWorkResult.Error(model, response.message()))
                        } else {
                            val model = setResponseStatus<T>(
                                null,
                                response.code().toString(),
                                response.message()
                            )
                            emit(NetWorkResult.Error(model, response.message()))
                        }


                    }
                } catch (e: Exception) {
                    emit(NetWorkResult.Error(null, e.message))
                }
            }
        } else {
            emit(NetWorkResult.Error(null, API_INTERNET_MESSAGE))
        }
    }.flowOn(Dispatchers.IO)
}

inline fun <reified T> setResponseStatus(instance: T?, errorCode: String?, message: String?): T? {
    return try {
        instance?.let {
            val properties = it::class.memberProperties
            for (property in properties) {
                if (property is KMutableProperty<*>) {
                    when (property.name) {
                        "ErrorCode" -> (property as KMutableProperty<*>).setter.call(
                            instance, errorCode
                        )

                        "Message" -> (property as KMutableProperty<*>).setter.call(
                            instance, message
                        )
                    }
                }
            }
        }
        instance
    } catch (e: Exception) {
        null
    }
}

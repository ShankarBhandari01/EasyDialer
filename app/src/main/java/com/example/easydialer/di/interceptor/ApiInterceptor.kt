package com.example.easydialer.di.interceptor

import android.content.Context
import com.example.easydialer.utils.Constants
import com.example.easydialer.utils.Utils
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ApiInterceptor(var context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var newRequest: Request = chain.request()

        newRequest = newRequest.newBuilder()
            .addHeader(
                "AccessToken",
                Constants.API_KEY
            )
            .build()

        return chain.proceed(newRequest)
    }
}


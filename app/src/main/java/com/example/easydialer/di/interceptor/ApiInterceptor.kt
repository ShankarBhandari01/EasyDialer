package com.example.easydialer.di.interceptor

import com.example.easydialer.utils.Constants
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
//        val original: Request = chain.request()
//        val httpUrl: HttpUrl = original.url
//        val url: HttpUrl = httpUrl.newBuilder()
//            .addQueryParameter("api_key", Constants.API_KEY)
//            .build()
//        val request: Request = original.newBuilder().url(url).build()
//        return chain.proceed(request)

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


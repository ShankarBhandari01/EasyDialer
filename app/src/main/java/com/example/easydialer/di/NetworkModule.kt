package com.example.easydialer.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.easydialer.data.remote.ApiService
import com.example.easydialer.di.interceptor.ApiInterceptor
import com.example.easydialer.di.qualifiers.ApiInterceptorQualifier
import com.example.easydialer.utils.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    fun providesChuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor {
        return ChuckerInterceptor(context)
    }

    @Singleton
    @Provides
    fun provideHttpClient(
        chuckerInterceptor: ChuckerInterceptor,
        loggingInterceptor: HttpLoggingInterceptor,
        @ApiInterceptorQualifier apiInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(loggingInterceptor)
            .addInterceptor(apiInterceptor)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(chuckerInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @ApiInterceptorQualifier
    @Singleton
    @Provides
    fun providesApiKeyInterceptor(): Interceptor = ApiInterceptor()

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
            .addConverterFactory(gsonConverterFactory).build()
    }

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}
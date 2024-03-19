package com.nanda.githubsearch.network.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import okhttp3.Interceptor
import retrofit2.converter.gson.GsonConverterFactory
import com.nanda.githubsearch.BuildConfig

object ApiConfig {
    private const val BASE_URL = BuildConfig.BASE_URL
    private const val KEY_TOKEN = BuildConfig.TOKEN

    private val loggingInterceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("Authorization", KEY_TOKEN ?: "")
            .build()
        chain.proceed(request)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL ?: "")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val instance: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
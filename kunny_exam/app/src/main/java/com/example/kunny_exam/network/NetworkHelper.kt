package com.example.kunny_exam.network

import android.content.Context
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class NetworkHelper(private val context : Context) {
    private val BASE_URL = "https://api.github.com/"

    private lateinit var retrofit: Retrofit

    private lateinit var apiService: RetrofitService

    fun getRetrofitService(): RetrofitService {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL) //api의 baseURL
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        apiService = retrofit.create(RetrofitService::class.java)
        return apiService
    }
}
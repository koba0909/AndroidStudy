package com.study.androidstudy_hoon.data.service

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GithubServiceBuilder {

    private const val BASE_URL = "https://api.github.com/"

    val githubSearchBuilder: GithubRepoService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GithubRepoService::class.java)
}

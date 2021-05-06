package com.example.kunny_exam.service

import com.example.kunny_exam.dto.SearchRepoResponse
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("search/repositories")
    fun getSearchRepo(@Query("q") q: String) : Call<SearchRepoResponse>

    @GET("search/repositories")
    fun getSearchRepoObservable(@Query("q") q: String) : Single<SearchRepoResponse>
}
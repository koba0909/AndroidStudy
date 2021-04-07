package com.example.kunny_exam.network

import com.example.kunny_exam.data.SearchRepoData
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("search/repositories")
    fun getSearchRepo(@Query("q") q: String) : Call<SearchRepoData>

    @GET("search/repositories")
    fun getSearchRepoObservable(@Query("q") q: String) : Observable<SearchRepoData>
}
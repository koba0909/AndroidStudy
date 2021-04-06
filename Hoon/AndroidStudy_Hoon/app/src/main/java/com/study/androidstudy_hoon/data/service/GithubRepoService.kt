package com.study.androidstudy_hoon.data.service

import com.study.androidstudy_hoon.data.dto.RepoSearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubRepoService {
    @GET("search/repositories")
    fun searchRepository(
            @Query("q") query: String
    ): Single<RepoSearchResponse>
}
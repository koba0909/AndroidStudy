package com.study.androidstudy_hoon.data.repository

import com.study.androidstudy_hoon.data.dto.RepoSearchResponse
import io.reactivex.Single

interface GithubSearchRepository {
    fun getGithubSearchRepository(query: String): Single<RepoSearchResponse>
}
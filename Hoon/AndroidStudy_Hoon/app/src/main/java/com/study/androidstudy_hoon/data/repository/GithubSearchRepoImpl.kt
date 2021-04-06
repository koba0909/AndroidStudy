package com.study.androidstudy_hoon.data.repository

import com.study.androidstudy_hoon.data.dto.RepoSearchResponse
import com.study.androidstudy_hoon.data.service.GithubServiceBuilder
import io.reactivex.Single

class GithubSearchRepoImpl(
        private val retrofitInstance: GithubServiceBuilder
) : GithubSearchRepository {
    override fun getGithubSearchRepository(query: String): Single<RepoSearchResponse> {
        return retrofitInstance.githubSearchBuilder.searchRepository(query)
    }
}


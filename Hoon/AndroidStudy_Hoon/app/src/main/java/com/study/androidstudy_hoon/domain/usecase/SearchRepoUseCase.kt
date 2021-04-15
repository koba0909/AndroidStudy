package com.study.androidstudy_hoon.domain.usecase

import com.study.androidstudy_hoon.data.dto.Repo
import com.study.androidstudy_hoon.data.repository.GithubSearchRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class SearchRepoUseCase(private val githubSearchRepository: GithubSearchRepository) {
    fun getSearchResult(query: String): Single<List<Repo>> =
        githubSearchRepository.getGithubSearchRepository(query)
                .subscribeOn(Schedulers.io())
                .map {
                    it.items
                }
}
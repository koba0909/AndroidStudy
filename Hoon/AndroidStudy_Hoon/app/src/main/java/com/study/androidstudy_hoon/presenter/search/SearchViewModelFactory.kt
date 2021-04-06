package com.study.androidstudy_hoon.presenter.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.study.androidstudy_hoon.data.repository.GithubSearchRepoImpl
import com.study.androidstudy_hoon.data.service.GithubServiceBuilder
import com.study.androidstudy_hoon.domain.usecase.SearchRepoUseCase

class SearchViewModelFactory(private val searchRepoUseCase: SearchRepoUseCase) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            SearchViewModel(searchRepoUseCase = searchRepoUseCase) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}
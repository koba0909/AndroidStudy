package com.study.androidstudy_hoon

import androidx.lifecycle.ViewModelProvider
import com.study.androidstudy_hoon.data.repository.GithubSearchRepoImpl
import com.study.androidstudy_hoon.data.service.GithubServiceBuilder
import com.study.androidstudy_hoon.domain.usecase.SearchRepoUseCase
import com.study.androidstudy_hoon.presenter.search.SearchViewModelFactory

object Injection {

    private fun provideSearchRepoUseCase(): SearchRepoUseCase {
        return SearchRepoUseCase(GithubSearchRepoImpl(GithubServiceBuilder))
    }

    fun provideSearchViewModelFactory(): ViewModelProvider.Factory {
        return SearchViewModelFactory(provideSearchRepoUseCase())
    }
}
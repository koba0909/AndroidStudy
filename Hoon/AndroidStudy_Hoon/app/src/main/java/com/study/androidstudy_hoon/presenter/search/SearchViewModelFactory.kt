package com.study.androidstudy_hoon.presenter.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.study.androidstudy_hoon.domain.usecase.RoomRepoUseCase
import com.study.androidstudy_hoon.domain.usecase.SearchRepoUseCase

class SearchViewModelFactory(private val searchRepoUseCase: SearchRepoUseCase, private val roomRepoUseCase: RoomRepoUseCase) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            SearchViewModel(searchRepoUseCase = searchRepoUseCase, roomRepoUseCase) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}
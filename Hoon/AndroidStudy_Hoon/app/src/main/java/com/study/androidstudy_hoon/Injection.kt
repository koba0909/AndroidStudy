package com.study.androidstudy_hoon

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.study.androidstudy_hoon.data.repository.GithubSearchRepoImpl
import com.study.androidstudy_hoon.data.repository.RoomRepository
import com.study.androidstudy_hoon.data.service.GithubServiceBuilder
import com.study.androidstudy_hoon.domain.usecase.RoomRepoUseCase
import com.study.androidstudy_hoon.domain.usecase.SearchRepoUseCase
import com.study.androidstudy_hoon.presenter.main.MainViewModelFactory
import com.study.androidstudy_hoon.presenter.search.SearchViewModelFactory

object Injection {

    private fun provideSearchRepoUseCase(): SearchRepoUseCase {
        return SearchRepoUseCase(GithubSearchRepoImpl(GithubServiceBuilder))
    }

    fun provideSearchViewModelFactory(context: Context): ViewModelProvider.Factory {
        return SearchViewModelFactory(provideSearchRepoUseCase(), provideRoomRepoUseCase(context))
    }

    private fun provideRoomRepoUseCase(context: Context): RoomRepoUseCase {
        return RoomRepoUseCase(RoomRepository(context = context))
    }

    fun provideMainViewModelFactory(context: Context): ViewModelProvider.Factory {
        return MainViewModelFactory(provideRoomRepoUseCase(context))
    }
}
package com.study.androidstudy_hoon.presenter.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.study.androidstudy_hoon.domain.usecase.RoomRepoUseCase

class MainViewModelFactory(private val roomRepoUseCase: RoomRepoUseCase) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(roomRepoUseCase) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}
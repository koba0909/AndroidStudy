package com.example.kunny_exam.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kunny_exam.model.dto.RepoEntity
import com.example.kunny_exam.repository.RoomRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val roomRepository: RoomRepository)
    : BaseViewModel() {

    private val _roomRepoList : MutableLiveData<List<RepoEntity>> = MutableLiveData()
    val roomRepoList : LiveData<List<RepoEntity>>
        get() = this._roomRepoList

    fun getRoomSearchList() {
        addDisposable(
            roomRepository.getAllRepoEntities()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe {
                    _roomRepoList.value = it
                }
        )
    }
}
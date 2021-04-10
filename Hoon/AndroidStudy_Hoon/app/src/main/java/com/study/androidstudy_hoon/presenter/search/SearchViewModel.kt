package com.study.androidstudy_hoon.presenter.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.study.androidstudy_hoon.data.dto.Repo
import com.study.androidstudy_hoon.domain.base.BaseViewModel
import com.study.androidstudy_hoon.domain.usecase.SearchRepoUseCase
import io.reactivex.android.schedulers.AndroidSchedulers

class SearchViewModel(private val searchRepoUseCase: SearchRepoUseCase) : BaseViewModel() {

    private val _searchRepoList = MutableLiveData<List<Repo>>()
    val searchRepoList: LiveData<List<Repo>>
        get() = _searchRepoList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun fetchRepoSearch(query: String?) {
        addDisposable(searchRepoUseCase.getSearchResult(query?: "")
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _isLoading.value = true }
                .doOnTerminate { _isLoading.value = false }
                .subscribe({
                    _searchRepoList.value = it
                }, {
                   Log.e("Error","$it")
                })
        )
    }

}
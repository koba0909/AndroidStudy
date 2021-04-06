package com.study.androidstudy_hoon.presenter.search

import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
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
                .subscribe({
                    _searchRepoList.value = it
                }, {
                   Log.e("Error","$it")
                })
        )
    }

    fun showProgress(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

}
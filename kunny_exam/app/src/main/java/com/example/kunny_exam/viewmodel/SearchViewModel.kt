package com.example.kunny_exam.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kunny_exam.dto.SearchRepoInfo
import com.example.kunny_exam.repository.SearchRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchViewModel(private val searchRepository: SearchRepository) : BaseViewModel() {
    private val _repo : MutableLiveData<List<SearchRepoInfo>> = MutableLiveData()
    val repo : LiveData<List<SearchRepoInfo>>
        get() = _repo

    fun getSearchRepoList(keyword : String){
        addDisposable(
            searchRepository.getSearchRepoList(keyword)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { it->
                    _repo.value = it
                }
        )
    }
}
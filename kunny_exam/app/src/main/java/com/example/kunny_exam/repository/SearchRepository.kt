package com.example.kunny_exam.repository

import android.content.Context
import com.example.kunny_exam.dto.SearchRepoInfo
import com.example.kunny_exam.service.NetworkHelper
import io.reactivex.Observable
import io.reactivex.Single

class SearchRepository(private val context: Context) {
    private val retrofitService by lazy { NetworkHelper(context).getRetrofitService() }

    fun getSearchRepoList(keyword : String) : Single<List<SearchRepoInfo>> {
        return retrofitService.getSearchRepoObservable(keyword).map { it.items }
    }
}
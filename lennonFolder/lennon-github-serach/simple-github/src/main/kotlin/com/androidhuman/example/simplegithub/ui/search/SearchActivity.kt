package com.androidhuman.example.simplegithub.ui.search

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.androidhuman.example.simplegithub.R
import com.androidhuman.example.simplegithub.api.GithubApi
import com.androidhuman.example.simplegithub.api.model.GithubRepo
import com.androidhuman.example.simplegithub.api.provideGithubApi
import com.androidhuman.example.simplegithub.data.provideSearchHistoryDao
import com.androidhuman.example.simplegithub.databinding.ActivitySearchBinding
import com.androidhuman.example.simplegithub.ui.repo.RepositoryActivity
import com.androidhuman.example.simplegithub.ui.search.SearchAdapter.ItemClickListener
import com.androidhuman.example.simplegithub.util.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class SearchActivity : AppCompatActivity(), ItemClickListener {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var menuSearch: MenuItem
    private lateinit var searchView: SearchView
    private val adapter: SearchAdapter by lazy {
        SearchAdapter().apply {
            setItemClickListener(this@SearchActivity)
        }
    }
    private val api: GithubApi by lazy { provideGithubApi(this) }

    //internal var searchCall: Call<RepoSearchResponse>? = null
    private val disposables = CompositeDisposable()

    // 데이터베이스 생성 후 Dao 인스턴스 받기
    private val searchHistoryDao by lazy { provideSearchHistoryDao(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding.rvActivitySearchList) {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = this@SearchActivity.adapter
        }
    }

    override fun onStop() {
        super.onStop()
        //searchCall?.run { cancel() }
        disposables.clear()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_activity_search, menu)
        menuSearch = menu.findItem(R.id.menu_activity_search_query)
        searchView = (menuSearch.actionView as SearchView).apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                // 검색어 입력 후 검색을 눌렀을때 콜백
                override fun onQueryTextSubmit(query: String): Boolean {
                    updateTitle(query)
                    hideSoftKeyboard(this@SearchActivity, searchView)
                    collapseSearchView()
                    searchRepository(query)
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    return false
                }
            })
        }

        with(menuSearch) {
            setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
                override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                    return true
                }

                override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                    if (searchView.query.isEmpty()) {
                        finish()
                    }
                    return true
                }
            })
            expandActionView()
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (R.id.menu_activity_search_query == item.itemId) {
            item.expandActionView()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(repository: GithubRepo) {

        disposables.add(Completable.fromCallable {
            searchHistoryDao.add(repository)
        }
                .subscribeOn(Schedulers.io())
                .subscribe())

        val intent = Intent(this, RepositoryActivity::class.java).apply {
            putExtra(RepositoryActivity.KEY_USER_LOGIN, repository.owner.login)
            putExtra(RepositoryActivity.KEY_REPO_NAME, repository.name)
        }
        startActivity(intent)
    }

    private fun searchRepository(query: String) {

        disposables.add(api.searchRepository(query).flatMap {
            if (0 == it.totalCount) {
                Observable.error(IllegalStateException("No search result"))
            } else {
                Observable.just(it.items)
            }
        }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    with(adapter) {
                        clearResults()
                    }
                    hideError()
                    showProgress()
                }
                .doOnTerminate { hideProgress() }
                .subscribe({ items ->
                    with(adapter) {
                        updateItem(items)
                    }
                }, {
                    showError(it.message)
                }))
        //Rx 자바를 쓰기전 코드
//        clearResults()
//        hideError()
//        showProgress()
//        searchCall = api.searchRepository(query)
//        searchCall!!.enqueue(object : Callback<RepoSearchResponse> {
//            override fun onResponse(call: Call<RepoSearchResponse>,
//                                    response: Response<RepoSearchResponse>) {
//                hideProgress()
//                val searchResult = response.body()
//                if (response.isSuccessful && null != searchResult) {
//                    // 검색 결과를 어댑터의 데이터에 갱신
//                    with(adapter) {
//                        setItems(searchResult.items)
//                        notifyDataSetChanged()
//                    }
//                    if (0 == searchResult.totalCount) {
//                        showError(getString(R.string.no_search_result))
//                    }
//                } else {
//                    showError("Not successful: " + response.message())
//                }
//            }
//
//            override fun onFailure(call: Call<RepoSearchResponse?>, t: Throwable) {
//                hideProgress()
//                showError(t.message)
//            }
//        })
    }

    private fun updateTitle(query: String) {
        supportActionBar?.run {
            subtitle = query
        }
    }

    private fun collapseSearchView() {
        menuSearch.collapseActionView()
    }

    private fun showProgress() {
        binding.pbActivitySearch.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        binding.pbActivitySearch.visibility = View.GONE
    }

    private fun showError(message: String?) {
        with(binding.tvActivitySearchMessage) {
            text = message ?: "Unexpected errorsssssss."
            visibility = View.VISIBLE
        }
    }

    private fun hideError() {
        with(binding.tvActivitySearchMessage) {
            text = ""
            visibility = View.GONE
        }
    }
}
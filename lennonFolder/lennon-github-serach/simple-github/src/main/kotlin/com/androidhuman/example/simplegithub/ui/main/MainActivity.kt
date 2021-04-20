package com.androidhuman.example.simplegithub.ui.main

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.androidhuman.example.simplegithub.R
import com.androidhuman.example.simplegithub.api.model.GithubRepo
import com.androidhuman.example.simplegithub.data.provideSearchHistoryDao
import com.androidhuman.example.simplegithub.databinding.ActivityMainBinding
import com.androidhuman.example.simplegithub.rx.AutoClearedDisposable
import com.androidhuman.example.simplegithub.ui.repo.RepositoryActivity
import com.androidhuman.example.simplegithub.ui.search.SearchActivity
import com.androidhuman.example.simplegithub.ui.search.SearchAdapter
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

// 이 액티비티는 현재 저장소 검색 액티비티를 호출하는 기능만 구현
class MainActivity : AppCompatActivity(), SearchAdapter.ItemClickListener {

    private val adapter by lazy { SearchAdapter().apply { setItemClickListener(this@MainActivity) } }
    private val searchHistoryDao by lazy { provideSearchHistoryDao(this) }
    private val disposables = AutoClearedDisposable(this)

    private lateinit var binding: ActivityMainBinding
    private lateinit var btnSearch: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycle.addObserver(disposables)
        lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            fun fetch() {
                fetchSearchHistory()
            }
        })

        btnSearch = binding.btnActivityMainSearch
        btnSearch.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        with(binding.rvActivityMainList) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    private fun fetchSearchHistory(): Disposable = searchHistoryDao.getHistory()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ items ->
                with(adapter) {
                    setItems(items)
                    notifyDataSetChanged()
                }

                if (items.isEmpty()) {
                    showMessage(getString(R.string.no_recent_repositories))
                } else {
                    hideMessage()
                }
            }) {
                showMessage(it.message)
            }

    private fun hideMessage() {
        with(binding.tvActivityMainMessage) {
            text = ""
            visibility = View.GONE
        }
    }

    private fun showMessage(message: String?) {
        with(binding.tvActivityMainMessage) {
            text = message ?: "Unexpected error"
            visibility = View.VISIBLE
        }
    }

    override fun onItemClick(repository: GithubRepo) {

        startActivity(Intent(this, RepositoryActivity::class.java).run {
            putExtra(RepositoryActivity.KEY_REPO_NAME, repository.owner.login)
            putExtra(RepositoryActivity.KEY_REPO_NAME, repository.name)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_search, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (R.id.menu_activity_main_clear_all == item?.itemId) {
            clearAll()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun clearAll() {
        disposables.add(Completable.fromCallable {
            searchHistoryDao.clearAll()
        }
                .subscribeOn(Schedulers.io())
                .subscribe())
    }
}
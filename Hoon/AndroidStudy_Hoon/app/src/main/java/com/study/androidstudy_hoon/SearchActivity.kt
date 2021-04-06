package com.study.androidstudy_hoon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.study.androidstudy_hoon.data.repository.GithubSearchRepoImpl
import com.study.androidstudy_hoon.data.service.GithubServiceBuilder
import com.study.androidstudy_hoon.databinding.ActivitySearchBinding
import com.study.androidstudy_hoon.domain.usecase.SearchRepoUseCase
import com.study.androidstudy_hoon.presenter.search.SearchAdapter
import com.study.androidstudy_hoon.presenter.search.SearchViewModel
import com.study.androidstudy_hoon.presenter.search.SearchViewModelFactory

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModel: SearchViewModel
    private lateinit var adapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        val view = binding.root

        viewModel = ViewModelProvider(this, SearchViewModelFactory(SearchRepoUseCase(GithubSearchRepoImpl(GithubServiceBuilder))))
                .get(SearchViewModel::class.java)

        setContentView(view)
        initAdapter()
    }

    private fun initAdapter() {
        adapter = SearchAdapter(applicationContext)
        binding.searchRecyclerView.adapter = adapter
        binding.searchRecyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        viewModel.searchRepoList.observe(this, {
            viewModel.showProgress(false, binding.searchProgress, binding.searchRecyclerView)
            adapter.searchRepoList = it
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.showProgress(true, binding.searchProgress, binding.searchRecyclerView)
                viewModel.fetchRepoSearch(query?.trim())
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return false
            }
        })
        return true
    }
}
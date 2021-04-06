package com.study.androidstudy_hoon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
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
    private val searchAdapter by lazy { SearchAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)


        viewModel = ViewModelProvider(this, Injection.provideSearchViewModelFactory())
                .get(SearchViewModel::class.java)

        setContentView(binding.root)
        initObserve()
        initAdapter()
    }

    private fun initObserve() {
        viewModel.searchRepoList.observe(this, {
            viewModel.showProgress(false)
            searchAdapter.searchRepoList = it
        })

        viewModel.isLoading.observe(this, { isLoading ->
            if (isLoading) {
                binding.searchProgress.visibility = View.VISIBLE
                binding.searchRecyclerView.visibility = View.GONE
            } else {
                binding.searchProgress.visibility = View.GONE
                binding.searchRecyclerView.visibility = View.VISIBLE
            }
        })
    }

    private fun initAdapter() {
        binding.searchRecyclerView.apply {
            adapter = searchAdapter
            addItemDecoration(DividerItemDecoration(this@SearchActivity, LinearLayoutManager.VERTICAL))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        val searchView = menu?.findItem(R.id.action_search)?.actionView as? SearchView
        searchView?.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.showProgress(true)
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
package com.study.androidstudy_hoon

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.study.androidstudy_hoon.databinding.ActivitySearchBinding
import com.study.androidstudy_hoon.domain.base.BaseActivity
import com.study.androidstudy_hoon.presenter.search.DetailFragment
import com.study.androidstudy_hoon.presenter.search.SearchAdapter
import com.study.androidstudy_hoon.presenter.search.SearchViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchActivity : BaseActivity<ActivitySearchBinding>({ ActivitySearchBinding.inflate(it) }) {

    private lateinit var viewModel: SearchViewModel
    private val searchAdapter by lazy { SearchAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel =
            ViewModelProvider(this, Injection.provideSearchViewModelFactory(applicationContext))
                .get(SearchViewModel::class.java)

        setContentView(binding.root)
        initObserve()
        initAdapter()
    }

    private fun initObserve() {
        viewModel.searchRepoList.observe(this, { repositoryList ->
            searchAdapter.submitList(repositoryList)
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
            addItemDecoration(
                DividerItemDecoration(
                    this@SearchActivity,
                    LinearLayoutManager.VERTICAL
                )
            )
        }

        addDisposable(
            searchAdapter.clickSubject.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    supportFragmentManager.beginTransaction()
                        .add(
                            binding.searchActivityContainer.id, DetailFragment.newInstance(it),
                        )
                        .addToBackStack(null)
                        .commit()

                    // Room 데이터 저장
                    viewModel.insertSearchRepoData(it)
                })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        val searchView = menu?.findItem(R.id.action_search)?.actionView as? SearchView
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.fetchRepoSearch(query?.trim())
                menu.getItem(0)?.collapseActionView()
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return false
            }
        })
        return true
    }
}
package com.example.kunny_exam.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.example.kunny_exam.*
import com.example.kunny_exam.dto.toRepoEntity
import com.example.kunny_exam.databinding.ActivitySearchBinding
import com.example.kunny_exam.model.database.SearchRoomDB
import com.example.kunny_exam.service.NetworkHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class SearchActivity : AppCompatActivity() {
    private val TAG = SearchActivity::class.java.name

    private val retrofitService by lazy { NetworkHelper(this).getRetrofitService() }
    private val repoAdapter by lazy { SearchRepoListAdapter(this) }
    private val searchRoomDB by lazy { SearchRoomDB.getInstance(this) }
    private val searchRepoDao by lazy { searchRoomDB.getRepoDao() }
    private val binding by lazy { ActivitySearchBinding.inflate(LayoutInflater.from(this)) }

    private var svSearch : SearchView? = null
    lateinit var menuSearch : MenuItem

    private val compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)

        menuSearch = menu.findItem(R.id.menu_search_icon)

        svSearch = menuSearch.actionView as? SearchView
        if(svSearch != null){
            svSearch!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String): Boolean {
                    try {
                        getRepoListObservable(query)
                    }catch (e : Exception){
                        e.printStackTrace()
                    }

                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })
        }
        return true
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId) {
            R.id.menu_search_icon ->{
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun getRepoListObservable(keyword : String){
        val disposable = retrofitService.getSearchRepoObservable(keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                        with(repoAdapter){
                            submitList(response.items)
                            binding.rvSearch.adapter = this
                            compositeDisposable.add(searchRepoClick())
                    }
                }
        compositeDisposable.add(disposable)
    }

    private fun hideSoftKeyboard() {
        (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).run {
            hideSoftInputFromWindow(svSearch!!.windowToken, 0)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.clear()
    }

    private fun searchRepoClick() : Disposable {
        return repoAdapter. getOnItemClickObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe { item ->
                    searchRepoDao.add(item.toRepoEntity())

                    val intent = Intent(this@SearchActivity,
                            SearchRepoDetailActivity::class.java)
                    startActivity(intent)
                }
    }
}
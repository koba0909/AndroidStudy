package com.example.kunny_exam.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kunny_exam.*
import com.example.kunny_exam.data.SearchRepoData
import com.example.kunny_exam.data.SearchRepoInfo
import com.example.kunny_exam.data.toRepoEntity
import com.example.kunny_exam.network.NetworkHelper
import com.example.kunny_exam.network.RetrofitService
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Response

class SearchActivity : AppCompatActivity() {
    private val TAG = SearchActivity::class.java.name

    private var retrofitService : RetrofitService? = null

    var repoList : List<SearchRepoInfo>? = null
    var repoAdapter : SearchRepoAdapter? = null
    var searchRoomDB : SearchRoomDB? = null
    var searchRepoDao : SearchRepoDao? = null

    lateinit var svSearch : SearchView
    lateinit var menuSearch : MenuItem
    lateinit var rvSearch : RecyclerView

    private val compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        rvSearch = findViewById(R.id.rv_search)

        retrofitService = NetworkHelper(this).getRetrofitService()
        repoAdapter = SearchRepoAdapter()

        searchRoomDB = SearchRoomDB.getInstance(this)
        searchRepoDao = searchRoomDB!!.getRepoDao()

        rvSearch.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)

        menuSearch = menu.findItem(R.id.menu_search_icon)
        svSearch = menuSearch.actionView as SearchView

        val observable : Observable<SearchRepoData>? = null

        svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                getRepoList(query)

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

        return true
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId) {
            R.id.menu_search_icon ->{
//                getRepoList(svSearch.query as String)
                getRepoListObservable(svSearch.query as String)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun getRepoList(keyword : String) {
        retrofitService!!.getSearchRepo(keyword).enqueue(object : retrofit2.Callback<SearchRepoData>{
            override fun onResponse(
                    call: Call<SearchRepoData>,
                    response: Response<SearchRepoData>
            ) {
                if(response.isSuccessful){
                    val repoData = response.body()

                    repoList = repoData!!.items
                    repoAdapter!!.setData(repoList!!)
//                    repoAdapter!!.setListener(object : ItemClickListener{
//                        override fun onItemCLick(repoInfo: SearchRepoInfo) {
//                            searchRepoDao?.add(repoInfo.toRepoEntity())
//
//                            val intent = Intent(this@SearchActivity, SearchRepoDetailActivity::class.java)
//                            startActivity(intent)
//                        }
//                   })
                    compositeDisposable.add(repoAdapter!!.getOnItemClickObservable().subscribe {item ->
//                        searchRepoDao?.add(item.toRepoEntity())
                        val intent = Intent(this@SearchActivity, SearchRepoDetailActivity::class.java)

                        startActivity(intent)
                    })
                    repoAdapter!!.notifyDataSetChanged()
                    rvSearch.adapter = repoAdapter

                    hideSoftKeyboard()
                }
            }

            override fun onFailure(call: Call<SearchRepoData>, t: Throwable) {
                Log.e(TAG, t.message!!)
            }
        })
    }

    fun getRepoListObservable(keyword : String){
        val disposable = retrofitService!!.getSearchRepoObservable(keyword)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    with(repoAdapter){
                        this!!.setData(response.items)
                        setListener(object : ItemClickListener{
                            override fun onItemCLick(repoInfo: SearchRepoInfo) {
                                searchRepoDao?.add(repoInfo.toRepoEntity())

                                val intent = Intent(this@SearchActivity, SearchRepoDetailActivity::class.java)
                                startActivity(intent)
                            }
                        })
                        notifyDataSetChanged()
                        rvSearch.adapter = this
                    }
                }
        compositeDisposable.add(disposable)
    }

    private fun hideSoftKeyboard() {
        (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).run {
            hideSoftInputFromWindow(svSearch.windowToken, 0)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.clear()
    }
}
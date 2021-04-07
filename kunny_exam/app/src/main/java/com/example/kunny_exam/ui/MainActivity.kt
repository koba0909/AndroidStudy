package com.example.kunny_exam.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kunny_exam.*
import com.example.kunny_exam.data.OwnerData
import com.example.kunny_exam.data.SearchRepoInfo
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.annotations.SerializedName
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    //Widget
    lateinit var floatingActionButton : FloatingActionButton
    lateinit var rvMain : RecyclerView
    lateinit var tvMainEmpty : TextView

    //Room DB
    lateinit var searchRoomDB : SearchRoomDB
    lateinit var searchRepoDao : SearchRepoDao

    var dbRepoList : List<SearchRepoInfo>? = null
    var repoAdapter : SearchRepoAdapter? = null

    private val compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchRoomDB = SearchRoomDB.getInstance(this)!!
        searchRepoDao = searchRoomDB.getRepoDao()

        dbRepoList = ArrayList()
        repoAdapter = SearchRepoAdapter(this)

        floatingActionButton = findViewById(R.id.fab_main)
        rvMain = findViewById(R.id.rv_main)
        tvMainEmpty = findViewById(R.id.tv_main_empty)

        floatingActionButton.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java);
            startActivity(intent)
        }

        val disposable : Disposable
        disposable = searchRepoDao.getAllRepo().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{items ->
                with(repoAdapter){
                    val repoInfos = mutableListOf<SearchRepoInfo>()
                    items.map {
                        repoInfos.add(
                            SearchRepoInfo(
                                name = it.name,
                                full_name = it.fullName,
                                language = it.language,
                                stargazers_count = it.stars,
                                ownerData = it.owner
                            )
                        )
                    }
                    this!!.setData(repoInfos)
                    notifyDataSetChanged()

                    rvMain.adapter = this
                }

                if(items.isEmpty()){
                    tvMainEmpty.visibility = View.VISIBLE
                    rvMain.visibility = View.GONE
                }else{
                    tvMainEmpty.visibility = View.GONE
                    rvMain.visibility = View.VISIBLE
                }
            }
        compositeDisposable.add(disposable)
    }
}
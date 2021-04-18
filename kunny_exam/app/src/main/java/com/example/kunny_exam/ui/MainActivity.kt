package com.example.kunny_exam.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kunny_exam.*
import com.example.kunny_exam.data.OwnerData
import com.example.kunny_exam.data.SearchRepoInfo
import com.example.kunny_exam.databinding.ActivityMainBinding
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

    //Room DB
    private val searchRoomDB by lazy { SearchRoomDB.getInstance(this) }
    private val searchRepoDao by lazy { searchRoomDB.getRepoDao()}
    private val repoAdapter by lazy { SearchRepoAdapter(this) }
    private val binding by lazy { ActivityMainBinding.inflate(LayoutInflater.from(this)) }

    private lateinit var dbRepoList : List<SearchRepoInfo>
    private val compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
        showRoomList()

        setContentView(binding.root)
    }

    private fun init(){
        binding.fabMain.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java);
            startActivity(intent)
        }
        binding.rvMain.adapter = repoAdapter

        dbRepoList = ArrayList()
    }

    private fun showRoomList(){
        val disposable : Disposable = searchRepoDao.getAllRepo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{items ->
                    with(repoAdapter){
                        val repoInfos = mutableListOf<SearchRepoInfo>().also {
                            it.addAll(items.map { entity -> entity.toSearchRepoInfo() })
                        }
                        setData(repoInfos)
                    }

                    if(items.isEmpty()){
                        binding.tvMainEmpty.visibility = View.VISIBLE
                        binding.rvMain.visibility = View.GONE
                    }else{
                        binding.tvMainEmpty.visibility = View.GONE
                        binding.rvMain.visibility = View.VISIBLE
                    }
                }
        compositeDisposable.add(disposable)
    }
}
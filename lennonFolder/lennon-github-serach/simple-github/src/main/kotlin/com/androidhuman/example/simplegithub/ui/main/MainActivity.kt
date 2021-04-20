package com.androidhuman.example.simplegithub.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import com.androidhuman.example.simplegithub.data.provideSearchHistoryDao
import com.androidhuman.example.simplegithub.databinding.ActivityMainBinding
import com.androidhuman.example.simplegithub.ui.search.SearchActivity
import com.androidhuman.example.simplegithub.ui.search.SearchAdapter

// 이 액티비티는 현재 저장소 검색 액티비티를 호출하는 기능만 구현
class MainActivity : AppCompatActivity() {

    private val adapter by lazy {
        SearchAdapter().apply { setItemClickListener(this@MainActivity)}
    }
    private val searchHistoryDao by lazy { provideSearchHistoryDao(this)}

    private lateinit var binding: ActivityMainBinding
    private lateinit var btnSearch: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        btnSearch = binding.btnActivityMainSearch
        btnSearch.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java)) }
    }
}
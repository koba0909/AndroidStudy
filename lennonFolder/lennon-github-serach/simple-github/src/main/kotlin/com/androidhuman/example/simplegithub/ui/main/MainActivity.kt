package com.androidhuman.example.simplegithub.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.androidhuman.example.simplegithub.R
import com.androidhuman.example.simplegithub.ui.search.SearchActivity

// 이 액티비티는 현재 저장소 검색 액티비티를 호출하는 기능만 구현
class MainActivity : AppCompatActivity() {
    internal lateinit var btnSearch: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnSearch = findViewById(R.id.btnActivityMainSearch)
        btnSearch.setOnClickListener(View.OnClickListener { startActivity(Intent(this@MainActivity, SearchActivity::class.java)) })
    }
}
package com.androidhuman.example.simplegithub.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.androidhuman.example.simplegithub.api.model.GithubRepo

@Database(entities = arrayOf(GithubRepo::class), version = 1)
abstract class SimpleGithubDatabase : RoomDatabase() {

    // 데이터베이스와 연결할 dao 정의
    abstract fun searchHistoryDao(): SearchHistoryDao
}
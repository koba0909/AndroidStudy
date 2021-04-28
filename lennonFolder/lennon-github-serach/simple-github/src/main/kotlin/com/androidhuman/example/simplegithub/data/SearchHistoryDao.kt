package com.androidhuman.example.simplegithub.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.androidhuman.example.simplegithub.api.model.GithubRepoDto
import io.reactivex.Flowable

@Dao
interface SearchHistoryDao {

    // 데이터베이스에 저장소 추가
    // 이미 저장된 항목이 있을 경우 데이터를 덮어씀
    // 한마디로 엔티티에 내용물을 채워넣는다고 보면 됨
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(repo: GithubRepoDto)

    // 저장되어 있는 저장소 목록 반환
    // Flowable 형태의 자료를 반환, 데이터베이스가 반경되면 알림을 받아 새로운 자료 가져옴
    // 따라서항상  최신 자료를 유지
    @Query("SELECT * FROM repositories")
    fun getHistory(): Flowable<List<GithubRepoDto>>

    // repositories 테이블의 모든 데이터 삭제
    @Query("DELETE FROM repositories")
    fun clearAll()
}
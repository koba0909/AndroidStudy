package com.study.androidstudy_hoon.data.dao

import androidx.room.*
import com.study.androidstudy_hoon.data.dto.Repo
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface RepoDao {
    @Query("SELECT * FROM Repo")
    fun getAll(): Observable<List<Repo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repo: Repo): Completable

    @Update
    fun update(repo: Repo): Completable

    @Delete
    fun delete(repo: Repo): Completable
}
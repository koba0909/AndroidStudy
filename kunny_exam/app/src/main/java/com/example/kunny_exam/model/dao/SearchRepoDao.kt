package com.example.kunny_exam.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.kunny_exam.model.dto.RepoEntity
import io.reactivex.Observable

@Dao
interface SearchRepoDao {
    @Query("SELECT * FROM repositories")
    fun getAllRepoEntities() : Observable<List<RepoEntity>>

    @Query("SELECT * FROM repositories")
    fun updateRepo() : LiveData<List<RepoEntity>>

    @Insert(onConflict = REPLACE)
    fun add(repo : RepoEntity)

//    @Query("DELETE FROM repositories WHERE full_name")
//    fun deleteRepo(fullName : String)
}
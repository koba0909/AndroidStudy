package com.example.kunny_exam

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.kunny_exam.data.SearchRepoInfo
import io.reactivex.Observable

@Dao
interface SearchRepoDao {
    @Query("SELECT * FROM repositories")
    fun getAllRepo() : Observable<List<SearchRepoInfo>>

    @Insert(onConflict = REPLACE)
    fun add(repo : SearchRepoInfo)

//    @Query("DELETE FROM repositories WHERE full_name")
//    fun deleteRepo(fullName : String)
}
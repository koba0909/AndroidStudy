package com.example.kunny_exam.repository

import android.content.Context
import com.example.kunny_exam.model.dto.RepoEntity
import com.example.kunny_exam.model.database.SearchRoomDB
import io.reactivex.Observable

class RoomRepository(private val context: Context) {
    private val roomDatabase by lazy { SearchRoomDB.getInstance(context) }

    fun getAllRepoEntities() : Observable<List<RepoEntity>>{
        return roomDatabase.getRepoDao().getAllRepoEntities()
    }
}
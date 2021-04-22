package com.study.androidstudy_hoon.data.repository

import android.content.Context
import com.study.androidstudy_hoon.data.dto.Repo
import com.study.androidstudy_hoon.domain.base.RepoDatabase
import io.reactivex.Completable
import io.reactivex.Observable

class RoomRepository(context: Context) {

    private val roomInstance = RepoDatabase.newRoomInstance(context)

    fun insertSearchRepo(repo: Repo): Completable {
        return roomInstance.repoDao().insert(repo)
    }

    fun getAllSearchRepo(): Observable<List<Repo>> {
        return roomInstance.repoDao().getAll()
    }
}
package com.study.androidstudy_hoon.domain.base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.study.androidstudy_hoon.data.dao.RepoDao
import com.study.androidstudy_hoon.data.dto.Repo

@Database(entities = [Repo::class], version = 1)
abstract class RepoDatabase : RoomDatabase() {
    abstract fun repoDao(): RepoDao

    companion object {
        private lateinit var ROOM_INSTANCE: RepoDatabase

        fun newRoomInstance(context: Context): RepoDatabase {
            synchronized(RepoDatabase::class) {
                ROOM_INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        RepoDatabase::class.java,
                        "repo")
                        .build()
            }
            return ROOM_INSTANCE
        }
    }
}
package com.example.kunny_exam.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kunny_exam.common.Constants
import com.example.kunny_exam.model.dao.SearchRepoDao
import com.example.kunny_exam.model.dto.RepoEntity

@Database(entities = [RepoEntity::class], version = 2)
abstract class SearchRoomDB : RoomDatabase() {
    abstract fun getRepoDao() : SearchRepoDao

    companion object{

        private lateinit var INSTANCE : SearchRoomDB

        fun getInstance(context : Context) : SearchRoomDB {
            synchronized(SearchRoomDB::class){
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    SearchRoomDB::class.java,
                    Constants.DB.REPOSITORIES_ROOM_DB_NAME)
                    .build()
            }
            return INSTANCE
        }
    }
}
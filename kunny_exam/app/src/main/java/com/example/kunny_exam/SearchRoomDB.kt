package com.example.kunny_exam

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kunny_exam.common.Constants

@Database(entities = [RepoEntity::class], version = 1)
abstract class SearchRoomDB : RoomDatabase() {
    abstract fun getRepoDao() : SearchRepoDao

    companion object{

        private var INSTANCE : SearchRoomDB? = null

        fun getInstance(context : Context) : SearchRoomDB? {
            if(INSTANCE == null){
                synchronized(SearchRoomDB::class){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        SearchRoomDB::class.java,
                        Constants.DB.REPOSITORIES_ROOM_DB_NAME)
                        .build()
                }
            }
            return INSTANCE
        }
    }
}
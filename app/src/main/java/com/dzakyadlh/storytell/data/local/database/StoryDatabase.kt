package com.dzakyadlh.storytell.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dzakyadlh.storytell.data.local.dao.RemoteKeysDao
import com.dzakyadlh.storytell.data.local.dao.StoryDao
import com.dzakyadlh.storytell.data.response.ListStoryItem

@Database(entities = [ListStoryItem::class, RemoteKeys::class], version = 2, exportSchema = false)
abstract class StoryDatabase : RoomDatabase() {

    abstract fun storyDao(): StoryDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE: StoryDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): StoryDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    StoryDatabase::class.java, "story_database"
                )
                    .fallbackToDestructiveMigration().build().also { INSTANCE = it }
            }
        }
    }
}
package com.dzakyadlh.storytell.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dzakyadlh.storytell.data.local.entity.StoryEntity

@Dao
interface StoryDao {
    @Query("SELECT * FROM story ORDER BY createdAt DESC")
    fun getAllStory(): LiveData<List<StoryEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addStory(story: StoryEntity)

    @Delete
    fun deleteStory(story: StoryEntity)
}
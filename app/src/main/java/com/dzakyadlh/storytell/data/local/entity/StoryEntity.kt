package com.dzakyadlh.storytell.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "story")
class StoryEntity {
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: String = ""

    @ColumnInfo(name = "username")
    var username: String? = null

    @ColumnInfo(name = "desc")
    var desc: String? = null

    @ColumnInfo(name = "photoUrl")
    var photoUrl: String? = null

    @ColumnInfo(name = "createdAt")
    var createdAt: String? = null

    @ColumnInfo(name = "lat")
    var lat: String? = null

    @ColumnInfo(name = "lon")
    var lon: String? = null
}
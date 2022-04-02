package com.dicoding.android.zsgithubuserapp.data.local.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "UserFavorite")
data class UserEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = 0,

    @ColumnInfo(name = "avatar")
    val avatar_url : String? = "",

    @ColumnInfo(name = "username")
    val login: String? = ""
) : Serializable

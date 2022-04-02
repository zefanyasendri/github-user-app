package com.dicoding.android.zsgithubuserapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavorite(user: UserEntity)

    @Query("SELECT * from UserFavorite ORDER BY username ASC")
    fun getUsersFavorite(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM UserFavorite WHERE username = :username")
    suspend fun getDetailUserFavorite(username: String): UserEntity?

    @Query("DELETE FROM UserFavorite WHERE UserFavorite.id = :id")
    fun deleteUserFavorite(id: Int): Int

    @Query("SELECT COUNT(*) FROM UserFavorite WHERE UserFavorite.id = :id")
    suspend fun getUserIsFavorite(id: Int): Int
}
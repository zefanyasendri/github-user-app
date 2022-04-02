package com.dicoding.android.zsgithubuserapp.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dicoding.android.zsgithubuserapp.data.UserRepository
import com.dicoding.android.zsgithubuserapp.data.model.UserDetail

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    private val mUserRepository: UserRepository = UserRepository(application)

    // Detail
    fun getDetail(): LiveData<UserDetail> { return mUserRepository.getDetail() }
    fun setDetail(username: String) = mUserRepository.setDetail(username)

    // Favorite
    fun addFavorite(id: Int, username: String, avatarUrl: String) { mUserRepository.addFavorite(id, username, avatarUrl) }
    suspend fun checkUserFavorite(id: Int) = mUserRepository.checkUserFavorite(id)
    fun deleteFavorite(id: Int) { mUserRepository.deleteFavorite(id) }
}
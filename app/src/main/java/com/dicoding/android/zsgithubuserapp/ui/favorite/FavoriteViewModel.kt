package com.dicoding.android.zsgithubuserapp.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dicoding.android.zsgithubuserapp.data.UserRepository
import com.dicoding.android.zsgithubuserapp.data.local.room.UserEntity

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val mUserRepository: UserRepository = UserRepository(application)
    fun getUserFavorite(): LiveData<List<UserEntity>> = mUserRepository.getUserFavorite()
}
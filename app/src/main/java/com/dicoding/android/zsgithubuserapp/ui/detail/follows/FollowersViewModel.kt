package com.dicoding.android.zsgithubuserapp.ui.detail.follows

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.dicoding.android.zsgithubuserapp.data.UserRepository

class FollowersViewModel(application: Application) : AndroidViewModel(application) {
    private val mUserRepository = UserRepository(application)
    fun getListFollowers() = mUserRepository.getListFollowers()
    fun setListFollowers(username: String) = mUserRepository.setListFollowers(username)
}
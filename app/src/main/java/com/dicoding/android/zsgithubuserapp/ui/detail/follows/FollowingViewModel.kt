package com.dicoding.android.zsgithubuserapp.ui.detail.follows

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.dicoding.android.zsgithubuserapp.data.UserRepository

class FollowingViewModel(application: Application) : AndroidViewModel(application) {
    private val mUserRepository = UserRepository(application)
    fun getListFollowing() = mUserRepository.getListFollowing()
    fun setListFollowing(username: String) = mUserRepository.setListFollowing(username)
}
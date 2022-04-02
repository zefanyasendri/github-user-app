package com.dicoding.android.zsgithubuserapp.data

import android.app.Application
import android.content.ContentValues
import android.os.Build
import android.service.controls.ControlsProviderService
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.android.zsgithubuserapp.data.local.room.UserDao
import com.dicoding.android.zsgithubuserapp.data.local.room.UserDatabase
import com.dicoding.android.zsgithubuserapp.data.local.room.UserEntity
import com.dicoding.android.zsgithubuserapp.data.model.User
import com.dicoding.android.zsgithubuserapp.data.model.UserDetail
import com.dicoding.android.zsgithubuserapp.data.remote.ApiConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(application: Application) {
    private val mUserDao: UserDao
    private val following = MutableLiveData<List<User>>()
    private val followers = MutableLiveData<List<User>>()
    private val detailUser = MutableLiveData<UserDetail>()

    init {
        val db = UserDatabase.getDatabase(application)
        mUserDao = db.userDao()
    }

    // Favorite
    fun addFavorite(id: Int, username: String, avatarUrl: String){
        CoroutineScope(Dispatchers.IO).launch {
            val user = UserEntity(id, username, avatarUrl)
            mUserDao.insertFavorite(user)
        }
    }
    fun getUserFavorite(): LiveData<List<UserEntity>> { return mUserDao.getUsersFavorite() }
    suspend fun checkUserFavorite(id: Int) = mUserDao.getUserIsFavorite(id)
    fun deleteFavorite(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            mUserDao.deleteUserFavorite(id)
        }
    }

    // Followers
    fun getListFollowers(): LiveData<List<User>> { return followers }
    fun setListFollowers(username: String) {
        val client = ApiConfig.getApiService().getFollowersUser(username)
        client.enqueue(object : Callback<ArrayList<User>> {
            @RequiresApi(Build.VERSION_CODES.R)
            override fun onResponse(call: Call<ArrayList<User>>, response: Response<ArrayList<User>>) {
                if (response.isSuccessful) {
                    followers.postValue(response.body())
                } else {
                    Log.e(ControlsProviderService.TAG, "onFailure: ${response.message()}")
                }
            }
            @RequiresApi(Build.VERSION_CODES.R)
            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Log.e(ControlsProviderService.TAG, "Failure : ${t.message}")
            }
        })
    }

    // Following
    fun getListFollowing(): LiveData<List<User>> { return following }
    fun setListFollowing(username: String) {
        val client = ApiConfig.getApiService().getFollowingUser(username)
        client.enqueue(object : Callback<ArrayList<User>> {
            @RequiresApi(Build.VERSION_CODES.R)
            override fun onResponse(call: Call<ArrayList<User>>, response: Response<ArrayList<User>>) {
                if (response.isSuccessful) {
                    following.postValue(response.body())
                } else {
                    Log.e(ControlsProviderService.TAG, "onFailure: ${response.message()}")
                }
            }
            @RequiresApi(Build.VERSION_CODES.R)
            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Log.e(ControlsProviderService.TAG, "Failure : ${t.message}")
            }
        })
    }

    // Detail User
    fun getDetail(): LiveData<UserDetail> { return detailUser }
    fun setDetail(username: String) {
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<UserDetail> {
            override fun onResponse(call: Call<UserDetail>, response: Response<UserDetail>) {
                if (response.isSuccessful) {
                    detailUser.postValue(response.body())
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }
            @RequiresApi(Build.VERSION_CODES.R)
            override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                Log.e(ControlsProviderService.TAG, "Failure: ${t.message}")
            }
        })
    }
}
package com.dicoding.android.zsgithubuserapp.ui.main

import android.content.ContentValues
import android.os.Build
import android.service.controls.ControlsProviderService
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.dicoding.android.zsgithubuserapp.data.remote.ApiConfig
import com.dicoding.android.zsgithubuserapp.data.local.datastore.SettingPreferences
import com.dicoding.android.zsgithubuserapp.data.model.User
import com.dicoding.android.zsgithubuserapp.data.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.IllegalArgumentException

class MainViewModel(private val preferences: SettingPreferences) : ViewModel() {
    val listUser = MutableLiveData<List<User>>()

    // Theme User
    fun getTheme() = preferences.getThemeSetting().asLiveData()

    // User
    fun getListUser(): LiveData<List<User>> { return listUser }
    fun setLisUser(q: String) {
        val client = ApiConfig.getApiService().getUsers(q)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    listUser.postValue(response.body()?.items)
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }
            @RequiresApi(Build.VERSION_CODES.R)
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e(ControlsProviderService.TAG, "Failure: ${t.message}")
            }
        })
    }

    class Factory(private val pref: SettingPreferences):ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(MainViewModel::class.java)) return MainViewModel(pref) as T
            throw IllegalArgumentException("Unknown ViewModel")
        }
    }
}
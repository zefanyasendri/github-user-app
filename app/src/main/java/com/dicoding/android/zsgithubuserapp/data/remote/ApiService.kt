package com.dicoding.android.zsgithubuserapp.data.remote

import com.dicoding.android.zsgithubuserapp.BuildConfig
import com.dicoding.android.zsgithubuserapp.data.model.User
import com.dicoding.android.zsgithubuserapp.data.model.UserDetail
import com.dicoding.android.zsgithubuserapp.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    // Get Users
    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.KEY}")
    fun getUsers(
        @Query("q") keyword: String
    ): Call<UserResponse>

    // Get User by Username
    @GET("users/{username}")
    @Headers("Authorization: token ${BuildConfig.KEY}")
    fun getDetailUser (
        @Path("username") username: String
    ): Call<UserDetail>

    // Get User Following
    @GET("users/{username}/following")
    @Headers("Authorization: token ${BuildConfig.KEY}")
    fun getFollowingUser (
        @Path("username") username: String
    ): Call<ArrayList<User>>

    // Get User Followers
    @GET("users/{username}/followers")
    @Headers("Authorization: token ${BuildConfig.KEY}")
    fun getFollowersUser (
        @Path("username") username: String
    ): Call<ArrayList<User>>
}
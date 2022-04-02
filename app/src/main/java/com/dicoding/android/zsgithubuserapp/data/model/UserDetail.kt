package com.dicoding.android.zsgithubuserapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDetail (
    val id: Int? = 0,
    val avatar_url: String? = "",
    val login: String? = "",
    val name: String? = "",
    val location: String? = "",
    val company: String? = "",
    val public_repos: String? = "",
    val followers: String? = "",
    val following: String? = "",
    val isFavorite: Boolean = false
) : Parcelable
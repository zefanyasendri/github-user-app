package com.dicoding.android.zsgithubuserapp.util

import androidx.annotation.StringRes
import com.dicoding.android.zsgithubuserapp.R

object AppConstants {
    const val EXTRA_ID = "EXTRA_ID"
    const val EXTRA_AVATAR = "EXTRA_AVATAR"
    const val EXTRA_USER = "EXTRA_USER"

    @StringRes
    val TAB = intArrayOf(
        R.string.user_followers,
        R.string.user_following
    )
}
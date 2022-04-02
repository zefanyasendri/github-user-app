package com.dicoding.android.zsgithubuserapp.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.android.zsgithubuserapp.data.local.datastore.SettingPreferences
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class ChangeThemeViewModel(private val pref: SettingPreferences) : ViewModel() {
    fun getTheme() = pref.getThemeSetting().asLiveData()
    fun saveTheme(isDark : Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDark)
        }
    }

    class Factory(private val pref: SettingPreferences):ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(ChangeThemeViewModel::class.java)) return ChangeThemeViewModel(pref) as T
            throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}
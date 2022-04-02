package com.dicoding.android.zsgithubuserapp.ui.main

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.android.zsgithubuserapp.ui.theme.ChangeThemeActivity
import com.dicoding.android.zsgithubuserapp.R
import com.dicoding.android.zsgithubuserapp.data.local.datastore.SettingPreferences
import com.dicoding.android.zsgithubuserapp.data.model.User
import com.dicoding.android.zsgithubuserapp.databinding.ActivityMainBinding
import com.dicoding.android.zsgithubuserapp.ui.detail.DetailActivity
import com.dicoding.android.zsgithubuserapp.ui.favorite.FavoriteActivity
import com.dicoding.android.zsgithubuserapp.util.AppConstants

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ListUserAdapter
    private val viewModel by viewModels<MainViewModel> {
        MainViewModel.Factory(SettingPreferences(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "ZS GitHub User"

        // List User
        adapter = ListUserAdapter()
        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun itemClicked(userData: User) {
                Intent(this@MainActivity, DetailActivity::class.java).also {
                    it.putExtra(AppConstants.EXTRA_USER, userData.login)
                    it.putExtra(AppConstants.EXTRA_ID, userData.id)
                    it.putExtra(AppConstants.EXTRA_AVATAR, userData.avatar_url)
                    startActivity(it)
                }
            }
        })

        // Change Theme
        viewModel.getTheme().observe(this) { isDarkMode : Boolean ->
            if(!isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#212121")))
                supportActionBar?.title = Html.fromHtml("<font color=\"#ffea00\">" + supportActionBar?.title + "</font>")
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }

        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.adapter = adapter
            edSearch.setOnKeyListener { _, key, event ->
                if (event.action == KeyEvent.ACTION_DOWN && key == KeyEvent.KEYCODE_ENTER) {
                    searchUser()
                    return@setOnKeyListener true
                } else {
                    return@setOnKeyListener false
                }
            }
        }

        viewModel.getListUser().observe(this, {
            if(it != null) {
                adapter.setAllData(it)
                onLoading(false)
            }
        })
    }

    private fun searchUser(){
        binding.apply {
            val query = edSearch.text.toString()
            if (query.isEmpty()) return
            viewModel.setLisUser(query)
            onLoading(true)
        }
    }

    private fun onLoading(data: Boolean) {
        if (data) {
            binding.progressBar.visibility = View.VISIBLE
            binding.searchMessage.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
            binding.searchMessage.visibility = View.INVISIBLE
        }
    }

    // Option Menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_option, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_theme -> {
                Intent(this@MainActivity, ChangeThemeActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.menu_favorite -> {
                Intent(this@MainActivity, FavoriteActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
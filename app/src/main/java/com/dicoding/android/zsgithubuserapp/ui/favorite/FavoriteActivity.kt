package com.dicoding.android.zsgithubuserapp.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.android.zsgithubuserapp.R
import com.dicoding.android.zsgithubuserapp.ui.main.ListUserAdapter
import com.dicoding.android.zsgithubuserapp.data.local.room.UserEntity
import com.dicoding.android.zsgithubuserapp.data.model.User
import com.dicoding.android.zsgithubuserapp.databinding.ActivityFavoriteBinding
import com.dicoding.android.zsgithubuserapp.ui.detail.DetailActivity
import com.dicoding.android.zsgithubuserapp.ui.theme.ChangeThemeActivity
import com.dicoding.android.zsgithubuserapp.util.AppConstants

class FavoriteActivity : AppCompatActivity() {
    private lateinit var adapter: ListUserAdapter
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Action Bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Favorite User"
        supportActionBar?.title = Html.fromHtml("<font color=\"#ffea00\">" + supportActionBar?.title + "</font>")

        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun itemClicked(userData: User) {
                Intent(this@FavoriteActivity, DetailActivity::class.java).also {
                    it.putExtra(AppConstants.EXTRA_USER, userData.login)
                    it.putExtra(AppConstants.EXTRA_ID, userData.id)
                    it.putExtra(AppConstants.EXTRA_AVATAR, userData.avatar_url)
                    startActivity(it)
                }
            }
        })

        binding.apply {
            rvFav.adapter = adapter
            rvFav.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFav.setHasFixedSize(true)
        }

        viewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]
        viewModel.getUserFavorite().observe(this, {
            if(it!=null){
                val favUser = listFav(it)
                adapter.setAllData(favUser)
                onLoading(true)
            }
            onLoading(false)
        })

        super.onCreate(savedInstanceState)
    }

    private fun listFav(users: List<UserEntity>): ArrayList<User> {
        val listFavUser = ArrayList<User>()
        for (user in users) {
            listFavUser.add(User(user.id, user.login, user.avatar_url))
        }
        return listFavUser
    }

    // Option Menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_option, menu)
        val item = menu.findItem(R.id.menu_favorite)
        item.isVisible = false
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
            R.id.menu_theme -> {
                Intent(this@FavoriteActivity, ChangeThemeActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onLoading(q: Boolean) {
        binding.favProgressBar.visibility = if (q) View.VISIBLE else View.GONE
    }
}
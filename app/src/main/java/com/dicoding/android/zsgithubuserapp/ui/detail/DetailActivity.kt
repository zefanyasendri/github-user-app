package com.dicoding.android.zsgithubuserapp.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.android.zsgithubuserapp.R
import com.dicoding.android.zsgithubuserapp.databinding.ActivityDetailBinding
import com.dicoding.android.zsgithubuserapp.ui.detail.follows.FollowAdapter
import com.dicoding.android.zsgithubuserapp.util.AppConstants.TAB
import com.dicoding.android.zsgithubuserapp.ui.theme.ChangeThemeActivity
import com.dicoding.android.zsgithubuserapp.util.AppConstants
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra(AppConstants.EXTRA_ID, 0)
        val avatar = intent.getStringExtra(AppConstants.EXTRA_AVATAR)
        val username = intent.getStringExtra(AppConstants.EXTRA_USER)

        // Action Bar
        supportActionBar?.title = username
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = Html.fromHtml("<font color=\"#ffea00\">" + supportActionBar?.title + "</font>")

        val bundle = Bundle()
        bundle.putString(AppConstants.EXTRA_USER, username)

        // FollowAdapter
        val followAdapter = FollowAdapter(this, bundle)
        binding.apply {
            viewPager.adapter = followAdapter
            TabLayoutMediator(tabs, viewPager) {
                    tabs, position -> tabs.text = resources.getString(TAB[position])
            }.attach()
        }

        // ViewModel
        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        if (username != null) {
            onLoading(true)
            viewModel.setDetail(username)
        }
        viewModel.getDetail().observe(this, {
            if(it != null) {
                binding.apply {
                    Glide.with(this@DetailActivity)
                        .load(it.avatar_url)
                        .apply(RequestOptions.circleCropTransform())
                        .circleCrop()
                        .into(tvAvatar)
                    tvName.text = it.name
                    tvNumRepo.text = it.public_repos
                    tvNumFollowers.text = it.followers
                    tvNumFollowing.text = it.following
                    tvCompany.text = it.company
                    tvLocation.text = it.location
                }
                onLoading(false)
            }
        })

        // Check User Favorite
        var check = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUserFavorite(id)
            withContext(Dispatchers.Main) {
                if(count != null) {
                    if (count > 0) {
                        check = true
                        binding.tbFavorite.isChecked = true
                        binding.fabFavorite.setImageResource(R.drawable.ic_bookmark)
                    } else {
                        check = false
                        binding.tbFavorite.isChecked = false
                        binding.fabFavorite.setImageResource(R.drawable.ic_bookmark_border)
                    }
                }
            }
        }

        // Button Favorite
        binding.fabFavorite.setOnClickListener {
            check = !check
            if (check) {
                if(username != null && avatar != null) {
                    viewModel.addFavorite(id,username,avatar)
                    binding.fabFavorite.setImageResource(R.drawable.ic_bookmark)
                    showToast(getString(R.string.added))
                }
            } else {
                viewModel.deleteFavorite(id)
                binding.fabFavorite.setImageResource(R.drawable.ic_bookmark_border)
                showToast(getString(R.string.deleted))
            }
            binding.tbFavorite.isChecked = check
        }

        super.onCreate(savedInstanceState)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun onLoading(q: Boolean) {
        if (q) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
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
                Intent(this@DetailActivity, ChangeThemeActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
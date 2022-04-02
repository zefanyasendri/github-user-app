package com.dicoding.android.zsgithubuserapp.ui.theme

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.dicoding.android.zsgithubuserapp.data.local.datastore.SettingPreferences
import com.dicoding.android.zsgithubuserapp.databinding.ActivityChangeThemeBinding

class ChangeThemeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangeThemeBinding
    private val viewModel by viewModels<ChangeThemeViewModel> {
        ChangeThemeViewModel.Factory(SettingPreferences(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeThemeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Change Theme"
        supportActionBar?.title = Html.fromHtml("<font color=\"#ffea00\">" + supportActionBar?.title + "</font>")

        viewModel.getTheme().observe(this) { isDarkMode: Boolean ->
            if (!isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#212121")))
                supportActionBar?.title = Html.fromHtml("<font color=\"#ffea00\">" + supportActionBar?.title + "</font>")
                binding.switchTheme.isChecked = false
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked = true
            }
            binding.switchTheme.isChecked = isDarkMode
        }

        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            viewModel.saveTheme(isChecked)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
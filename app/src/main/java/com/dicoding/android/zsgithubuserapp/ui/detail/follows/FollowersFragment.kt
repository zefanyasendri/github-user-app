package com.dicoding.android.zsgithubuserapp.ui.detail.follows

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.android.zsgithubuserapp.R
import com.dicoding.android.zsgithubuserapp.data.model.User
import com.dicoding.android.zsgithubuserapp.databinding.FragmentFollowersFollowingBinding
import com.dicoding.android.zsgithubuserapp.ui.detail.DetailActivity
import com.dicoding.android.zsgithubuserapp.ui.main.ListUserAdapter
import com.dicoding.android.zsgithubuserapp.util.AppConstants

class FollowersFragment : Fragment(R.layout.fragment_followers_following) {
    private lateinit var username: String
    private var bindingFollowers: FragmentFollowersFollowingBinding? = null
    private val binding get() = bindingFollowers!!
    private lateinit var adapter: ListUserAdapter
    private lateinit var viewModel: FollowersViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val arguments = arguments
        username = arguments?.getString(AppConstants.EXTRA_USER).toString()

        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun itemClicked(userData: User) {
                val intent = Intent(activity, DetailActivity::class.java).also {
                    it.putExtra(AppConstants.EXTRA_USER, userData.login)
                    it.putExtra(AppConstants.EXTRA_ID, userData.id)
                    it.putExtra(AppConstants.EXTRA_AVATAR, userData.avatar_url)
                }
                activity?.startActivity(intent)
            }
        })

        bindingFollowers = FragmentFollowersFollowingBinding.bind(view)
        binding.apply {
            listUserFollows.adapter = adapter
            listUserFollows.setHasFixedSize(true)
            listUserFollows.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        onLoading(true)
        viewModel = ViewModelProvider(this)[FollowersViewModel::class.java]
        viewModel.getListFollowers().observe(viewLifecycleOwner, {
            if(it != null) {
                adapter.setAllData(it)
                onLoading(false)
            }
        })
        viewModel.setListFollowers(username)

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        bindingFollowers = null
        super.onDestroyView()
    }

    private fun onLoading(q: Boolean) {
        if (q) {
            binding.followsProgressBar.visibility = View.VISIBLE
            binding.tvMsgFollows.visibility = View.VISIBLE
        } else {
            binding.followsProgressBar.visibility = View.INVISIBLE
            binding.tvMsgFollows.visibility = View.INVISIBLE
        }
    }
}
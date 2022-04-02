package com.dicoding.android.zsgithubuserapp.ui.detail.follows

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.android.zsgithubuserapp.R
import com.dicoding.android.zsgithubuserapp.data.model.User
import com.dicoding.android.zsgithubuserapp.databinding.FragmentFollowersFollowingBinding
import com.dicoding.android.zsgithubuserapp.ui.detail.DetailActivity
import com.dicoding.android.zsgithubuserapp.ui.main.ListUserAdapter
import com.dicoding.android.zsgithubuserapp.util.AppConstants

class FollowingFragment : Fragment(R.layout.fragment_followers_following) {
    private lateinit var username: String
    private var bindingFollowing: FragmentFollowersFollowingBinding? = null
    private val binding get() = bindingFollowing!!
    private lateinit var adapter: ListUserAdapter
    private lateinit var viewModel: FollowingViewModel

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

        bindingFollowing = FragmentFollowersFollowingBinding.bind(view)
        binding.apply {
            listUserFollows.adapter = adapter
            listUserFollows.setHasFixedSize(true)
            listUserFollows.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        onLoading(true)
        viewModel = ViewModelProvider(this)[FollowingViewModel::class.java]
        viewModel.getListFollowing().observe(viewLifecycleOwner, {
            if(it != null) {
                adapter.setAllData(it)
                onLoading(false)
            }
        })
        viewModel.setListFollowing(username)

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        bindingFollowing = null
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
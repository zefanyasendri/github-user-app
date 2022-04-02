package com.dicoding.android.zsgithubuserapp.ui.detail.follows

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.android.zsgithubuserapp.util.AppConstants.TAB

class FollowAdapter(activity: AppCompatActivity, data: Bundle) : FragmentStateAdapter(activity) {
    private var fBundle: Bundle = data

    override fun getItemCount(): Int { return TAB.size }
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        fragment?.arguments = this.fBundle
        return fragment as Fragment
    }
}
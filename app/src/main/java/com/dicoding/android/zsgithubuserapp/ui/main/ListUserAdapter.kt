package com.dicoding.android.zsgithubuserapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.android.zsgithubuserapp.data.model.User
import com.dicoding.android.zsgithubuserapp.databinding.ItemRowUserBinding

class ListUserAdapter: RecyclerView.Adapter<ListUserAdapter.UserViewHolder>() {
    private var itemClickCallback: OnItemClickCallback? = null
    private val listUser = ArrayList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

    inner class UserViewHolder(private val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.root.setOnClickListener {
                itemClickCallback?.itemClicked(user)
            }
            binding.apply {
                Glide.with(itemView.context)
                    .load(user.avatar_url)
                    .apply(RequestOptions())
                    .circleCrop()
                    .into(imgItemPhoto)
                tvItemUsername.text = user.login
            }
        }
    }

    fun setAllData(data: List<User>) {
        listUser.clear()
        listUser.addAll(data)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(ItemClickCallback: OnItemClickCallback) {
        this.itemClickCallback = ItemClickCallback
    }

    interface OnItemClickCallback {
        fun itemClicked(userData: User)
    }
}
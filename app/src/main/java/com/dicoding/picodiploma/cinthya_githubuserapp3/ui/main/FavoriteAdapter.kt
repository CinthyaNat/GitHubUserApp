package com.dicoding.picodiploma.cinthya_githubuserapp3.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.cinthya_githubuserapp3.database.UserFavorite
import com.dicoding.picodiploma.cinthya_githubuserapp3.databinding.ItemRowUserBinding
import com.dicoding.picodiploma.cinthya_githubuserapp3.helper.FavoriteDiffCallback

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.UserFavoriteViewHolder>()  {
    private val listFavorite = ArrayList<UserFavorite>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: String)
    }

    fun setListFavorite(listFavorite: List<UserFavorite>) {
        val diffCallback = FavoriteDiffCallback(this.listFavorite, listFavorite)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorite.clear()
        this.listFavorite.addAll(listFavorite)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserFavoriteViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserFavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserFavoriteViewHolder, position: Int) {
        holder.bind(listFavorite[position])
    }
    override fun getItemCount(): Int {
        return listFavorite.size
    }
    inner class UserFavoriteViewHolder(private val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(userFavorite: UserFavorite) {
            with(binding) {
                tvItemUsername.text = userFavorite.username
                Glide.with(itemAvatar.context)
                    .load(userFavorite.avatar_url)
                    .into(itemAvatar)
                itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(listFavorite[adapterPosition].username.toString())}
            }
        }
    }


}
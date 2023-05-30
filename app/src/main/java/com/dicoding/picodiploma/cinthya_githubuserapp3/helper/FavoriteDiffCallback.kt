package com.dicoding.picodiploma.cinthya_githubuserapp3.helper

import androidx.recyclerview.widget.DiffUtil
import com.dicoding.picodiploma.cinthya_githubuserapp3.database.UserFavorite

class FavoriteDiffCallback(private val mOldFavoriteList: List<UserFavorite>, private val mNewFavoriteList: List<UserFavorite>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldFavoriteList.size
    }
    override fun getNewListSize(): Int {
        return mNewFavoriteList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFavoriteList[oldItemPosition].id == mNewFavoriteList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldFavoriteList[oldItemPosition]
        val newEmployee = mNewFavoriteList[newItemPosition]
        return oldEmployee.username == newEmployee.username && oldEmployee.avatar_url == newEmployee.avatar_url
    }

}
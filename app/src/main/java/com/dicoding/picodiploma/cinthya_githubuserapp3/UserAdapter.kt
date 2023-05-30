package com.dicoding.picodiploma.cinthya_githubuserapp3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class UserAdapter(private val listUser: List<ItemsItem>) : RecyclerView.Adapter<UserAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_user, parent, false)
        return ListViewHolder(view)
    }
    override fun onBindViewHolder(viewHolder: ListViewHolder, position: Int) {
        val currentUser: ItemsItem = listUser[position]
        viewHolder.tvItem.text = currentUser.login
        Glide.with(viewHolder.imgAvatar.context)
            .load(currentUser.avatarUrl)
            .into(viewHolder.imgAvatar)

        viewHolder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listUser[viewHolder.adapterPosition].login) }
    }

    override fun getItemCount() = listUser.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvItem: TextView = itemView.findViewById(R.id.tv_item_username)
        var imgAvatar: ImageView = itemView.findViewById(R.id.item_avatar)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: String)
    }

}
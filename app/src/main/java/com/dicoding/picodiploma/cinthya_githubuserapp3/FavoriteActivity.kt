package com.dicoding.picodiploma.cinthya_githubuserapp3

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.cinthya_githubuserapp3.databinding.ActivityFavoriteBinding
import com.dicoding.picodiploma.cinthya_githubuserapp3.ui.main.DetailActivity
import com.dicoding.picodiploma.cinthya_githubuserapp3.ui.main.FavoriteAdapter
import com.dicoding.picodiploma.cinthya_githubuserapp3.ui.main.MainViewModel

class FavoriteActivity : AppCompatActivity() {


    private var _activityFavoriteBinding: ActivityFavoriteBinding? = null
    private val binding get() = _activityFavoriteBinding
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityFavoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val mainViewModel = obtainViewModel(this@FavoriteActivity)
        mainViewModel.getAllFavorite().observe(this, { favoriteList ->
            if (favoriteList  != null) {
                adapter.setListFavorite(favoriteList)
            }
        })

        adapter = FavoriteAdapter()

        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding?.recyclerUser?.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding?.recyclerUser?.layoutManager = LinearLayoutManager(this)
        }
        binding?.recyclerUser?.setHasFixedSize(true)
        binding?.recyclerUser?.adapter = adapter

        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: String) {
                val intentToDetail = Intent(this@FavoriteActivity, DetailActivity::class.java)
                intentToDetail.putExtra("DATA", data)
                startActivity(intentToDetail)
            }
        })
    }

    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(MainViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_withsetting, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu2 -> {
                val i = Intent(this, SettingActivity::class.java)
                startActivity(i)
                return true
            }
            else -> return true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityFavoriteBinding = null
    }
}
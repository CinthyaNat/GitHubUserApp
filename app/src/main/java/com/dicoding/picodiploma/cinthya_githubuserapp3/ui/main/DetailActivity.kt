package com.dicoding.picodiploma.cinthya_githubuserapp3.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.cinthya_githubuserapp3.*
import com.dicoding.picodiploma.cinthya_githubuserapp3.database.UserFavorite
import com.dicoding.picodiploma.cinthya_githubuserapp3.databinding.ActivityDetailBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mainViewModel = obtainViewModel(this@DetailActivity)
        var favorited = false

        val data = intent.getStringExtra("DATA")
        mainViewModel.getUser(data.toString())

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        sectionsPagerAdapter.username = data.toString()
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        mainViewModel.currentUser.observe(this, { currentUser ->
            setData(currentUser)
        })

        mainViewModel.isLoadingDetail.observe(this, {
            showLoading(it)
        })

        mainViewModel.checkIsFavorite(data.toString()).observe(this, { is_favorite ->
            favorited = is_favorite
            if(is_favorite){
                binding.fabLove.setImageDrawable(ContextCompat.getDrawable(binding.fabLove.context,
                    R.drawable.ic_favourite))
            }else{
                binding.fabLove.setImageDrawable(ContextCompat.getDrawable(binding.fabLove.context,
                    R.drawable.ic_favorite_borde))
            }
        })

        binding.fabLove.setOnClickListener{view ->
            if (view.id == R.id.fab_love) {
                if(favorited){
                    val certainUser = UserFavorite()
                    certainUser.username = data
                    certainUser.avatar_url = mainViewModel.currentUser.value?.avatarUrl

                    mainViewModel.deleteUser(data.toString())
                    showToast("Favorit untuk user ini berhasil dihapus")
                }else{
                    val certainUser = UserFavorite()
                    certainUser.username = data
                    certainUser.avatar_url = mainViewModel.currentUser.value?.avatarUrl

                    mainViewModel.insert(certainUser)
                    showToast("User berhasil difavoritkan")

                }
            }
        }
        mainViewModel.snackbarMainDetail.observe(this, {
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    window.decorView.rootView,
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        })
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


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
    private fun setData(User: ItemsItem) {
        binding.showCompany.text = User.company.toString()
        binding.showUsername.text = User.login
        binding.showName.text = User.name.toString()
        binding.showFollowers.text = getString(R.string.follower_count, User.followers)
        binding.showFollowing.text = getString(R.string.following_count, User.following)
        binding.showLocation.text = User.location.toString()
        binding.showRepo.text = User.publicRepos.toString()

        Glide.with(this@DetailActivity)
            .load(User.avatarUrl)
            .into(binding.showAvatar)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[MainViewModel::class.java]
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

}
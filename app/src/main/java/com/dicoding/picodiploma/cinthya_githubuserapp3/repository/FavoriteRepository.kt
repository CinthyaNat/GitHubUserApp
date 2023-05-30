package com.dicoding.picodiploma.cinthya_githubuserapp3.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.picodiploma.cinthya_githubuserapp3.database.FavoriteDao
import com.dicoding.picodiploma.cinthya_githubuserapp3.database.FavoriteRoomDatabase
import com.dicoding.picodiploma.cinthya_githubuserapp3.database.UserFavorite
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDao()
    }

    fun getAllFavorite(): LiveData<List<UserFavorite>> = mFavoriteDao.getAllFavorite()

    fun checkIsFavorite(username: String): LiveData<Boolean> = mFavoriteDao.checkIsFavorite(username)

    fun deleteByName(username: String){
        executorService.execute { mFavoriteDao.deleteByName(username)}
    }

    fun insert(userFavorite: UserFavorite) {
        executorService.execute { mFavoriteDao.insert(userFavorite) }
    }

}
package com.dicoding.picodiploma.cinthya_githubuserapp3.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(userFavorite: UserFavorite)

    @Delete
    fun delete(userFavorite: UserFavorite)

    @Query("DELETE FROM userFavorite WHERE username = :username")
    fun deleteByName(username: String)

    @Query("SELECT * from userFavorite ORDER BY id ASC")
    fun getAllFavorite(): LiveData<List<UserFavorite>>

    @Query("SELECT EXISTS(SELECT * from userFavorite WHERE username = :username)")
    fun checkIsFavorite(username: String): LiveData<Boolean>
}
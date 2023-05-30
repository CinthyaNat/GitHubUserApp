package com.dicoding.picodiploma.cinthya_githubuserapp3.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.cinthya_githubuserapp3.ApiConfig
import com.dicoding.picodiploma.cinthya_githubuserapp3.Event
import com.dicoding.picodiploma.cinthya_githubuserapp3.ItemsItem
import com.dicoding.picodiploma.cinthya_githubuserapp3.UserResponse
import com.dicoding.picodiploma.cinthya_githubuserapp3.database.UserFavorite
import com.dicoding.picodiploma.cinthya_githubuserapp3.repository.FavoriteRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application) : ViewModel()  {
    private val _listUser = MutableLiveData<List<ItemsItem>>()
    val listUser: LiveData<List<ItemsItem>> = _listUser

    private val _currentUser = MutableLiveData<ItemsItem>()
    val currentUser: LiveData<ItemsItem> = _currentUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isLoadingDetail = MutableLiveData<Boolean>()
    val isLoadingDetail: LiveData<Boolean> = _isLoadingDetail

    private val _snackbarMainError = MutableLiveData<Event<String>>()
    val snackbarMainError: LiveData<Event<String>> = _snackbarMainError

    private val _snackbarMainDetail = MutableLiveData<Event<String>>()
    val snackbarMainDetail: LiveData<Event<String>> = _snackbarMainDetail

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun getAllFavorite(): LiveData<List<UserFavorite>> = mFavoriteRepository.getAllFavorite()

    fun checkIsFavorite(username: String): LiveData<Boolean> = mFavoriteRepository.checkIsFavorite(username)

    fun insert(userFavorite: UserFavorite) {
        mFavoriteRepository.insert(userFavorite)
    }

    fun deleteUser(username: String){
        mFavoriteRepository.deleteByName(username)
    }

    fun searchUser(username : String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListUsers(username)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listUser.value = responseBody.items
                    }
                } else {
                    _snackbarMainError.value = Event(response.message().toString())
                }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                _snackbarMainError.value = Event(t.message.toString())
            }
        })
    }

    fun getUser(username : String) {
        _isLoadingDetail.value = true
        val client = ApiConfig.getApiService().getUserDetail(username)
        client.enqueue(object : Callback<ItemsItem> {
            override fun onResponse(
                call: Call<ItemsItem>,
                response: Response<ItemsItem>
            ) {
                _isLoadingDetail.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _currentUser.value = response.body()
                    }
                } else {
                    _snackbarMainDetail.value = Event(response.message().toString())
                }
            }
            override fun onFailure(call: Call<ItemsItem>, t: Throwable) {
                _isLoadingDetail.value = false
                _snackbarMainDetail.value = Event(t.message.toString())
            }
        })
    }

}
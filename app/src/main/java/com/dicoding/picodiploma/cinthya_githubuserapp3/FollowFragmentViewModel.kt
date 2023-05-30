package com.dicoding.picodiploma.cinthya_githubuserapp3

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowFragmentViewModel : ViewModel()   {

    private val _listFollower = MutableLiveData<List<ItemsItem>>()
    val listFollower: LiveData<List<ItemsItem>> = _listFollower

    private val _listFollowing = MutableLiveData<List<ItemsItem>>()
    val listFollowing : LiveData<List<ItemsItem>> = _listFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isLoadingFollowing = MutableLiveData<Boolean>()
    val isLoadingFollowing : LiveData<Boolean> = _isLoadingFollowing

    private val _snackbarFollowerError = MutableLiveData<Event<String>>()
    val snackbarFollowerError: LiveData<Event<String>> = _snackbarFollowerError

    private val _snackbarFollowingError = MutableLiveData<Event<String>>()
    val snackbarFollowingError: LiveData<Event<String>> = _snackbarFollowingError

    fun getFollower(username : String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowers(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listFollower.value = response.body()
                    }
                } else {
                    _snackbarFollowerError.value = Event(response.message().toString())
                }
            }
            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                _snackbarFollowerError.value = Event(t.message.toString())
            }
        })
    }

    fun getFollowing(username : String) {
        _isLoadingFollowing.value = true
        val client = ApiConfig.getApiService().getUserFollowing(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoadingFollowing.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listFollowing.value = response.body()
                    }
                } else {
                    _snackbarFollowingError.value = Event(response.message().toString())
                }
            }
            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoadingFollowing.value = false
                _snackbarFollowingError.value = Event(t.message.toString())
            }
        })
    }
}
package com.dicoding.picodiploma.cinthya_githubuserapp3

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getListUsers(
        @Query("q") username: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<ItemsItem>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

}
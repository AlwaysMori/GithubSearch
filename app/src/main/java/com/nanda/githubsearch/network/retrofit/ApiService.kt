package com.nanda.githubsearch.network.retrofit

import com.nanda.githubsearch.network.response.DataItems
import retrofit2.Call
import com.nanda.githubsearch.network.response.ResponseList
import com.nanda.githubsearch.network.response.DetailUserResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path



interface ApiService {

    @GET("search/users")
    fun getUsers(
        @Query("q") username: String?
    ): Call<ResponseList>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<DataItems>>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<DataItems>>
}
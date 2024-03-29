package com.nanda.githubsearch.network.response

import com.google.gson.annotations.SerializedName


data class DetailUserResponse(
    @SerializedName("avatar_url")
    val avatarUrl: String?,

    @SerializedName("followers")
    val followers: Int?,

    @SerializedName("following")
    val following: Int?,

    @SerializedName("login")
    val login: String,

    @SerializedName("bio")
    val bio: Any? = null,

    @SerializedName("name")
    val name: String? = null
)

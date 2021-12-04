package com.example.sample.data.model

import com.google.gson.annotations.SerializedName

data class GetAccessTokenResponseModel(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("user_id")
    val userId: Long
)

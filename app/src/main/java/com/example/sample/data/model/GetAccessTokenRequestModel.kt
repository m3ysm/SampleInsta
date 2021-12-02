package com.example.sample.data.model

import com.google.gson.annotations.SerializedName

class GetAccessTokenRequestModel(
    @SerializedName("client_id")
    val clientId: String,
    @SerializedName("client_secret")
    val clientSecret: String,
    @SerializedName("grant_type")
    val grantType: String,
    @SerializedName("redirect_uri")
    val redirectUri: String,
    @SerializedName("code")
    val code: String
)
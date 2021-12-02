package com.example.sample.data.model

import com.google.gson.annotations.SerializedName

data class GetMediaDataResponseModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("media_type")
    val mediaType: String,
    @SerializedName("media_url")
    val mediaUrl: String,
    @SerializedName("username")
    val userName: String,
    @SerializedName("timestamp")
    val timeStamp: String
)

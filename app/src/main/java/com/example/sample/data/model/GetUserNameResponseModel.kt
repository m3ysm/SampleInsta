package com.example.sample.data.model

import com.google.gson.annotations.SerializedName

data class GetUserNameResponseModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("username")
    val userName: String
)

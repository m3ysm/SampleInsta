package com.example.sample.data.model

import com.google.gson.annotations.SerializedName

data class GetIdAndCaptionResponseModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("caption")
    val caption: String
)

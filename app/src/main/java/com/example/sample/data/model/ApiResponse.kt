package com.example.batmanproject.data.model

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("Search")
    val search: T,
    @SerializedName("totalResults")
    val totalResults: Int,
    @SerializedName("Response")
    val response: Boolean
)

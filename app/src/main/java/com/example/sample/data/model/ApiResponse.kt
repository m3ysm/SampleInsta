package com.example.sample.data.model

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("data")
    val data: T,
    @SerializedName("paging")
    val paging: PagingModel
)

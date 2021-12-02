package com.example.sample.data.model

import com.google.gson.annotations.SerializedName

data class Cursors(

    @SerializedName("after")
    val after: String,
    @SerializedName("before")
    val before: String
)
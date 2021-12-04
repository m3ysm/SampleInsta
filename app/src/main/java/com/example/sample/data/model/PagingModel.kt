package com.example.sample.data.model

import com.google.gson.annotations.SerializedName

data class PagingModel(
    @SerializedName("cursors")
    val cursors: Cursors,
    @SerializedName("previous")
    val previous: String,
    @SerializedName("next")
    val next: String
)

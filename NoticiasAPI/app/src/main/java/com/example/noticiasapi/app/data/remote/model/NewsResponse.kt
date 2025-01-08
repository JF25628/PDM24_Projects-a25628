package com.example.noticiasapi.app.data.remote.model

import com.google.gson.annotations.SerializedName

data class NewsResponse (
    @SerializedName("country") val country: String,
    @SerializedName("language") val language: String,
    @SerializedName("top_news") val topNews: List<TopNew>
)

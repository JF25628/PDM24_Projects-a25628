package com.example.noticiasapi.app.data.remote.model

import com.google.gson.annotations.SerializedName

data class TopNew(
    @SerializedName("news")
    val news: List<NewsDto>
)
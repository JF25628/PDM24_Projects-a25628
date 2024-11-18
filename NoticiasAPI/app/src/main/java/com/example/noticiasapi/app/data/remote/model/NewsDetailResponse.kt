package com.example.noticiasapi.app.data.remote.model

import com.google.gson.annotations.SerializedName

data class NewsDetailResponse (
    @SerializedName("news") val news : List<NewsDetailDto>
)
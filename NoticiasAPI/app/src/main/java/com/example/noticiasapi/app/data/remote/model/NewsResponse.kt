package com.example.noticiasapi.app.data.remote.model

data class NewsResponse(
    val status: String,
    val news: List<NewsItem>
)

data class NewsItem(
    val id: String,
    val title: String,
    val description: String,
    val url: String,
    val image: String?,
    val published: String
)

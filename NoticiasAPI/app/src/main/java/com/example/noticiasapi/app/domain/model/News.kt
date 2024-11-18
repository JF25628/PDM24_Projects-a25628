package com.example.noticiasapi.app.domain.model

//data class NewsResponse(
//    val news_res: List<NewsItem>
//)
//
//data class NewsItem(
//    val news: List<News>
//)

data class News(
    val id: Int,
    val title: String,
    val language: String,
    val summary: String?
)
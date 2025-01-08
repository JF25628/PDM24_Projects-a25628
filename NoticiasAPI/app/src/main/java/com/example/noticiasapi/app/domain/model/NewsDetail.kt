package com.example.noticiasapi.app.domain.model

data class NewsDetail(
    val id: Int,
    val author: String,
    val publishDate: String,
    val text: String,
    val title: String,
)
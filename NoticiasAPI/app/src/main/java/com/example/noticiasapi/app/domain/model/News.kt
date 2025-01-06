package com.example.noticiasapi.app.domain.model

data class News(
    val id: Int,
    val title: String,
    val language: String,
    val summary: String?
)
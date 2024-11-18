package com.example.noticiasapi.app.data.remote.model

import com.example.noticiasapi.app.domain.model.News

data class NewsDto(
    val id: String,
    val name: String,
    val symbol: String
) {
    fun toNews(): News {
        return News(id = id, name = name, symbol = symbol)
    }
}
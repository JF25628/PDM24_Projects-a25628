package com.example.noticiasapi.app.data.repository

import NewsRepository
import com.example.noticiasapi.app.data.remote.api.NewsAPI
import com.example.noticiasapi.app.data.remote.api.RetrofitInstance
import com.example.noticiasapi.app.data.remote.model.NewsItem
import com.example.noticiasapi.app.domain.model.News
import com.example.noticiasapi.app.domain.model.NewsDetail

class NewsRepositoryImpl(private val api: NewsAPI) : NewsRepository {
    override suspend fun getNews(): List<News>{
        return api.getNews().map { it.toNews() }
    }

    override suspend fun getNewsDetail(newId: String) : NewsDetail {
        return api.getNewsDetail(newId).toNewsDetail()
    }


    suspend fun getUSNews(): List<NewsItem>? {
        val response = RetrofitInstance.api.getUSNews(
            apiKey = "dbd175677492464fae34a6fb041753cc",
            sourceCountry = "us",
            language = "en"
        )
        return if (response.isSuccessful) response.body()?.news else null
    }
}




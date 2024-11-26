package com.example.noticiasapi.app.data.repository

import NewsRepository
import android.util.Log
import com.example.noticiasapi.app.data.remote.api.NewsAPI

import com.example.noticiasapi.app.domain.model.News
import com.example.noticiasapi.app.domain.model.NewsDetail


class NewsRepositoryImpl(private val api: NewsAPI) : NewsRepository {

    private val apiKey = "dbd175677492464fae34a6fb041753cc"

    override suspend fun getNews(): List<News> {
        val response = api.getNews(
            sourceCountry = "us",
            language = "en",
            apiKey = apiKey
        )
        val topNews = response.topNews.flatMap {    it.news }
        return topNews.map { it.toNews() }
    }


    override suspend fun getNewsDetail(newsId: Int): NewsDetail {
        try {
            val response = api.getNewsDetail(newsId, apiKey)
            val newsDetailDto = response.news.firstOrNull { it.id == newsId }

            if (newsDetailDto != null) {
                return newsDetailDto.toNewsDetail()
            } else {
                throw Exception("News with id $newsId not found.")
            }
        } catch (e: Exception) {
            Log.e("Error", "Error fetching news details: ${e.message}", e)
            throw e
        }
    }
}




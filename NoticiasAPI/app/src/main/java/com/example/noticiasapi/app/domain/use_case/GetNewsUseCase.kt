package com.example.noticiasapi.app.domain.use_case

import NewsRepository
import com.example.noticiasapi.app.domain.model.News

class GetNewsUseCase (private val repository: NewsRepository) : List<News> {
    suspend operator fun invoke(): List<News>{
        return repository.getNews()
    }
}
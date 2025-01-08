package com.example.noticiasapi.app.presentation.news_list.viewModel

import com.example.noticiasapi.app.domain.use_case.GetNewsUseCase
import com.example.noticiasapi.app.domain.model.News
import com.example.noticiasapi.app.data.repository.NewsRepositoryImpl
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noticiasapi.app.data.remote.api.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NewsListViewModel : ViewModel()
{
    private val api = RetrofitInstance.api
    private val repository = NewsRepositoryImpl(api)
    private val getNewsUseCase = GetNewsUseCase(repository)

    val newsList = MutableStateFlow<List<News>>(emptyList())

    fun fetchNews()
    {
        viewModelScope.launch {
            try
            {
                val news = getNewsUseCase()
                newsList.value = news
            }
            catch (e: Exception)
            {
                newsList.value = emptyList()

            }
        }
    }

}
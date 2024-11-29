package com.example.noticiasapi.app.presentation.news_list.viewModel

import GetNewsDetailsUseCase
import com.example.noticiasapi.app.domain.model.NewsDetail
import com.example.noticiasapi.app.data.repository.NewsRepositoryImpl
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noticiasapi.app.data.remote.api.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NewsDetailListViewModel : ViewModel()
{
    private val api = RetrofitInstance.api
    private val repository = NewsRepositoryImpl(api)
    private val getNewsDetailUseCase = GetNewsDetailsUseCase(repository)

    val newsDetail = MutableStateFlow<NewsDetail?>(null)

    fun fetchNewsDetail(newsId: Int)
    {
        viewModelScope.launch {
            try {
                newsDetail.value = getNewsDetailUseCase(newsId)
            }
            catch (e: Exception){
                newsDetail.value = null
            }
        }

    }

}



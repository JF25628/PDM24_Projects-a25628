package com.example.noticiasapi.app.presentation.news_list
import com.example.noticiasapi.app.domain.use_case.GetNewsUseCase
import com.example.noticiasapi.app.domain.model.News
import com.example.noticiasapi.app.data.repository.NewsRepositoryImpl
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noticiasapi.app.data.remote.api.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NewsListViewModel : ViewModel(){
    private val api = RetrofitInstance.api
    private val repository = NewsRepositoryImpl(api)
    private val getNewsUseCase = GetNewsUseCase(repository)

    val news = MutableStateFlow<List<News>>(emptyList())

    fun fetchNews(){
        viewModelScope.launch {
            try {
                news.value = getNewsUseCase
            }
            catch (e: Exception){
                news.value = emptyList()
            }
        }
    }
}




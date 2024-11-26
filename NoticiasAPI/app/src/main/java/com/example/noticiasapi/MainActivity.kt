package com.example.noticiasapi


import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.example.noticiasapi.app.data.remote.api.RetrofitInstance.api
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.content.Intent
import android.util.Log

import android.widget.TextView
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.noticiasapi.app.presentation.news_list.screens.NewsDetailListScreen
import com.example.noticiasapi.app.presentation.news_list.screens.NewsListScreen
import com.example.noticiasapi.app.presentation.news_list.viewModel.NewsDetailListViewModel
import com.example.noticiasapi.app.presentation.news_list.viewModel.NewsListViewModel


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    var selectedNewsId by remember { mutableStateOf<Int?>(null) }

    if (selectedNewsId == null) {
        val newsListViewModel: NewsListViewModel = viewModel()
        NewsListScreen (newsListViewModel) { newsId ->
            selectedNewsId = newsId
        }
    } else {
        val newsDetailViewModel: NewsDetailListViewModel = viewModel()
        NewsDetailListScreen (newsDetailViewModel,selectedNewsId!!) {
            selectedNewsId = null
        }
    }
}
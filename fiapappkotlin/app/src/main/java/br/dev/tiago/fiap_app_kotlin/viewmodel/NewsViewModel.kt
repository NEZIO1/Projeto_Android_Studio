package br.dev.tiago.fiap_app_kotlin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.dev.tiago.fiap_app_kotlin.data.repository.NewsRepository
import br.dev.tiago.fiap_app_kotlin.data.remote.NewsArticle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class NewsViewModel(private val repository: NewsRepository) : ViewModel() {
    val _news = MutableStateFlow<List<NewsArticle>>(emptyList())
    val news: StateFlow<List<NewsArticle>> get() = _news

    init {
        fetchNews()
    }

    private fun fetchNews() {
        viewModelScope.launch {
            try {
                val response = repository.getNews()
                _news.value = response.results.take(5)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

package br.dev.tiago.fiap_app_kotlin.data.repository

import br.dev.tiago.fiap_app_kotlin.data.remote.NewsApiService
import br.dev.tiago.fiap_app_kotlin.data.remote.NewsResponse

class NewsRepository(private val apiService: NewsApiService) {
    suspend fun getNews(): NewsResponse {
        return apiService.getNews()
    }
}

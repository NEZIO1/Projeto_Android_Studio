package br.dev.tiago.fiap_app_kotlin.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

data class NewsResponse(
    val results: List<NewsArticle>
)

data class NewsArticle(
    val id: Long,
    val href: String,
    val title: String,
    val description: String
)

interface NewsApiService {
    @GET("everything")
    suspend fun getNews(
        @Query("api_key") apiKey: String = "api_live_o7MsvA5HsZLIBZxgFc0fky3SWIDr3WxZoPTOHVojuff4Wp8v",
        @Query("language") language: String = "pt"
    ): NewsResponse
}

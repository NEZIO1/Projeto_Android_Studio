package br.com.fiap.challenge.data

interface ApiService {
    @POST("risco-psico")
    suspend fun enviarRisco(@Body body: RiscoPsicoRequest): StatusResponse

    annotation class StatusResponse

    annotation class RiscoPsicoRequest

    annotation class Body

    @POST("diario-humor")
    suspend fun enviarHumor(@Body body: DiarioHumorRequest): StatusResponse

    annotation class DiarioHumorRequest

    @GET("dashboard")
    suspend fun obterDashboard(): DashboardResponse

    annotation class DashboardResponse

    annotation class GET(val string: String)
}

annotation class POST(val string: String)

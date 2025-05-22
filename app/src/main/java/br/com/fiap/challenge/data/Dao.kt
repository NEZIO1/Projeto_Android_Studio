package br.com.fiap.challenge.data

@Dao
interface HealthDao {
    @Insert suspend fun insertRisco(risco: RiscoEntity)
    @Query("SELECT COUNT(*) FROM resposta_risco") suspend fun countRiscos(): Int

    @Insert suspend fun insertHumor(humor: HumorEntity)
    @Query("""
        SELECT AVG(CASE 
            WHEN humor = 'Triste' THEN 1 
            WHEN humor = 'Alegre' THEN 5 
            ELSE 3 END) 
        FROM entrada_humor 
        WHERE timestamp > :since
    """)
    suspend fun avgHumor(since: Long): Double
}

annotation class Dao

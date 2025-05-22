package br.com.fiap.challenge.data

@Entity(tableName = "resposta_risco")
data class RiscoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val perguntaId: Int,
    val valor: String
)

@Entity(tableName = "entrada_humor")
data class HumorEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val humor: String,
    val obs: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)

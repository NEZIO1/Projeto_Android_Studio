package br.com.fiap.despesas.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_despesa")
data class Despesa(
    @PrimaryKey (autoGenerate = true) var id: Long = 0,
    @ColumnInfo (name = "desc_despesa") var descricaoDespesa: String = "",
    @ColumnInfo(name = "data_desc_despesa") var dataDespesa: String = "",
    var valor: Double = 0.0
)

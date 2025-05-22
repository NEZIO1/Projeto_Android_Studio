package br.com.fiap.despesas.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import br.com.fiap.despesas.model.Despesa

@Dao
interface DespesaDao {

    @Insert
    fun salvar(despesa: Despesa): Long

    @Update
    fun atualizar(despesa: Despesa) : Int

}
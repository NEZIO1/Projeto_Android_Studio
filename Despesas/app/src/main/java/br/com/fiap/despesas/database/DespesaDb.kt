package br.com.fiap.despesas.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.fiap.despesas.dao.DespesaDao
import br.com.fiap.despesas.model.Despesa

@Database(entities = [Despesa::class], version = 1)
abstract class DespesaDb: RoomDatabase(){

    abstract fun despesaDao(): DespesaDao
}
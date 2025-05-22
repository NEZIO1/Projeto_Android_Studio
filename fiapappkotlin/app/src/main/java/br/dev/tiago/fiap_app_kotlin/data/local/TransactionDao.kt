package br.dev.tiago.fiap_app_kotlin.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity)

    @Delete
    suspend fun deleteTransaction(transaction: TransactionEntity)

    @Query("SELECT * FROM transactions ORDER BY date DESC")
    fun getAllTransactions(): Flow<List<TransactionEntity>>

    @Query("SELECT SUM(amount) FROM transactions")
    fun getTotalBalance(): Flow<Double>

    @Query("SELECT SUM(amount) FROM transactions WHERE type = 'Investimento' AND strftime('%m', date / 1000, 'unixepoch', 'localtime') = :month AND strftime('%Y', date / 1000, 'unixepoch', 'localtime') = :year")
    fun getTotalInvestments(month: String, year: String): Flow<Double?>

    @Query("SELECT SUM(amount) FROM transactions WHERE type = 'Receita' AND strftime('%m', date / 1000, 'unixepoch', 'localtime') = :month AND strftime('%Y', date / 1000, 'unixepoch', 'localtime') = :year")
    fun getTotalRevenue(month: String, year: String): Flow<Double?>

    @Query("SELECT SUM(amount) FROM transactions WHERE type = 'Despesa' AND strftime('%m', date / 1000, 'unixepoch', 'localtime') = :month AND strftime('%Y', date / 1000, 'unixepoch', 'localtime') = :year")
    fun getTotalExpenses(month: String, year: String): Flow<Double?>

    @Query("SELECT SUM(amount) FROM transactions WHERE type = 'Investimento'")
    fun getTotalInvestmentsAll(): Flow<Double?>

    @Query("SELECT SUM(amount) FROM transactions WHERE type = 'Receita'")
    fun getTotalRevenueAll(): Flow<Double?>

    @Query("SELECT SUM(amount) FROM transactions WHERE type = 'Despesa'")
    fun getTotalExpensesAll(): Flow<Double?>
}

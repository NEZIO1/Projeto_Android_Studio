package br.dev.tiago.fiap_app_kotlin.repository

import br.dev.tiago.fiap_app_kotlin.data.local.TransactionDao
import br.dev.tiago.fiap_app_kotlin.data.local.TransactionEntity
import kotlinx.coroutines.flow.Flow

class TransactionRepository(private val transactionDao: TransactionDao) {

    val allTransactions: Flow<List<TransactionEntity>> = transactionDao.getAllTransactions()

    val totalBalance: Flow<Double> = transactionDao.getTotalBalance()

    suspend fun insertTransaction(transaction: TransactionEntity) {
        transactionDao.insertTransaction(transaction)
    }

    suspend fun deleteTransaction(transaction: TransactionEntity) {
        transactionDao.deleteTransaction(transaction)
    }

    fun getTotalInvestments(month: String, year: String): Flow<Double?> =
        transactionDao.getTotalInvestments(month, year)

    fun getTotalRevenue(month: String, year: String): Flow<Double?> =
        transactionDao.getTotalRevenue(month, year)

    fun getTotalExpenses(month: String, year: String): Flow<Double?> =
        transactionDao.getTotalExpenses(month, year)

    fun getTotalInvestmentsAll(): Flow<Double?> = transactionDao.getTotalInvestmentsAll()
    fun getTotalRevenueAll(): Flow<Double?> = transactionDao.getTotalRevenueAll()
    fun getTotalExpensesAll(): Flow<Double?> = transactionDao.getTotalExpensesAll()
}

package com.budgeteer.service

import com.budgeteer.model.Transaction
import com.budgeteer.model.TransactionRequest
import com.budgeteer.repository.TransactionRepository
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Singleton
class TransactionService(
    private val transactionRepository: TransactionRepository
) {
    fun getAllTransactions(): Flow<Transaction> {
        return transactionRepository.findAll()
    }

    suspend fun getTransaction(id: Long): Transaction? {
        return transactionRepository.findById(id)
    }

    suspend fun createTransaction(request: TransactionRequest): Transaction {
        val category = determineCategory(request.merchant)
        val transaction = Transaction(
            transactionDate = request.transactionDate,
            amount = request.amount,
            description = request.description,
            merchant = request.merchant,
            category = category,
            originalCurrency = request.originalCurrency,
            note = request.note,
            location = request.location
        )
        return transactionRepository.save(transaction)
    }

    suspend fun getTransactionsByDateRange(
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): Flow<Transaction> {
        return transactionRepository.findByTransactionDateBetween(startDate, endDate)
    }

    private fun determineCategory(merchant: String): String {
        // TODO: Implement category determination logic using your categories.json data
        return "Uncategorized"
    }
}
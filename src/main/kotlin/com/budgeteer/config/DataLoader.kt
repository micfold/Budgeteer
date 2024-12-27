package com.budgeteer.config

import com.budgeteer.model.Transaction
import com.budgeteer.repository.TransactionRepository
import io.micronaut.context.event.StartupEvent
import io.micronaut.runtime.event.annotation.EventListener
import jakarta.inject.Singleton
import kotlinx.coroutines.runBlocking
import java.math.BigDecimal
import java.time.LocalDateTime

@Singleton
class DataLoader(
    private val transactionRepository: TransactionRepository
) {
    @EventListener
    fun loadData(event: StartupEvent) {
        runBlocking {
            val sampleTransactions = listOf(
                Transaction(
                    transactionDate = LocalDateTime.now(),
                    amount = BigDecimal("124.50"),
                    description = "Grocery shopping",
                    merchant = "Albert",
                    category = "Groceries",
                    originalCurrency = "CZK"
                ),
                Transaction(
                    transactionDate = LocalDateTime.now().minusDays(1),
                    amount = BigDecimal("45.00"),
                    description = "Coffee and cake",
                    merchant = "Starbucks",
                    category = "Food & Dining",
                    originalCurrency = "CZK"
                ),
                Transaction(
                    transactionDate = LocalDateTime.now().minusDays(2),
                    amount = BigDecimal("350.00"),
                    description = "Monthly transport pass",
                    merchant = "DPP",
                    category = "Transportation",
                    originalCurrency = "CZK"
                )
            )

            sampleTransactions.forEach { transaction ->
                transactionRepository.save(transaction)
            }
        }
    }
}
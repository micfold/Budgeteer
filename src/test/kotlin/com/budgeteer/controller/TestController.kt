package com.budgeteer.controller

import com.budgeteer.model.TransactionRequest
import com.budgeteer.service.TransactionService
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@MicronautTest
@Controller("/test")
class TestController(
    private val transactionService: TransactionService
) {
    @Get("/populate")
    suspend fun populateTestData(): String {
        val testRequests = listOf(
            TransactionRequest(
                amount = BigDecimal("124.50"),
                description = "Grocery shopping",
                merchant = "Albert",
                transactionDate = LocalDateTime.now(),
                originalCurrency = "CZK"
            ),
            TransactionRequest(
                amount = BigDecimal("45.00"),
                description = "Coffee and cake",
                merchant = "Starbucks",
                transactionDate = LocalDateTime.now().minusDays(1),
                originalCurrency = "CZK"
            )
        )

        testRequests.forEach { request ->
            transactionService.createTransaction(request)
        }

        return "Test data populated"
    }

    @Get("/summary")
    fun getTestSummary(): Flow<String> {
        return transactionService.getAllTransactions()
            .map { transaction ->
                "${transaction.transactionDate.format(DateTimeFormatter.ISO_LOCAL_DATE)} - " +
                        "${transaction.merchant}: ${transaction.amount} ${transaction.originalCurrency}"
            }
    }
}
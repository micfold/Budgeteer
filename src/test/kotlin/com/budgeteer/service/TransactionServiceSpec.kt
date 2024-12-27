package com.budgeteer.service

import com.budgeteer.model.Transaction
import com.budgeteer.model.TransactionRequest
import com.budgeteer.repository.TransactionRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import java.math.BigDecimal
import java.time.LocalDateTime

@MicronautTest
class TransactionServiceSpec : BehaviorSpec({
    val repository = mockk<TransactionRepository>()
    val service = TransactionService(repository)

    given("a transaction service") {
        `when`("creating a new transaction") {
            val request = TransactionRequest(
                amount = BigDecimal("100.00"),
                description = "Test transaction",
                merchant = "Test Merchant",
                transactionDate = LocalDateTime.now(),
                originalCurrency = "CZK"
            )

            val savedTransaction = Transaction(
                id = 1L,
                amount = request.amount,
                description = request.description,
                merchant = request.merchant,
                transactionDate = request.transactionDate,
                category = "Uncategorized",
                originalCurrency = request.originalCurrency
            )

            coEvery { repository.save(any()) } returns savedTransaction

            then("it should save and return the transaction") {
                val result = service.createTransaction(request)
                result.id shouldBe 1L
                result.amount shouldBe request.amount
                result.description shouldBe request.description

                coVerify { repository.save(any()) }
            }
        }

        `when`("retrieving a transaction by id") {
            val transaction = Transaction(
                id = 1L,
                amount = BigDecimal("100.00"),
                description = "Test transaction",
                merchant = "Test Merchant",
                transactionDate = LocalDateTime.now(),
                category = "Uncategorized",
                originalCurrency = "CZK"
            )

            coEvery { repository.findById(1L) } returns transaction
            coEvery { repository.findById(2L) } returns null

            then("it should return the transaction when it exists") {
                val result = service.getTransaction(1L)
                result shouldNotBe null
                result?.id shouldBe 1L
            }

            then("it should return null when transaction doesn't exist") {
                val result = service.getTransaction(2L)
                result shouldBe null
            }
        }

        `when`("retrieving transactions by date range") {
            val startDate = LocalDateTime.now().minusDays(1)
            val endDate = LocalDateTime.now()
            val transactions = listOf(
                Transaction(
                    id = 1L,
                    amount = BigDecimal("100.00"),
                    description = "Test transaction",
                    merchant = "Test Merchant",
                    transactionDate = LocalDateTime.now(),
                    category = "Uncategorized",
                    originalCurrency = "CZK"
                )
            )

            coEvery {
                repository.findByTransactionDateBetween(startDate, endDate)
            } returns flowOf(*transactions.toTypedArray())

            then("it should return transactions within the date range") {
                val result = service.getTransactionsByDateRange(startDate, endDate)
                result shouldNotBe null
            }
        }
    }
})
package com.budgeteer.controller

import com.budgeteer.model.Transaction
import com.budgeteer.model.TransactionRequest
import com.budgeteer.service.TransactionService
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.micronaut.http.HttpStatus
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import java.math.BigDecimal
import java.time.LocalDateTime

@MicronautTest
class TransactionControllerSpec : BehaviorSpec({
    val service = mockk<TransactionService>()
    val controller = TransactionController(service)

    given("a transaction controller") {
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

            coEvery { service.createTransaction(request) } returns savedTransaction

            then("it should return created status") {
                val response = controller.createTransaction(request)
                response.status shouldBe HttpStatus.CREATED
                response.body() shouldBe savedTransaction
            }
        }

        `when`("retrieving all transactions") {
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

            coEvery { service.getAllTransactions() } returns flowOf(*transactions.toTypedArray())

            then("it should return all transactions") {
                val result = controller.getTransactions()
                result shouldNotBe null
            }
        }
    }
})
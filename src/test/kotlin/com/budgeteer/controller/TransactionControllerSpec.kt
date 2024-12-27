package com.budgeteer.controller

import com.budgeteer.model.Transaction
import com.budgeteer.model.TransactionRequest
import com.budgeteer.service.TransactionService
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.micronaut.http.HttpStatus
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import java.math.BigDecimal
import java.time.LocalDateTime

class TransactionControllerSpec : BehaviorSpec({
    val service = mockk<TransactionService>()
    val controller = TransactionController(service)

    Given("a transaction controller") {
        When("creating a new transaction") {
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

            Then("it should return created status") {
                val response = controller.createTransaction(request)
                response.status shouldBe HttpStatus.CREATED
                response.body() shouldBe savedTransaction
            }
        }

        When("retrieving all transactions") {
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

            Then("it should return all transactions") {
                val result = controller.getTransactions()
                result shouldNotBe null
            }
        }
    }
})
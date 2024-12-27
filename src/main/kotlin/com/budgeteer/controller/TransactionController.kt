package com.budgeteer.controller

import com.budgeteer.model.Transaction
import com.budgeteer.model.TransactionRequest
import com.budgeteer.service.TransactionService
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Controller("/api/transactions")
class TransactionController(
    private val transactionService: TransactionService
) {
    @Get
    fun getTransactions(): Flow<Transaction> {
        return transactionService.getAllTransactions()
    }

    @Get("/{id}")
    suspend fun getTransaction(id: Long): HttpResponse<Transaction> {
        val transaction = transactionService.getTransaction(id)
        return if (transaction != null) {
            HttpResponse.ok(transaction)
        } else {
            HttpResponse.notFound()
        }
    }

    @Post
    suspend fun createTransaction(@Body request: TransactionRequest): HttpResponse<Transaction> {
        val transaction = transactionService.createTransaction(request)
        return HttpResponse.created(transaction)
    }

    @Get("/search")
    suspend fun searchTransactions(
        @QueryValue startDate: LocalDateTime,
        @QueryValue endDate: LocalDateTime,
    ): Flow<Transaction> {
        return transactionService.getTransactionsByDateRange(startDate, endDate)
    }
}
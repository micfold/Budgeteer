package com.budgeteer.repository

import com.budgeteer.model.Transaction
import io.micronaut.data.repository.kotlin.CoroutineCrudRepository
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@JdbcRepository(dialect = Dialect.H2)
interface TransactionRepository : CoroutineCrudRepository<Transaction, Long> {
    suspend fun findByTransactionDateBetween(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
    ): Flow<Transaction>

    suspend fun findByCategory(category: String): Flow<Transaction>

    suspend fun findByMerchantContainsIgnoreCase(merchant: String): Flow<Transaction>
}
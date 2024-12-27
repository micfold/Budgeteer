package com.budgeteer.model

import io.micronaut.data.annotation.GeneratedValue
import java.math.BigDecimal
import java.time.LocalDateTime
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity

@MappedEntity
data class Transaction(
    @field:Id
    @field:GeneratedValue
    val id: Long? = null,
    val transactionDate: LocalDateTime,
    val amount: BigDecimal,
    val description: String,
    val merchant: String,
    val category: String,
    val originalCurrency: String,
    val exchangeRate: BigDecimal? = null,
    val note: String? = null,
    val location: String? = null
)

data class TransactionRequest(
    val amount: BigDecimal,
    val description: String,
    val merchant: String,
    val transactionDate: LocalDateTime,
    val originalCurrency: String = "CZK",
    val note: String? = null,
    val location: String? = null
)
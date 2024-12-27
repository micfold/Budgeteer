package com.budgeteer.model

import io.micronaut.data.annotation.GeneratedValue
import java.math.BigDecimal
import java.time.LocalDateTime
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.MappedProperty

@MappedEntity("transaction")
data class Transaction(
    @field:Id
    @field:GeneratedValue(GeneratedValue.Type.IDENTITY)
    val id: Long? = null,

    @MappedProperty("transaction_date")
    val transactionDate: LocalDateTime,

    @MappedProperty("amount")
    val amount: BigDecimal,

    @MappedProperty("description")
    val description: String,

    @MappedProperty("merchant")
    val merchant: String,

    @MappedProperty("category")
    val category: String,

    @MappedProperty("original_currency")
    val originalCurrency: String,

    @MappedProperty("exchange_rate")
    val exchangeRate: BigDecimal? = null,

    @MappedProperty("note")
    val note: String? = null,

    @MappedProperty("location")
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
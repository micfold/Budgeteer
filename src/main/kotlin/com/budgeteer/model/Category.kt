package com.budgeteer.model

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class Category(
    val name: String,
    val keywords: List<String>
)

@Serdeable
data class Categories(
    val categories: Map<String, List<String>>
)
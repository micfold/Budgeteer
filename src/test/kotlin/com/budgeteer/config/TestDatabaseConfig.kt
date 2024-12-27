package com.budgeteer.config

import io.micronaut.context.annotation.ConfigurationProperties
import io.micronaut.context.annotation.Context
import io.micronaut.test.support.TestPropertyProvider
import jakarta.inject.Singleton


@Singleton
@Context
@ConfigurationProperties("test")
class TestDatabaseConfig : TestPropertyProvider {
    override fun getProperties(): MutableMap<String, String> {
        return mutableMapOf(
            "datasources.default.url" to "jdbc:h2:mem:testdb;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;DB_CLOSE_DELAY=-1",
            "datasources.default.username" to "sa",
            "datasources.default.password" to "",
            "datasources.default.schema-generate" to "NONE",
            "flyway.datasources.default.enabled" to "true",
            "flyway.datasources.default.clean-schema" to "true",
            "flyway.datasources.default.clean-disabled" to "false"
        )
    }
}
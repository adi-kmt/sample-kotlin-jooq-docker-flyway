package com.example.kotlin.config

import io.r2dbc.pool.PoolingConnectionFactoryProvider.*
import io.r2dbc.postgresql.PostgresqlConnectionFactoryProvider.*
import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryOptions
import io.r2dbc.spi.ConnectionFactoryOptions.*
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.r2dbc.connection.R2dbcTransactionManager
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.transaction.ReactiveTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.time.Duration


@Configuration
class R2DBCConfiguration(
    private val properties: R2dbcProperties
) {

    @Bean
    fun databaseClient(
        connectionFactory: ConnectionFactory
    ): DatabaseClient = DatabaseClient.builder()
        .connectionFactory(connectionFactory)
        .build()
    @Bean
    @Primary
    fun connectionFactory(properties: R2dbcProperties): ConnectionFactory {
        val options: MutableMap<String, String> = HashMap()
        return ConnectionFactories.get(
            ConnectionFactoryOptions.builder()
                .option(PROTOCOL, "postgresql")
                .option<String>(DRIVER, "pool")
                .option<String>(HOST, "postgres")
                .option<Int>(PORT, 5432)
                .option<String>(USER, "postgres")
                .option(PASSWORD, "pass123")
                .option<String>(DATABASE, "postgres")
                .option(MAX_SIZE, 20)
                .option(INITIAL_SIZE, 20)
                .option(SCHEMA, "public")
                .option(MAX_ACQUIRE_TIME, Duration.ofSeconds(30))
                .option(MAX_IDLE_TIME, Duration.ofSeconds(30))
                .build()
        )
    }

    @Bean
    fun transactionManager(connectionFactory: ConnectionFactory): ReactiveTransactionManager {
        return R2dbcTransactionManager(connectionFactory)
    }
}
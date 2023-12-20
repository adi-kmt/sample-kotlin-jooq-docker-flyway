package com.example.kotlin.config

import io.r2dbc.pool.ConnectionPool
import io.r2dbc.pool.ConnectionPoolConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import io.r2dbc.spi.ConnectionFactory
import org.jooq.DSLContext
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.r2dbc.core.DatabaseClient

/***
 * Just created for explicitly configuring connection pool for playing around
 * Not needed
 */
@Configuration
class R2DBCConfiguration(
    private val properties: R2dbcProperties
) {

   /* @Bean
    fun connectionPool(
        connectionFactory: ConnectionFactory,
    ): ConnectionPool {
        val poolConfig = ConnectionPoolConfiguration.builder(connectionFactory)
            .maxIdleTime(properties.pool.maxIdleTime )
            .maxSize(properties.pool.maxSize)
            .build()

        return ConnectionPool(poolConfig)
    }

    @Bean
    fun databaseClient(
        connectionFactory: ConnectionFactory
    ): DatabaseClient {
        return DatabaseClient.create(connectionFactory)
    }

    @Bean
    fun connectionFactory(properties: R2dbcProperties): ConnectionFactory {
        val connectionConfiguration = PostgresqlConnectionConfiguration
            .builder()
            .host("localhost")
            .port(5432) // Your database port
            .database(properties.name)
            .username(properties.username)
            .password(properties.password)
            .build()

        return PostgresqlConnectionFactory(connectionConfiguration)
    }*/
}
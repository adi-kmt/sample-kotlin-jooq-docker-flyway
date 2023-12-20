package com.example.kotlin.config

import io.r2dbc.spi.ConnectionFactory
import org.jooq.DSLContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JooqConfig {
    @Bean
    fun dslContext(connectionFactory: ConnectionFactory): DSLContext {
        return org.jooq.impl.DSL.using(connectionFactory).dsl()
    }
}
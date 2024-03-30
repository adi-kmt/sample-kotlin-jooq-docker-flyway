package com.example.kotlin.repository

import com.example.generated.Tables.PRODUCT
import com.example.kotlin.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.jooq.DSLContext
import org.springframework.cache.annotation.Cacheable
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype.Repository
import org.springframework.transaction.reactive.TransactionalOperator
import org.springframework.transaction.reactive.executeAndAwait
import java.util.*

@Repository
@ComponentScan("com.example.generated.tables.daos")
class ProductRepository(
    private val jooq: DSLContext,
    private val transactionalOperator: TransactionalOperator
) {

    suspend fun findAll(): Flow<Product> = jooq
        .selectFrom(PRODUCT)
        .asFlow()
        .map { Product(it.id!!, it.name!!) }

    suspend fun save(product: Product): UUID =
        transactionalOperator.executeAndAwait { reactiveTransaction ->
            runCatching {
                println("******reached here********")
                jooq
                    .insertInto(PRODUCT)
                    .columns(PRODUCT.ID, PRODUCT.NAME)
                    .values(product.id, product.name)
                    .awaitFirst()
            }.onFailure {
                // Rollback the transaction in case of an exception
                reactiveTransaction.setRollbackOnly()
            }.getOrThrow().also {
                println("******completed********")
            }
            product.id
        }



    @Cacheable("yourCacheName", key = "#id")
    suspend fun get(id: UUID): Product? = jooq
        .selectFrom(PRODUCT)
        .where(PRODUCT.ID.eq(id))
        .awaitFirstOrNull()
        ?.let { Product(it.id!!, it.name!!) }
}

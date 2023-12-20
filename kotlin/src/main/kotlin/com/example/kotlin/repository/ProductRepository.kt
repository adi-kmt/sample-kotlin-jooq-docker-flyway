package com.example.kotlin.repository

import com.example.generated.Tables.PRODUCT

import com.example.kotlin.model.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.withContext
import org.jooq.DSLContext
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class ProductRepository(
    private val jooq: DSLContext
) {

    suspend fun findAll(): Flow<Product> = jooq
        .selectFrom(PRODUCT)
        .asFlow()
        .map { Product(it.id, it.name) }

    suspend fun save(product: Product): UUID = withContext(Dispatchers.IO) {
         jooq
            .insertInto(PRODUCT)
            .columns(PRODUCT.ID, PRODUCT.NAME)
            .values(product.id, product.name)
            .returning(PRODUCT.ID)
            .awaitFirst()
            .id
    }

    @Cacheable("yourCacheName", key = "#id")
    suspend fun get(id: UUID): Product? = jooq
        .selectFrom(PRODUCT)
        .where(PRODUCT.ID.eq(id))
        .awaitFirstOrNull()
        ?.let { Product(it.id, it.name) }
}
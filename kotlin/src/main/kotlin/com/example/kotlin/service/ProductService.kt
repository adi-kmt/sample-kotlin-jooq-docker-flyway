package com.example.kotlin.service

import com.example.kotlin.model.Product
import com.example.kotlin.repository.ProductRepository
import kotlinx.coroutines.flow.toList
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class ProductService(
    private val repository: ProductRepository
) {

    suspend fun findAll(): List<Product> = repository
        .findAll()
        .toList()

    suspend fun save(product: Product): UUID {
        return repository
            .save(product)
    }

    @Cacheable("yourCacheName", key = "#id")
    suspend fun get(id: UUID): Product? = repository
        .get(id)
}
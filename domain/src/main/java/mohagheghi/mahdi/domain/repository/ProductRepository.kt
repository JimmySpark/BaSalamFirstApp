package mohagheghi.mahdi.domain.repository

import kotlinx.coroutines.flow.Flow
import mohagheghi.mahdi.domain.entity.Product
import mohagheghi.mahdi.domain.util.ResponseType

interface ProductRepository {
    suspend fun getProducts(): Flow<ResponseType<Flow<List<Product>>>>
}
package mohagheghi.mahdi.domain.usecase

import mohagheghi.mahdi.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(private val productRepository: ProductRepository) {

    suspend fun getProducts() = productRepository.getProducts()
}
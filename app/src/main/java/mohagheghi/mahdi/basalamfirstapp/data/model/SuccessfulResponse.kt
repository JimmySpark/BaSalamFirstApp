package mohagheghi.mahdi.basalamfirstapp.data.model

import mohagheghi.mahdi.basalamfirstapp.data.local.entity.Product

data class SuccessfulResponse(
    val data: Data
) {
    data class Data(
        val productSearch: ProductSearch
    ) {
        data class ProductSearch(
            val products: List<Product>
        )
    }
}
package mohagheghi.mahdi.basalamfirstapp.data.remote.model

import mohagheghi.mahdi.basalamfirstapp.data.local.entity.Product

data class Products(
    var data: Data
) {
    data class Data(
        var productSearch: ProductSearch
    )

    data class ProductSearch(
        var products: List<Product>
    )
}
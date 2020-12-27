package mohagheghi.mahdi.basalamfirstapp.data.model

data class Product(
    var data: Data
) {
    data class Data(
        var productSearch: ProductSearch
    )

    data class ProductSearch(
        var products: List<Products>
    )

    data class Products(
        val id: String,
        val name: String,
        val photo: Photo,
        val vendor: Vendor,
        val weight: Int,
        val price: Long,
        val rating: Rating
    )

    data class Photo(
        val url: String
    )

    data class Vendor(
        val name: String
    )

    data class Rating(
        val rating: Float,
        val count: Int
    )
}
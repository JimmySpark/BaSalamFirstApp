package mohagheghi.mahdi.domain.entity

data class Product(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val vendorName: String,
    val weight: Int,
    val price: Long,
    val rating: Float,
    val ratingCount: Int
)

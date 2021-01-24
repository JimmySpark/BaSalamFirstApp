package mohagheghi.mahdi.data.mapper

import mohagheghi.mahdi.data.entity.ProductCache
import mohagheghi.mahdi.domain.entity.Product
import javax.inject.Inject

class CacheMapper @Inject constructor() : Mapper<ProductCache, Product> {
    override fun mapFrom(from: ProductCache): Product {
        return Product(
            from.id,
            from.name,
            from.photo.url,
            from.vendor.name,
            from.weight,
            from.price,
            from.rating.rating,
            from.rating.count
        )
    }

    fun map(products: List<ProductCache>) = products.map { mapFrom(it) }
}
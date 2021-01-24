package mohagheghi.mahdi.data.mapper

import mohagheghi.mahdi.basalamfirstapp.GetProductsQuery
import mohagheghi.mahdi.data.entity.ProductCache
import javax.inject.Inject

class NetworkToCacheMapper @Inject constructor() : Mapper<GetProductsQuery.Product, ProductCache> {
    override fun mapFrom(from: GetProductsQuery.Product): ProductCache {
        val photo = ProductCache.Photo(from.photo?.url!!)
        val vendor = ProductCache.Vendor(from.vendor?.name!!)
        val rating = ProductCache.Rating(from.rating?.rating?.toFloat()!!, from.rating.count!!)
        return ProductCache(
            from.id?.toInt()!!,
            from.name,
            photo,
            vendor,
            from.weight!!,
            from.price.toLong(),
            rating
        )
    }

    fun map(data: GetProductsQuery.Data): List<ProductCache> =
        data.productSearch?.products?.map { mapFrom(it!!) } as List<ProductCache>
}
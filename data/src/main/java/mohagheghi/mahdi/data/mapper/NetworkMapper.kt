package mohagheghi.mahdi.data.mapper

import mohagheghi.mahdi.basalamfirstapp.GetProductsQuery
import mohagheghi.mahdi.domain.entity.Product
import javax.inject.Inject

class NetworkMapper @Inject constructor() : Mapper<GetProductsQuery.Product, Product> {
    override fun mapFrom(from: GetProductsQuery.Product): Product {
        return Product(
            from.id?.toInt()!!,
            from.name,
            from.photo?.url!!,
            from.vendor?.name!!,
            from.weight!!,
            from.price.toLong(),
            from.rating?.rating?.toFloat()!!,
            from.rating.count!!
        )
    }

    fun map(data: GetProductsQuery.Data): List<Product> =
        data.productSearch?.products?.map { mapFrom(it!!) } as List<Product>
}
package mohagheghi.mahdi.basalamfirstapp.data.util

import mohagheghi.mahdi.basalamfirstapp.GetProductsQuery
import mohagheghi.mahdi.basalamfirstapp.data.local.entity.Product

class ProductMapper(val data: GetProductsQuery.Data) {

    private fun dataToProduct(product: GetProductsQuery.Product): Product {
        val photo = Product.Photo(product.photo?.url!!)
        val vendor = Product.Vendor(product.vendor?.name!!)
        val rating = Product.Rating(product.rating?.rating?.toFloat()!!, product.rating.count!!)
        return Product(
            product.id?.toInt()!!,
            product.name,
            photo,
            vendor,
            product.weight!!,
            product.price.toLong(),
            rating
        )
    }

    fun map() = data.productSearch?.products?.map { dataToProduct(it!!) } as List<Product>
}
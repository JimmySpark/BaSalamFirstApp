package mohagheghi.mahdi.basalamfirstapp.data.util

import mohagheghi.mahdi.basalamfirstapp.GetProductsQuery
import mohagheghi.mahdi.basalamfirstapp.data.local.entity.Product

class ProductMapper(val data: GetProductsQuery.Data) {

    private fun dataToProduct(product: GetProductsQuery.Product): Product {
        var photo =
            Product.Photo("https://cdn.filesrvcloud.ir/v1/AUTH_4a3e3bbd118c41b48238766e82ca5e4a/mc/20a549f6a9659df844671ce1b5221ecb4295d7df.png?temp_url_sig=e376cbbf5d745690d3f59adc3fb974cfffd20c84&temp_url_expires=4099667400&extra")
        if (product.photo?.url != null) {
            photo = Product.Photo(product.photo.url)
        }
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
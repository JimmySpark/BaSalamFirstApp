package mohagheghi.mahdi.basalamfirstapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import mohagheghi.mahdi.basalamfirstapp.data.api.ApiService
import mohagheghi.mahdi.basalamfirstapp.data.local.dao.ProductDao
import mohagheghi.mahdi.basalamfirstapp.data.local.entity.Product
import mohagheghi.mahdi.basalamfirstapp.data.remote.model.Products
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRepository(
    private val productDao: ProductDao,
    private val api: ApiService
) {

    fun getProducts(): LiveData<List<Product>> {
        api.getProducts().enqueue(object : Callback<Products> {
            override fun onResponse(call: Call<Products>, response: Response<Products>) {
                productDao.deleteAll()
                productDao.addAll(response.body()?.data?.productSearch?.products!!)
            }

            override fun onFailure(call: Call<Products>, t: Throwable) {
                Log.e("get_products_api", t.message!!)
            }
        })

        return productDao.getAll()
    }
}
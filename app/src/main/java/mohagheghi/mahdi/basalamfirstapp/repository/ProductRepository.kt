package mohagheghi.mahdi.basalamfirstapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import mohagheghi.mahdi.basalamfirstapp.data.api.ApiService
import mohagheghi.mahdi.basalamfirstapp.data.local.dao.ProductDao
import mohagheghi.mahdi.basalamfirstapp.data.local.entity.Product
import mohagheghi.mahdi.basalamfirstapp.data.remote.model.Products
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor

class ProductRepository(
    private val productDao: ProductDao,
    private val api: ApiService,
    private val executor: Executor,
    val isLoading: MutableLiveData<Boolean>
) {

    fun getProducts(): LiveData<List<Product>> {
//        Log.i("test_", "getting products")

        executor.execute{
            if (productDao.getCount() == 0){
                isLoading.postValue(true)
            }
        }

        api.getProducts().enqueue(object : Callback<Products> {
            override fun onResponse(call: Call<Products>, response: Response<Products>) {
                isLoading.value = false
                executor.execute {
                    productDao.deleteAll()
                    productDao.addAll(response.body()?.data?.productSearch?.products!!)
                }
            }

            override fun onFailure(call: Call<Products>, t: Throwable) {
                isLoading.value = false
                Log.e("get_products_api", t.message!!)
            }
        })

        return productDao.getAll()
    }
}
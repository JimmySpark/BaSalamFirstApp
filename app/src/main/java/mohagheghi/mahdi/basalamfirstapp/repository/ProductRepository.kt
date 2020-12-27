package mohagheghi.mahdi.basalamfirstapp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import mohagheghi.mahdi.basalamfirstapp.data.api.ApiClient
import mohagheghi.mahdi.basalamfirstapp.helper.NetworkResponseCallback
import mohagheghi.mahdi.basalamfirstapp.data.model.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRepository {

    private lateinit var callback: NetworkResponseCallback
    private var products: MutableLiveData<List<Product.Products>> =
        MutableLiveData<List<Product.Products>>().apply { value = emptyList() }

    companion object {
        private var instance: ProductRepository? = null
        fun getInstance(): ProductRepository {
            if (instance == null) {
                synchronized(this) {
                    instance = ProductRepository()
                }
            }
            return instance!!
        }
    }

    private lateinit var call: Call<Product>

    fun getProducts(networkResponseCallback: NetworkResponseCallback): MutableLiveData<List<Product.Products>> {
        callback = networkResponseCallback
        if (products.value!!.isNotEmpty()) {
            callback.onNetworkRequestSuccess()
            return products
        }
        call = ApiClient.getInstance().getApiService().getProducts()
        call.enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                products.value = response.body()?.data?.productSearch?.products
                callback.onNetworkRequestSuccess()
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                products.value = emptyList()
                callback.onNetworkRequestFailure(t)
                Log.e("get_products", t.message!!)
            }

        })
        return products
    }
}
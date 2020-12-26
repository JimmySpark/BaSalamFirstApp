package mohagheghi.mahdi.basalamfirstapp

import android.util.Log
import androidx.lifecycle.MutableLiveData
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

    fun getProducts(networkResponseCallback: NetworkResponseCallback): MutableLiveData<List<Product.Products>>{
        callback = networkResponseCallback
        if (products.value!!.isEmpty()){
            callback.onNetworkRequestSuccess()
            return products
        }
        call = RestClient.getInstance().getApiService().getProducts()
        call.enqueue(object : Callback<Product>{
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                products.value = response.body()?.data?.productSearch?.products
                callback.onNetworkRequestSuccess()
                Log.i("get_products", "Success")
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                products.value = emptyList()
                callback.onNetworkRequestFailure(t)
                Log.i("get_products", "Fail")
                Log.e("get_products", t.message!!)
            }

        })
        return products
    }
}
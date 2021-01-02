package mohagheghi.mahdi.basalamfirstapp.repository

import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.google.gson.JsonObject
import mohagheghi.mahdi.basalamfirstapp.data.remote.response.ErrorResponseServer
import mohagheghi.mahdi.basalamfirstapp.data.remote.response.ErrorResponseSize
import mohagheghi.mahdi.basalamfirstapp.util.ResponseState
import mohagheghi.mahdi.basalamfirstapp.data.remote.response.SuccessfulResponse
import mohagheghi.mahdi.basalamfirstapp.data.api.ApiService
import mohagheghi.mahdi.basalamfirstapp.data.local.dao.ProductDao
import mohagheghi.mahdi.basalamfirstapp.data.local.entity.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor

class ProductRepository(
    private val productDao: ProductDao,
    private val api: ApiService,
    private val executor: Executor
) {

    fun getProducts(responseState: ResponseState): LiveData<List<Product>> {
//        Log.i("test_", "getting products")

        executor.execute {
            responseState.onShowLoading(productDao.getCount() == 0)
        }

        api.getProducts().enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                responseState.onHideLoading()
                if (response.isSuccessful) {
                    val sResponse = Gson().fromJson(response.body(), SuccessfulResponse::class.java)
                    if (sResponse.data != null) {
                        val products = sResponse.data.productSearch.products
//                        products = emptyList()
                        responseState.onEmptyList(products.isEmpty())
                        executor.execute {
                            productDao.deleteAll()
                            productDao.addAll(products)
                        }
                    } else {
                        val eResponse = Gson().fromJson(response.body(), ErrorResponseServer::class.java)
                        val errorServerMessage = eResponse.errors[0].messages.message
                        if (errorServerMessage != null) {
                            responseState.onError(500, errorServerMessage[0])
                        } else {
                            val eResponse = Gson().fromJson(response.body(), ErrorResponseSize::class.java)
                            responseState.onError(500, eResponse.errors[0].messages.size[0])
                        }
                    }
                } else {
                    responseState.onError(response.code(), response.message())
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                responseState.onHideLoading()
                responseState.onFailure(t)
            }
        })

        return productDao.getAll()
    }
}
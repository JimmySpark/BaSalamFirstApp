package mohagheghi.mahdi.basalamfirstapp.view.repository

import com.google.gson.Gson
import com.google.gson.JsonObject
import mohagheghi.mahdi.basalamfirstapp.data.api.ApiService
import mohagheghi.mahdi.basalamfirstapp.data.local.dao.ProductDao
import mohagheghi.mahdi.basalamfirstapp.data.model.ErrorResponse
import mohagheghi.mahdi.basalamfirstapp.data.model.SuccessfulResponse
import mohagheghi.mahdi.basalamfirstapp.view.util.ResponseState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor

class ProductRepository(
    private val productDao: ProductDao,
    private val api: ApiService,
    private val executor: Executor
) {

    fun getProducts(responseState: ResponseState): ResponseType {
//        Log.i("test_", "getProducts in Repo")
        api.getProducts().enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    val res = Gson().fromJson(response.body(), SuccessfulResponse::class.java)
                    if (res.data != null) {
                        val products = res.data.productSearch.products
//                        products = emptyList()
                        executor.execute {
                            productDao.deleteAll()
                            productDao.addAll(products)
                        }
                        if (products.isNotEmpty())
                            responseState.onResponse(ResponseType.Success(productDao.getAll()))
                        else
                            responseState.onResponse(ResponseType.EmptyList)
                    } else {
                        val res = Gson().fromJson(response.body(), ErrorResponse::class.java)
                        val errorMessage = res.errors[0].messages.message[0]
                        responseState.onResponse(ResponseType.Error(500, errorMessage))
                    }
                } else {
                    responseState.onResponse(ResponseType.Error(500, "خطا! لطفا مجددا تلاش کنید"))
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                responseState.onResponse(ResponseType.Error(501, t.message!!))
            }
        })

        return ResponseType.Success(productDao.getAll())
    }
}
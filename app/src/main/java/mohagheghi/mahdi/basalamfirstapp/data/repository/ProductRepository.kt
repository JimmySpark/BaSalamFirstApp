package mohagheghi.mahdi.basalamfirstapp.data.repository

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import mohagheghi.mahdi.basalamfirstapp.GetProductsQuery
import mohagheghi.mahdi.basalamfirstapp.data.local.AppDatabase
import mohagheghi.mahdi.basalamfirstapp.data.util.ProductMapper
import mohagheghi.mahdi.basalamfirstapp.data.util.ResponseType
import mohagheghi.mahdi.basalamfirstapp.data.util.ThreadExecutor
import mohagheghi.mahdi.basalamfirstapp.view.util.ResponseState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(
    private val db: AppDatabase,
    private val apollo: ApolloClient,
    private val executor: ThreadExecutor,
    private val size: Int
) {

    fun getProducts(responseState: ResponseState): ResponseType {
        apollo.query(GetProductsQuery(size)).watcher().enqueueAndWatch(object :
            ApolloCall.Callback<GetProductsQuery.Data>() {
            override fun onResponse(response: Response<GetProductsQuery.Data>) {
                if (response.data != null) {
                    val products = ProductMapper(response.data!!).map()
                    executor.execute {
                        db.productDao().deleteAll()
                        db.productDao().addAll(products)
                    }
                    if (products.isNotEmpty())
                        responseState.onResponse(ResponseType.Success(db.productDao().getAll()))
                    else
                        responseState.onResponse(ResponseType.EmptyList)
                } else {
                    responseState
                        .onResponse(
                            ResponseType.Error(
                                500,
                                "خطایی در سمت سرور رخ داده است!"
                            )
                        )
                }
            }

            override fun onFailure(e: ApolloException) {
                responseState.onResponse(ResponseType.Error(501, e.message!!))
            }

        })
        return ResponseType.Success(db.productDao().getAll())
    }
}
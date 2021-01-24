package mohagheghi.mahdi.data.repository

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import mohagheghi.mahdi.basalamfirstapp.GetProductsQuery
import mohagheghi.mahdi.data.db.ProductDao
import mohagheghi.mahdi.data.mapper.CacheMapper
import mohagheghi.mahdi.data.mapper.NetworkMapper
import mohagheghi.mahdi.data.mapper.NetworkToCacheMapper
import mohagheghi.mahdi.domain.entity.Product
import mohagheghi.mahdi.domain.repository.ProductRepository
import mohagheghi.mahdi.domain.util.ResponseType
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productDao: ProductDao,
    private val api: ApolloClient,
    private val cacheMapper: CacheMapper,
    private val networkMapper: NetworkMapper,
    private val networkToCacheMapper: NetworkToCacheMapper
) : ProductRepository {

    override suspend fun getProducts(): Flow<ResponseType<Flow<List<Product>>>> {
        return flow {
            emit(ResponseType.Success(productDao.getAll().map { cacheMapper.map(it) }))

            try {
                val response = api.query(GetProductsQuery(20)).await()
                val data = response.data
                if (data != null) {
                    productDao.deleteAll()
                    productDao.addAll(networkToCacheMapper.map(data))
                    val products = flow { emit(networkMapper.map(data)) }
                    products.collect {
                        if (it.isEmpty()) {
                            emit(ResponseType.EmptyList<Flow<List<Product>>>())
                        }
                    }
                } else {
                    emit(ResponseType.Error.ResponseError<Flow<List<Product>>>())
                }
            } catch (e: ApolloException) {
                emit(ResponseType.Error.NetworkError<Flow<List<Product>>>())
            }
        }
    }
}
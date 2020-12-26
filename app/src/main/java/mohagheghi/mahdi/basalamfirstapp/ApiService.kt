package mohagheghi.mahdi.basalamfirstapp

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("user")
    fun getProducts(@Field("query") query: String =
                        "{productSearch(size: 20) {products {id name photo(size: LARGE) { url } vendor { name } weight price rating { rating count: signals } } } }"
    ) : Call<Product>
}
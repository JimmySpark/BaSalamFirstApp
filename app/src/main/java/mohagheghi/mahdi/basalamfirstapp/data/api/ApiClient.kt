package mohagheghi.mahdi.basalamfirstapp.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient private constructor() {
    companion object {
        private const val BASE_URL = "https://api.basalam.com/api/"
        private lateinit var apiService: ApiService
        private var instance: ApiClient? = null
        fun getInstance(): ApiClient {
            if (instance == null) {
                synchronized(this) {
                    instance = ApiClient()
                }
            }
            return instance!!
        }
    }

    init {
        val okHttpClient = OkHttpClient().newBuilder().connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build()
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        apiService = retrofit.create(ApiService::class.java)
    }

    fun getApiService() = apiService
}
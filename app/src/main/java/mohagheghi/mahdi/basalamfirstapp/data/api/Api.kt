package mohagheghi.mahdi.basalamfirstapp.data.api

import com.apollographql.apollo.ApolloClient

class Api private constructor() {
    companion object {
        private const val SERVER_URL = "https://api.basalam.com/api/user"
        private lateinit var apolloClient: ApolloClient
        private var instance: Api? = null
        fun getInstance(): Api {
            if (instance == null) {
                synchronized(this) {
                    instance = Api()
                }
            }
            return instance!!
        }
    }

    init {
        apolloClient = ApolloClient.builder()
            .serverUrl(SERVER_URL)
            .build()
    }

    fun getApollo() = apolloClient
}
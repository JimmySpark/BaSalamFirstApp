package mohagheghi.mahdi.basalamfirstapp.di.module

import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApiModule {
    private val serverUrl = "https://api.basalam.com/api/user"

    @Provides
    fun provideApi(): ApolloClient {
        return ApolloClient.builder()
            .serverUrl(serverUrl)
            .build()
    }
}
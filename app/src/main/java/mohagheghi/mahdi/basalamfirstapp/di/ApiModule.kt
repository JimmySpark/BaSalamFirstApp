package mohagheghi.mahdi.basalamfirstapp.di

import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object ApiModule {
    private const val SERVER_URL = "https://api.basalam.com/api/user"

    @Provides
    @Singleton
    fun provideApi(): ApolloClient {
        return ApolloClient.builder()
            .serverUrl(SERVER_URL)
            .build()
    }
}
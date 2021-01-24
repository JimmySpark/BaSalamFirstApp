package mohagheghi.mahdi.basalamfirstapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import mohagheghi.mahdi.data.repository.ProductRepositoryImpl
import mohagheghi.mahdi.domain.repository.ProductRepository
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository
}
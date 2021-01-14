package mohagheghi.mahdi.basalamfirstapp.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import mohagheghi.mahdi.basalamfirstapp.data.repository.ProductRepository
import mohagheghi.mahdi.basalamfirstapp.di.module.ApiModule
import mohagheghi.mahdi.basalamfirstapp.di.module.DatabaseModule
import mohagheghi.mahdi.basalamfirstapp.di.module.ViewModelModule
import mohagheghi.mahdi.basalamfirstapp.view.ui.adapter.ProductsAdapter
import mohagheghi.mahdi.basalamfirstapp.view.viewmodel.ViewModelFactory
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, DatabaseModule::class, ViewModelModule::class])
interface AppComponent {

    fun getActivityComponentFactory(): ActivityComponent.Factory

    fun getRepository(): ProductRepository

    fun getViewModelFactory(): ViewModelFactory

    fun getRecyclerAdapter(): ProductsAdapter

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance @Named("db context") context: Context,
            @BindsInstance size: Int
        ): AppComponent
    }
}
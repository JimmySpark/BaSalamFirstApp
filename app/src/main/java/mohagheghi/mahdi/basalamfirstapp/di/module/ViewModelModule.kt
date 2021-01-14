package mohagheghi.mahdi.basalamfirstapp.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import mohagheghi.mahdi.basalamfirstapp.view.viewmodel.ProductViewModel
import mohagheghi.mahdi.basalamfirstapp.view.viewmodel.ViewModelFactory
import mohagheghi.mahdi.basalamfirstapp.view.viewmodel.ViewModelKey

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ProductViewModel::class)
    abstract fun productViewModel(productViewModel: ProductViewModel): ViewModel
}
package mohagheghi.mahdi.basalamfirstapp.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mohagheghi.mahdi.basalamfirstapp.data.repository.ProductRepository

class ProductViewModelFactory(private val repository: ProductRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProductViewModel(repository) as T
    }
}
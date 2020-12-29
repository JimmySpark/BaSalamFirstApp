package mohagheghi.mahdi.basalamfirstapp.viewmodel

import androidx.lifecycle.ViewModel
import mohagheghi.mahdi.basalamfirstapp.repository.ProductRepository

class ProductViewModel(val repository: ProductRepository) : ViewModel() {

    fun getProducts() = repository.getProducts()
}
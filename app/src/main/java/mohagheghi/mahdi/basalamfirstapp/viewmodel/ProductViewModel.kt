package mohagheghi.mahdi.basalamfirstapp.viewmodel

import androidx.lifecycle.ViewModel
import mohagheghi.mahdi.basalamfirstapp.repository.ProductRepository

class ProductViewModel(repository: ProductRepository) : ViewModel() {

    val products = repository.getProducts()
}
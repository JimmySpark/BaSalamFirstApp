package mohagheghi.mahdi.basalamfirstapp.view.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import mohagheghi.mahdi.basalamfirstapp.data.local.entity.Product

sealed class UiState(
    val products: LiveData<List<Product>> = MutableLiveData(emptyList()),
    val errorMessage: String = ""
) {
    object Loading : UiState()
    class Success(products: LiveData<List<Product>>) : UiState(products = products)
    object EmptyList : UiState()
    sealed class Error {
        class ResponseError(errorMessage: String) : UiState(errorMessage = errorMessage)
        object NetworkError : UiState()
    }
}

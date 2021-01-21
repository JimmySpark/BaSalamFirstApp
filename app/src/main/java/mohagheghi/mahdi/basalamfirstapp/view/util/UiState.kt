package mohagheghi.mahdi.basalamfirstapp.view.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import mohagheghi.mahdi.basalamfirstapp.data.local.entity.Product

sealed class UiState(
    val products: LiveData<List<Product>> = MutableLiveData(),
    val errorMessage: String = ""
) {
    class Loading(products: LiveData<List<Product>>) : UiState(products = products)
    class Success(products: LiveData<List<Product>>) : UiState(products = products)
    object EmptyList : UiState()
    sealed class Error {
        class ResponseError(errorMessage: String) : UiState(errorMessage = errorMessage)
        object NetworkError : UiState()
    }
}

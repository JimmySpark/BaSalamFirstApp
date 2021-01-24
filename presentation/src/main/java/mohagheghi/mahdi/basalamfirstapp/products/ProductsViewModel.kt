package mohagheghi.mahdi.basalamfirstapp.products

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import mohagheghi.mahdi.basalamfirstapp.util.UiState
import mohagheghi.mahdi.domain.entity.Product
import mohagheghi.mahdi.domain.usecase.GetProductsUseCase
import mohagheghi.mahdi.domain.util.ResponseType

class ProductsViewModel @ViewModelInject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    val data: MutableLiveData<UiState<LiveData<List<Product>>>> = MutableLiveData()

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            getProductsUseCase.getProducts().collect {
                when (it) {
                    is ResponseType.Success ->
                        data.value = UiState.Success(it.products?.asLiveData(coroutineContext)!!)
                    is ResponseType.EmptyList ->
                        data.value = UiState.EmptyList()
                    else -> {
                        var errorMessage = ""
                        if (it is ResponseType.Error.ResponseError)
                            errorMessage = "خطا در ارتباط با سرور!"
                        else if (it is ResponseType.Error.NetworkError)
                            errorMessage = "اتصال برقرار نشد! لطفا مجددا تلاش کنید"
                        data.value = UiState.Error(errorMessage)
                    }
                }
            }
        }
    }
}

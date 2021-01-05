package mohagheghi.mahdi.basalamfirstapp.view.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mohagheghi.mahdi.basalamfirstapp.data.repository.ProductRepository
import mohagheghi.mahdi.basalamfirstapp.data.repository.ResponseType
import mohagheghi.mahdi.basalamfirstapp.view.ui.UiState
import mohagheghi.mahdi.basalamfirstapp.view.util.ResponseState

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    val data = MutableLiveData<UiState>(UiState.Loading)

    init {
        loadData()
    }

    fun loadData() {
        repository.getProducts(object : ResponseState {
            override fun onResponse(response: ResponseType) {
                when (response) {
                    is ResponseType.Success -> data.postValue(UiState.Success(response.products))
                    is ResponseType.Error -> {
                        when (response.errorCode) {
                            500 -> data.postValue(UiState.Error.ResponseError(response.errorMessage))
                            501 -> {
                                if (response.errorMessage != null)
                                    Log.e("getProducts", response.errorMessage)
                                data.postValue(UiState.Error.NetworkError)
                            }
                        }
                    }
                    ResponseType.EmptyList -> data.postValue(UiState.EmptyList)
                }
            }
        })
    }
}
package mohagheghi.mahdi.basalamfirstapp.view.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mohagheghi.mahdi.basalamfirstapp.data.repository.ProductRepository
import mohagheghi.mahdi.basalamfirstapp.data.util.ResponseType
import mohagheghi.mahdi.basalamfirstapp.view.util.ResponseState
import mohagheghi.mahdi.basalamfirstapp.view.util.UiState

class ProductViewModel @ViewModelInject constructor(private val repository: ProductRepository) :
    ViewModel() {

    val data: MutableLiveData<UiState> = MutableLiveData()

    init {
        loadData()
    }

    fun loadData() {
        val response = repository.getProducts(20, object : ResponseState {
            override fun onResponse(response: ResponseType) {
                when (response) {
                    is ResponseType.Success ->
                        data.postValue(UiState.Success(response.products))
                    is ResponseType.Error ->
                        handleErrors(response.errorCode, response.errorMessage)
                    ResponseType.EmptyList ->
                        data.postValue(UiState.EmptyList)
                }
            }
        })

        data.postValue(UiState.Loading(response.products))
    }

    private fun handleErrors(errorCode: Int, errorMessage: String?) {
        when (errorCode) {
            500 -> data.postValue(UiState.Error.ResponseError(errorMessage!!))
            501 -> {
                if (errorMessage != null)
                    Log.e("getProducts", errorMessage)
                data.postValue(UiState.Error.NetworkError)
            }
        }
    }
}
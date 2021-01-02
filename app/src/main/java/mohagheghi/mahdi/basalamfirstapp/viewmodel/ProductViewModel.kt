package mohagheghi.mahdi.basalamfirstapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mohagheghi.mahdi.basalamfirstapp.util.ResponseState
import mohagheghi.mahdi.basalamfirstapp.repository.ProductRepository

class ProductViewModel(val repository: ProductRepository) : ViewModel() {

    val onLoading = MutableLiveData<Boolean>()
    val onError = MutableLiveData<Pair<Int, String>>()
    val onFailure = MutableLiveData<String?>()
    val onEmptyList = MutableLiveData<Boolean>()

    var products = repository.getProducts(object : ResponseState {
        override fun onShowLoading(isLoading: Boolean) {
            if (isLoading)
                onLoading.postValue(true)
        }

        override fun onHideLoading() {
            onLoading.value = false
        }

        override fun onError(errorCode: Int, message: String) {
            onError.value = Pair(errorCode, message)
        }

        override fun onFailure(t: Throwable) {
            onFailure.value = t.message
        }

        override fun onEmptyList(isEmpty: Boolean) {
            onEmptyList.value = isEmpty
        }
    })

    fun loadData(){
        products = repository.getProducts(object : ResponseState {
            override fun onShowLoading(isLoading: Boolean) {
                if (isLoading)
                    onLoading.postValue(true)
            }

            override fun onHideLoading() {
                onLoading.value = false
            }

            override fun onError(errorCode: Int, message: String) {
                onError.value = Pair(errorCode, message)
            }

            override fun onFailure(t: Throwable) {
                onFailure.value = t.message
            }

            override fun onEmptyList(isEmpty: Boolean) {
                onEmptyList.value = isEmpty
            }
        })
    }
}
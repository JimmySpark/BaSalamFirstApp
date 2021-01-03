package mohagheghi.mahdi.basalamfirstapp.view.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import mohagheghi.mahdi.basalamfirstapp.data.local.entity.Product

sealed class ResponseType(
    val products: LiveData<List<Product>> = MutableLiveData(emptyList()),
    val errorCode: Int = 0,
    val errorMessage: String = ""
) {
    class Success(products: LiveData<List<Product>>) : ResponseType(products)
    class Error(errorCode: Int, errorMessage: String) :
        ResponseType(errorCode = errorCode, errorMessage = errorMessage)

    object EmptyList : ResponseType()
}

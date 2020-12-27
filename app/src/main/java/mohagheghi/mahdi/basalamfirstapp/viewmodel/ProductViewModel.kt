package mohagheghi.mahdi.basalamfirstapp.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mohagheghi.mahdi.basalamfirstapp.helper.NetworkResponseCallback
import mohagheghi.mahdi.basalamfirstapp.data.model.Product
import mohagheghi.mahdi.basalamfirstapp.repository.ProductRepository
import mohagheghi.mahdi.basalamfirstapp.util.Loading

class ProductViewModel : ViewModel() {

    private var products: MutableLiveData<List<Product.Products>> =
        MutableLiveData<List<Product.Products>>().apply { value = emptyList() }

    fun getProducts(context: Context): MutableLiveData<List<Product.Products>> {
        val loading = Loading(context)
        loading.show()
        products = ProductRepository.getInstance().getProducts(object : NetworkResponseCallback {
            override fun onNetworkRequestSuccess() {
                loading.hide()
            }

            override fun onNetworkRequestFailure(t: Throwable) {
                loading.hide()
                Toast.makeText(context, "خطا! لطفا مجددا تلاش کنید", Toast.LENGTH_SHORT).show()
            }
        })
        return products
    }
}
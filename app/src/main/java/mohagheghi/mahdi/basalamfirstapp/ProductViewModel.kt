package mohagheghi.mahdi.basalamfirstapp

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductViewModel : ViewModel() {

    private var products : MutableLiveData<List<Product.Products>> =
        MutableLiveData<List<Product.Products>>().apply { value = emptyList() }

    fun getProducts(context: Context) : MutableLiveData<List<Product.Products>>{
        products = ProductRepository.getInstance().getProducts(object : NetworkResponseCallback {
            override fun onNetworkRequestSuccess() {
                //on success
                Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show()
            }

            override fun onNetworkRequestFailure(t: Throwable) {
                //on failure
                Toast.makeText(context, "Fail!", Toast.LENGTH_SHORT).show()
            }
        })
        return products
    }
}
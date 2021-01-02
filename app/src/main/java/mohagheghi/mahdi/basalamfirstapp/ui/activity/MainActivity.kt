package mohagheghi.mahdi.basalamfirstapp.ui.activity

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import mohagheghi.mahdi.basalamfirstapp.util.ThreadExecutor
import mohagheghi.mahdi.basalamfirstapp.data.api.ApiClient
import mohagheghi.mahdi.basalamfirstapp.data.local.AppDatabase
import mohagheghi.mahdi.basalamfirstapp.databinding.ActivityMainBinding
import mohagheghi.mahdi.basalamfirstapp.repository.ProductRepository
import mohagheghi.mahdi.basalamfirstapp.ui.adapter.ProductsAdapter
import mohagheghi.mahdi.basalamfirstapp.util.Loading
import mohagheghi.mahdi.basalamfirstapp.viewmodel.ProductViewModel
import mohagheghi.mahdi.basalamfirstapp.viewmodel.ProductViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ProductViewModel
    private lateinit var loading: Loading

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productAdapter = ProductsAdapter()

        binding.apply {
            recyclerProducts.apply {
                adapter = productAdapter
                layoutManager = GridLayoutManager(this@MainActivity, 2)
                setHasFixedSize(true)
            }
        }

        val repository = ProductRepository(
            AppDatabase.getInstance(this).productDao(),
            ApiClient.getInstance().getApiService(),
            ThreadExecutor()
        )
        viewModel = ViewModelProvider(
            this,
            ProductViewModelFactory(repository)
        ).get(ProductViewModel::class.java)

        viewModel.products.observe(this, {
            productAdapter.submitList(it)
        })
        loading = Loading(this)
        viewModel.onLoading.observe(this, {
            if (it)
                loading.show()
            else
                loading.hide()
        })
        viewModel.onError.observe(this, {
            val (errorCode, errorMessage) = it
            if (errorCode > 0)
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        })
        viewModel.onFailure.observe(this, {
            if (it != null)
                Log.e("get_products", it)
            showConnectionErrorSnackBar()
        })
        viewModel.onEmptyList.observe(this, {
            binding.txtNoProduct.isVisible = it
        })
    }


    fun showConnectionErrorSnackBar() {
        val snackBar = Snackbar.make(
            binding.root,
            "ارتباط برقرار نشد! لطفا مجددا تلاش کنید",
            Snackbar.LENGTH_INDEFINITE
        )
        snackBar.setAction("تلاش مجدد") { viewModel.loadData() }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            snackBar.view.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
        }
        snackBar.setTextColor(Color.BLACK)
        snackBar.setActionTextColor(Color.RED)
        snackBar.show()
    }
}
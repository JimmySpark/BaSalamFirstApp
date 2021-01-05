package mohagheghi.mahdi.basalamfirstapp.view.ui.activity

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import mohagheghi.mahdi.basalamfirstapp.data.api.Api
import mohagheghi.mahdi.basalamfirstapp.data.local.AppDatabase
import mohagheghi.mahdi.basalamfirstapp.data.repository.ProductRepository
import mohagheghi.mahdi.basalamfirstapp.data.util.ThreadExecutor
import mohagheghi.mahdi.basalamfirstapp.databinding.ActivityMainBinding
import mohagheghi.mahdi.basalamfirstapp.view.ui.UiState
import mohagheghi.mahdi.basalamfirstapp.view.ui.adapter.ProductsAdapter
import mohagheghi.mahdi.basalamfirstapp.view.util.Loading
import mohagheghi.mahdi.basalamfirstapp.view.viewmodel.ProductViewModel
import mohagheghi.mahdi.basalamfirstapp.view.viewmodel.ProductViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ProductViewModel
    private lateinit var productAdapter: ProductsAdapter
    private lateinit var loading: Loading

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productAdapter = ProductsAdapter()
        binding.apply {
            recyclerProducts.apply {
                adapter = productAdapter
                layoutManager = GridLayoutManager(this@MainActivity, 2)
                setHasFixedSize(true)
            }
        }

        val repository = ProductRepository(
            AppDatabase.getInstance(this).productDao(),
            Api.getInstance().getApollo(),
            ThreadExecutor()
        )
        viewModel = ViewModelProvider(this, ProductViewModelFactory(repository))
            .get(ProductViewModel::class.java)

        loading = Loading(this)

        initObservers()
    }

    private fun initObservers() {
        viewModel.data.observe(this, {
            when (it) {
                is UiState.Loading -> loading.show()
                is UiState.Success -> {
                    loading.hide()
                    it.products.observe(this, {
                        productAdapter.submitList(it)
                    })
                }
                is UiState.EmptyList -> {
                    loading.hide()
                    binding.txtNoProduct.isVisible = true
                }
                is UiState.Error.ResponseError -> {
                    loading.hide()
                    Toast.makeText(this, it.errorMessage, Toast.LENGTH_SHORT).show()
                }
                is UiState.Error.NetworkError -> {
                    loading.hide()
                    showConnectionErrorSnackBar()
                }
            }
        })
    }

    private fun showConnectionErrorSnackBar() {
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
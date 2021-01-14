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
import mohagheghi.mahdi.basalamfirstapp.App
import mohagheghi.mahdi.basalamfirstapp.databinding.ActivityMainBinding
import mohagheghi.mahdi.basalamfirstapp.di.component.ActivityComponent
import mohagheghi.mahdi.basalamfirstapp.di.component.AppComponent
import mohagheghi.mahdi.basalamfirstapp.view.ui.adapter.ProductsAdapter
import mohagheghi.mahdi.basalamfirstapp.view.util.Loading
import mohagheghi.mahdi.basalamfirstapp.view.util.UiState
import mohagheghi.mahdi.basalamfirstapp.view.viewmodel.ProductViewModel
import mohagheghi.mahdi.basalamfirstapp.view.viewmodel.ViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ProductViewModel
    private lateinit var appComponent: AppComponent
    private lateinit var activityComponent: ActivityComponent

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var productAdapter: ProductsAdapter
    @Inject
    lateinit var loading: Loading

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appComponent = (application as App).getComponent()
        activityComponent = appComponent.getActivityComponentFactory().create(this)
        activityComponent.inject(this)

        initRecyclerView()

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ProductViewModel::class.java)

        initObservers()
    }

    private fun initObservers() {
        viewModel.data.observe(this, {
            it.products.observe(this, { productList ->
                productAdapter.submitList(productList)
            })
            when (it) {
                is UiState.Loading -> loading.show()
                is UiState.Success -> loading.hide()
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

    private fun initRecyclerView() {
        binding.recyclerProducts.apply {
            adapter = productAdapter
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            setHasFixedSize(true)
        }
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
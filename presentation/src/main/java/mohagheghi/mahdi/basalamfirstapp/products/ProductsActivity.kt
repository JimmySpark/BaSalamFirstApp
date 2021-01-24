package mohagheghi.mahdi.basalamfirstapp.products

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import mohagheghi.mahdi.basalamfirstapp.databinding.ActivityMainBinding
import mohagheghi.mahdi.basalamfirstapp.util.Loading
import mohagheghi.mahdi.basalamfirstapp.util.UiState
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class ProductsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: ProductsViewModel by viewModels()
    private var updateTimes by Delegates.notNull<Int>()

    @Inject
    lateinit var productAdapter: ProductsAdapter

    @Inject
    lateinit var loading: Loading

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        initObservers()
    }

    private fun initObservers() {
        viewModel.data.observe(this, {
            updateTimes = 0
            when (it) {
                is UiState.Success -> {
                    it.products?.observe(this, { productList ->
                        if (updateTimes == 0)
                            loading.isShow(productList.isEmpty())
                        productAdapter.submitList(productList)
                        binding.recyclerProducts.smoothScrollToPosition(0)
                    })
                }
                is UiState.EmptyList -> {
                    loading.isShow(false)
                    binding.txtNoProduct.isVisible = true
                }
                is UiState.Error -> {
                    loading.isShow(false)
                    showErrorSnackBar(it.errorMessage!!)
                }
            }
        })
    }

    private fun initRecyclerView() {
        binding.recyclerProducts.apply {
            adapter = productAdapter
            layoutManager = GridLayoutManager(this@ProductsActivity, 2)
            setHasFixedSize(true)
        }
    }

    private fun showErrorSnackBar(errorMessage: String) {
        val snackBar = Snackbar.make(
            binding.root,
            errorMessage,
            Snackbar.LENGTH_INDEFINITE
        )
        snackBar.setAction("تلاش مجدد") { viewModel.loadProducts() }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            snackBar.view.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
        }
        snackBar.setTextColor(Color.BLACK)
        snackBar.setActionTextColor(Color.RED)
        snackBar.show()
    }
}
package mohagheghi.mahdi.basalamfirstapp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import mohagheghi.mahdi.basalamfirstapp.ThreadExecutor
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
    private var isLoading: MutableLiveData<Boolean> = MutableLiveData(false)

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

        loading = Loading(this)
        isLoading.observe(this, {
            if (it)
                loading.show()
            else
                loading.hide()
        })

        val repository = ProductRepository(
            AppDatabase.getInstance(this).productDao(),
            ApiClient.getInstance().getApiService(),
            ThreadExecutor(),
            isLoading
        )
        viewModel = ViewModelProvider(
            this,
            ProductViewModelFactory(repository)
        ).get(ProductViewModel::class.java)
        viewModel.products.observe(this, {
            productAdapter.submitList(it)
        })
    }
}
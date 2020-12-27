package mohagheghi.mahdi.basalamfirstapp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import mohagheghi.mahdi.basalamfirstapp.viewmodel.ProductViewModel
import mohagheghi.mahdi.basalamfirstapp.ui.adapter.ProductsAdapter
import mohagheghi.mahdi.basalamfirstapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ProductViewModel

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

        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        viewModel.getProducts(this).observe(this, { products ->
            productAdapter.submitList(products)
        })
    }
}
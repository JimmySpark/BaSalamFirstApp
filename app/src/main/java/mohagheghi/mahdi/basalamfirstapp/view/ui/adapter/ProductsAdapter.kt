package mohagheghi.mahdi.basalamfirstapp.view.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import mohagheghi.mahdi.basalamfirstapp.R
import mohagheghi.mahdi.basalamfirstapp.data.local.entity.Product
import mohagheghi.mahdi.basalamfirstapp.databinding.ItemProductBinding

class ProductsAdapter :
    ListAdapter<Product, ProductsAdapter.ProductViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.title.text = product.name
            binding.vendor.text = "غرفه: ${product.vendor.name}"
            binding.weight.text = "${product.weight} گرم"
            binding.price.text = String.format("%,3d تومان", product.price)
            binding.rating.text = product.rating.rating.toString()
            binding.ratingCount.text = "(${product.rating.count})"
            Picasso.get().load(product.photo.url).placeholder(R.drawable.ic_logo_basalam)
                .into(binding.image)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(
            oldItem: Product,
            newItem: Product
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Product,
            newItem: Product
        ): Boolean {
            return oldItem == newItem
        }

    }
}
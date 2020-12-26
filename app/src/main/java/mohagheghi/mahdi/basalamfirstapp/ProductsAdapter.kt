package mohagheghi.mahdi.basalamfirstapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import mohagheghi.mahdi.basalamfirstapp.databinding.ItemProductBinding

class ProductsAdapter :
    ListAdapter<Product.Products, ProductsAdapter.ProductViewHolder>(DiffCallback()) {

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

        fun bind(product: Product.Products) {
            binding.title.text = product.name
            binding.vendor.text = "غرفه: ${product.vendor.name}"
            binding.weight.text = "${product.weight} گرم"
            binding.price.text = "${product.price} تومان"
            binding.rating.text = product.rating.rating.toString()
            binding.ratingCount.text = product.rating.count.toString()
            Picasso.get().load(product.photo.url).placeholder(R.drawable.ic_logo_basalam)
                .into(binding.image)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Product.Products>() {
        override fun areItemsTheSame(
            oldItem: Product.Products,
            newItem: Product.Products
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Product.Products,
            newItem: Product.Products
        ): Boolean {
            return oldItem == newItem
        }

    }
}
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.wish.travel.data.Country
import com.wish.travel.databinding.ItemWishlistBinding

class WishlistViewHolder(private val binding: ItemWishlistBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: Country,
        position: Int,
        countrySelectedCallback: (String, String) -> Unit,
        removeAtPosition: (Int) -> Unit
    ) {
        binding.countryAvatarTextView.text = item.wishlistOrder.toString()
        binding.countryTextView.text = item.name
        binding.continentTextView.text = item.region

        binding.root.setOnClickListener {
            countrySelectedCallback(item.code, item.name)
        }

        binding.wishlistAdd.setOnClickListener { removeAtPosition(position) }
    }
}
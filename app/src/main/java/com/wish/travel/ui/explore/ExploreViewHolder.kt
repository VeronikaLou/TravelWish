import android.graphics.Color
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.wish.travel.data.Country
import com.wish.travel.databinding.ItemWishlistBinding

class ExploreViewHolder(private val binding: ItemWishlistBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        item: Country,
        position: Int,
        countrySelectedCallback: (String, String) -> Unit,
        addCountryToWishlist: (position: Int) -> Unit
    ) {
        binding.countryAvatarTextView.text = item.name[0].toString()
        binding.countryTextView.text = item.name
        binding.continentTextView.text = item.region

        if (item.wishlistOrder > 0) {
            binding.wishlistAdd.setBackgroundColor(Color.MAGENTA)
        } else {
            binding.wishlistAdd.setBackgroundColor(Color.WHITE)
        }

        binding.wishlistAdd.setOnClickListener { view ->
            addCountryToWishlist(position)
        }

        binding.root.setOnClickListener {
            countrySelectedCallback(item.code, item.name)
        }
    }

}
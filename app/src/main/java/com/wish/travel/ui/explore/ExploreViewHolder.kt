package com.wish.travel.ui.explore

import androidx.recyclerview.widget.RecyclerView
import com.wish.travel.data.Country
import com.wish.travel.databinding.ItemWishlistBinding

class ExploreViewHolder(private val binding: ItemWishlistBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Country) {
        binding.countryAvatarTextView.text = item.wishlistOrder.toString()
        binding.countryTextView.text = item.name
        binding.continentTextView.text = item.region
    }
}
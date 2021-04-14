package com.wish.travel.ui.wishlist

import androidx.recyclerview.widget.RecyclerView
import com.wish.travel.data.Country

class WishlistViewHolder(private val binding: ItemWishlistBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Country) {
        binding.avatarTextView.text = item.name.first().toString()
        binding.countryTextView.text = item.name
        binding.continentTextView.text = item.continent
    }
}
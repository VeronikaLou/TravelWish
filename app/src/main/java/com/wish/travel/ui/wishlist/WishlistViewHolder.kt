package com.wish.travel.ui.wishlist

import androidx.recyclerview.widget.RecyclerView
import com.wish.travel.data.Country
import com.wish.travel.databinding.ItemWishlistBinding

class WishlistViewHolder(private val binding: ItemWishlistBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Country, countrySelectedCallback: (String) -> Unit) {
        binding.countryAvatarTextView.text = item.wishlistOrder.toString()
        binding.countryTextView.text = item.name
        binding.continentTextView.text = item.region

        binding.root.setOnClickListener {
            countrySelectedCallback(item.code)
        }
    }
}
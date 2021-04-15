package com.wish.travel.ui.wishlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wish.travel.data.Country
import com.wish.travel.databinding.ItemWishlistBinding

class WishlistAdapter(private var items: List<Country> = emptyList()): RecyclerView.Adapter<WishlistViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistViewHolder =
        WishlistViewHolder(ItemWishlistBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    fun submistList(newItems: List<Country>){
        this.items = newItems
        items = newItems
        notifyDataSetChanged()
    }
}
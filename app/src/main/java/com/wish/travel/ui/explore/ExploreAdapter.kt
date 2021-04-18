package com.wish.travel.ui.explore

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wish.travel.data.Country
import com.wish.travel.databinding.ItemWishlistBinding

class ExploreAdapter(private var items: List<Country> = emptyList()): RecyclerView.Adapter<ExploreViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreViewHolder =
            ExploreViewHolder(ItemWishlistBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ExploreViewHolder, position: Int) {
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
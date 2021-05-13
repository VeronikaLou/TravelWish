package com.wish.travel.ui.explore

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.wish.travel.Communicator
import com.wish.travel.R
import com.wish.travel.data.Country
import com.wish.travel.databinding.ItemWishlistBinding

class ExploreAdapter(
        private var communicator: Communicator,
        private var items: List<Country> = emptyList()
) : RecyclerView.Adapter<ExploreAdapter.ExploreViewHolder>() {

    inner class ExploreViewHolder(private val binding: ItemWishlistBinding, private val communicator: Communicator) : RecyclerView.ViewHolder(binding.root) {

        val addToWishlistBtn = itemView.findViewById<ImageView>(R.id.wishlist_add);

        fun bind(item: Country, position: Int) {
            binding.countryAvatarTextView.text = item.name[0].toString()
            binding.countryTextView.text = item.name
            binding.continentTextView.text = item.region

            addToWishlistBtn.setOnClickListener { communicator.passWishlistCountriesIndexes(position) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreViewHolder {
        return ExploreViewHolder(ItemWishlistBinding.inflate(LayoutInflater.from(parent.context), parent, false), communicator)
    }

    override fun onBindViewHolder(holder: ExploreViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, position)
    }

    override fun getItemCount(): Int = items.size

    fun submistList(newItems: List<Country>) {
        this.items = newItems
        items = newItems
        notifyDataSetChanged()
    }
}
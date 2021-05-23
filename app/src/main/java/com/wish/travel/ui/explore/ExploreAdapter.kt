package com.wish.travel.ui.explore

import ExploreViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.wish.travel.Communicator
import com.wish.travel.R
import com.wish.travel.data.Country
import com.wish.travel.databinding.ItemWishlistBinding
import com.wish.travel.util.toast

class ExploreAdapter(
    private val activity: FragmentActivity?,
    private var communicator: Communicator,
    private var items: List<Country> = emptyList(),
    private val countrySelectedCallback: (String, String) -> Unit
) : RecyclerView.Adapter<ExploreViewHolder>() {

    private fun addCountryToWishlist(position: Int) {
        val country = items[position]
        val wishlistDB = communicator.getWishlistDB()
        var wishlistCountries = wishlistDB.allWishlistCountries
        if (!wishlistCountries.map { country -> country.name }.contains(country.name)) {
            country.wishlistOrder = wishlistCountries.size + 1
            wishlistDB.insertData(country)
            activity?.toast(country.name + " " + activity.getString(R.string.addedToWishlist))
        } else {
            wishlistDB.deleteWishlistCountry(country)
            wishlistCountries.mapIndexed { index, item -> item.wishlistOrder = index + 1 }
            wishlistDB.allWishlistCountries.forEachIndexed { index, country ->
                run {
                    country.wishlistOrder = index + 1
                    wishlistDB.updateWishlistCountry(
                        country
                    )
                }
            }
            activity?.toast(country.name + " " + activity.getString(R.string.removedFromWishlist))
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreViewHolder {
        return ExploreViewHolder(
            ItemWishlistBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ExploreViewHolder, position: Int) {
        val item = items[position]
        val wishlistItem = communicator.getWishlistDB().allWishlistCountries.find { c -> c.name == item.name }
        if(wishlistItem != null) {
            item.wishlistOrder = wishlistItem.wishlistOrder
        } else {
            item.wishlistOrder = 0
        }
        holder.bind(
            item,
            position,
            countrySelectedCallback,
            addCountryToWishlist = { position -> addCountryToWishlist(position) }
        )
    }

    override fun getItemCount(): Int = items.size

    fun submistList(newItems: List<Country>) {
        this.items = newItems
        items = newItems
        notifyDataSetChanged()
    }
}
package com.wish.travel.ui.wishlist

import WishlistViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.wish.travel.Communicator
import com.wish.travel.R
import com.wish.travel.data.Country
import com.wish.travel.databinding.ItemWishlistBinding
import com.wish.travel.util.toast

class WishlistAdapter(
    private val activity: FragmentActivity?,
    private var communicator: Communicator,
    private var items: ArrayList<Country> = communicator.getWishlistDB().allWishlistCountries,
    private val countrySelectedCallback: (String, String) -> Unit
) :
    RecyclerView.Adapter<WishlistViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistViewHolder =
        WishlistViewHolder(
            ItemWishlistBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) {
        val item = communicator.getWishlistDB().allWishlistCountries[position]
        holder.bind(
            item,
            position,
            countrySelectedCallback,
            removeAtPosition = { position -> removeItem(position) })
    }

    override fun getItemCount(): Int = items.size

    fun initList() {
        items = communicator.getWishlistDB().allWishlistCountries
    }

    private fun removeItem(position: Int) {
        val country = items[position]
        communicator.getWishlistDB().deleteWishlistCountry(country)
        reindexWishlistCountries()
        activity?.toast(country.name + " " + activity.getString(R.string.removedFromWishlist))
    }

    private fun reindexWishlistCountries() {
        val wishlistDB = communicator.getWishlistDB()
        val wishlistCountries = wishlistDB.allWishlistCountries
        wishlistCountries.mapIndexed { index, item -> item.wishlistOrder = index + 1 }
        wishlistCountries.forEach { country -> wishlistDB.updateWishlistCountry(country) }
        items = wishlistDB.allWishlistCountries
        notifyDataSetChanged()
    }
}
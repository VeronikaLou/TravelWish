package com.wish.travel.ui.wishlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.wish.travel.R
import com.wish.travel.data.Country
import com.wish.travel.databinding.FragmentWishlistBinding

class WishlistFragment: Fragment(R.layout.fragment_wishlist) {

    private lateinit var binding: FragmentWishlistBinding

    private var initCountries = listOf(
        Country("Argentina", "South America"),
        Country("Czechia", "Europe"),
        Country("China", "Asia"),
        Country("Germany", "Europe"),
        Country("Slovakia", "Europe"),
        Country("Slovenia", "Europe"),
        Country("Croatia", "Europe")
    )

    private val adapter: WishlistAdapter by lazy {
        WishlistAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWishlistBinding.bind(view)

        binding.wishlistRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.wishlistRecyclerView.adapter = adapter
        adapter.submistList(initCountries)
    }
}
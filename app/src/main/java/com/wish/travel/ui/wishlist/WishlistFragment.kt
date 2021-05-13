package com.wish.travel.ui.wishlist

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.wish.travel.R
import com.wish.travel.data.Country
import com.wish.travel.databinding.FragmentWishlistBinding
import com.wish.travel.ui.country.CountryActivity
import com.wish.travel.util.toast

class WishlistFragment : Fragment(R.layout.fragment_wishlist) {

    private lateinit var binding: FragmentWishlistBinding

    private val adapter: WishlistAdapter by lazy {
        WishlistAdapter { countryCode ->
            val intent = CountryActivity.newIntent(requireContext(), countryCode)
            startActivity(intent)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val wishlistCountries = arguments?.getSerializable("wishlist") as List<Country>

        binding = FragmentWishlistBinding.bind(view)
        binding.wishlistRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.wishlistRecyclerView.adapter = adapter
        adapter.submitList(wishlistCountries)
    }
}
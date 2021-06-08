package com.wish.travel.ui.wishlist

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.wish.travel.Communicator
import com.wish.travel.R
import com.wish.travel.data.Country
import com.wish.travel.databinding.FragmentWishlistBinding
import com.wish.travel.ui.country.CountryActivity
import com.wish.travel.util.toast

class WishlistFragment : Fragment(R.layout.fragment_wishlist) {

    private lateinit var binding: FragmentWishlistBinding

    private val adapter: WishlistAdapter by lazy {
        WishlistAdapter(
            activity,
            activity as Communicator,
            countrySelectedCallback = { countryCode, countryName ->
                val intent = CountryActivity.newIntent(requireContext(), countryCode, countryName)
                startActivity(intent)
            })
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWishlistBinding.bind(view)

        activity?.setTitle(R.string.wishlist)

        binding.wishlistRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.wishlistRecyclerView.adapter = adapter
        binding.emptyScreenText.text = "Nothing to see here :(\nAdd some countries to the wishlist."

        adapter.initList()

        if(adapter.itemCount == 0){
            binding.emptyScreenText.visibility = View.VISIBLE
            binding.wishlistRecyclerView.visibility = View.GONE
        } else {
            binding.emptyScreenText.visibility = View.GONE
            binding.wishlistRecyclerView.visibility = View.VISIBLE
        }
    }
}
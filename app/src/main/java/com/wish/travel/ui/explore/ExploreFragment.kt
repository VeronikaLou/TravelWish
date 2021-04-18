package com.wish.travel.ui.explore

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.wish.travel.R
import com.wish.travel.data.Country
import com.wish.travel.databinding.FragmentExploreBinding

class ExploreFragment: Fragment(R.layout.fragment_explore) {

    private lateinit var binding: FragmentExploreBinding

    private var initCountries = listOf(
            Country("Argentina", "South America", 1),
            Country("Czechia", "Europe", 2),
            Country("China", "Asia", 3),
            Country("Germany", "Europe", 4),
            Country("Slovakia", "Europe", 5),
            Country("Slovenia", "Europe", 6),
            Country("Croatia", "Europe", 7)
    )

    private val adapter: ExploreAdapter by lazy {
        ExploreAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentExploreBinding.bind(view)

        binding.exploreRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.exploreRecyclerView.adapter = adapter
        adapter.submistList(initCountries)
    }
}
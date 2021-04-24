package com.wish.travel.ui.explore

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.wish.travel.R
import com.wish.travel.data.Country
import com.wish.travel.databinding.FragmentExploreBinding
import com.wish.travel.repository.RestCountriesRepository
import com.wish.travel.util.toast

class ExploreFragment: Fragment(R.layout.fragment_explore) {

    private val restCountriesRepository = RestCountriesRepository()

    private lateinit var binding: FragmentExploreBinding

    private val adapter: ExploreAdapter by lazy {
        ExploreAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentExploreBinding.bind(view)

        binding.exploreRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.exploreRecyclerView.adapter = adapter
        // init list - all countries
        restCountriesRepository.getAllCountries(
            successCallback = { countries ->
                adapter.submistList(countries)
            },
            failureCallback = {
                adapter.submistList(emptyList())
                activity?.toast(R.string.explore_no_countries_found)
            })

        binding.searchButton.setOnClickListener {
            restCountriesRepository.getCountriesByName(binding.searchByNameTextField.editText?.text.toString(),
                    successCallback = { countries ->
                        adapter.submistList(countries)
                    },
                    failureCallback = {
                        adapter.submistList(emptyList())
                        activity?.toast(R.string.explore_no_countries_found)
                    })
        }
    }
}
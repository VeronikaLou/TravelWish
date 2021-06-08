package com.wish.travel.ui.explore

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.wish.travel.Communicator
import com.wish.travel.R
import com.wish.travel.data.Country
import com.wish.travel.databinding.FragmentExploreBinding
import com.wish.travel.repository.RestCountriesRepository
import com.wish.travel.ui.country.CountryActivity
import com.wish.travel.util.toast

class ExploreFragment: Fragment(R.layout.fragment_explore) {

    companion object {
        val regions = listOf("All", "Africa", "Americas", "Asia", "Europe", "Oceania")
    }

    private val restCountriesRepository = RestCountriesRepository()

    private lateinit var binding: FragmentExploreBinding

    private val adapter: ExploreAdapter by lazy {
        ExploreAdapter(activity, activity as Communicator, countrySelectedCallback = {
                countryCode, countryName ->
                    val intent = CountryActivity.newIntent(requireContext(), countryCode, countryName)
                    startActivity(intent)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentExploreBinding.bind(view)

        activity?.setTitle(R.string.explore)

        binding.exploreRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.exploreRecyclerView.adapter = adapter
        binding.progressBar.visibility = View.VISIBLE
        showAllCountries()

        binding.searchButton.setOnClickListener {
            searchCountries()
        }

        initRegionsSpinner()
    }

    private fun initRegionsSpinner() {
        val regionsAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, regions)
        binding.regionSpinner.adapter = regionsAdapter
    }

    private fun searchCountries() {
        binding.progressBar.visibility = View.VISIBLE
        val selectedRegionPosition = binding.regionSpinner.selectedItemPosition
        val searchName = binding.searchByNameInputField.editText?.text.toString()

        // no region selected
        if (selectedRegionPosition == 0) {
            if (searchName.isEmpty()) {
                showAllCountries()
            } else {
                showCountriesByName(searchName)
            }
        } else {
            if (searchName.isEmpty()) {
                showCountriesInRegion(regions[selectedRegionPosition])
            } else {
                showCountriesByNameAndRegion(searchName, regions[selectedRegionPosition])
            }
        }
    }

    private fun showCountriesByNameAndRegion(name: String, region: String) {
        restCountriesRepository.getCountriesByName(
            name,
            successCallback = { countries ->
                val filteredCountries = countries.filter { country -> country.region.equals(region) }
                if (filteredCountries.isEmpty()) {
                    noCountriesFound()
                }
                adapter.submistList(filteredCountries)
                binding.progressBar.visibility = View.GONE
            },
            failureCallback = {
                noCountriesFound()
                binding.progressBar.visibility = View.GONE
            })
    }

    private fun showCountriesByName(name: String) {
        restCountriesRepository.getCountriesByName(
            name,
            successCallback = { countries ->
                adapter.submistList(countries)
                binding.progressBar.visibility = View.GONE
            },
            failureCallback = {
                noCountriesFound()
                binding.progressBar.visibility = View.GONE
            })
    }

    private fun showCountriesInRegion(region: String) {
        restCountriesRepository.getCountriesByRegion(
            region,
            successCallback = { countries ->
                adapter.submistList(countries)
                binding.progressBar.visibility = View.GONE
            },
            failureCallback = {
                noCountriesFound()
                binding.progressBar.visibility = View.GONE
            })
    }

    private fun showAllCountries() {
        restCountriesRepository.getAllCountries(
            successCallback = { countries ->
                adapter.submistList(countries)
                binding.progressBar.visibility = View.GONE
            },
            failureCallback = {
                noCountriesFound()
                binding.progressBar.visibility = View.GONE
            })
    }

    private fun noCountriesFound() {
        adapter.submistList(emptyList())
        activity?.toast(R.string.explore_no_countries_found)
    }
}

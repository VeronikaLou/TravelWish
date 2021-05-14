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
        ExploreAdapter(activity as Communicator, countrySelectedCallback = {
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
        showAllCountries()

        binding.searchButton.setOnClickListener {
//            val name = binding.searchByNameInputField.editText?.text.toString()
//            showCountriesByName(name)
            searchCountries()
        }

        initRegionsSpinner()
    }

    private fun initRegionsSpinner() {
        val regionsAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, regions)
        binding.regionSpinner.adapter = regionsAdapter
//        binding.regionSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onNothingSelected(p0: AdapterView<*>?) {}
//
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
//                val selectedRegion = regions[position]
//                if (position == 0) {
//                    showAllCountries()
//                } else {
//                    showCountriesInRegion(selectedRegion)
//                }
//            }
//        }
    }

    private fun searchCountries() {
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
                (activity as Communicator).passCountries(ArrayList(filteredCountries))
            },
            failureCallback = {
                noCountriesFound()
            })
    }

    private fun showCountriesByName(name: String) {
        restCountriesRepository.getCountriesByName(
            name,
            successCallback = { countries ->
                adapter.submistList(countries)
                (activity as Communicator).passCountries(ArrayList(countries))
            },
            failureCallback = {
                noCountriesFound()
            })
    }

    private fun showCountriesInRegion(region: String) {
        restCountriesRepository.getCountriesByRegion(
            region,
            successCallback = { countries ->
                adapter.submistList(countries)
                (activity as Communicator).passCountries(ArrayList(countries))
            },
            failureCallback = {
                noCountriesFound()
            })
    }

    private fun showAllCountries() {
        restCountriesRepository.getAllCountries(
            successCallback = { countries ->
                adapter.submistList(countries)
                (activity as Communicator).passCountries(ArrayList(countries))
            },
            failureCallback = {
                noCountriesFound()
            })
    }

    private fun noCountriesFound() {
        adapter.submistList(emptyList())
        activity?.toast(R.string.explore_no_countries_found)
    }
}

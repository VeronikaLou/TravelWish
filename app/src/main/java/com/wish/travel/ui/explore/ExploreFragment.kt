package com.wish.travel.ui.explore

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.wish.travel.Communicator
import com.wish.travel.R
import com.wish.travel.databinding.FragmentExploreBinding
import com.wish.travel.repository.RestCountriesRepository
import com.wish.travel.ui.country.CountryActivity
import com.wish.travel.util.toast

class ExploreFragment: Fragment(R.layout.fragment_explore) {

    companion object {
        val regions = listOf("None", "Africa", "Americas", "Asia", "Europe", "Oceania")
    }

    private val restCountriesRepository = RestCountriesRepository()

    private lateinit var binding: FragmentExploreBinding

    private val adapter: ExploreAdapter by lazy {
        ExploreAdapter(activity as Communicator, countrySelectedCallback = {
                countryCode ->
                    val intent = CountryActivity.newIntent(requireContext(), countryCode)
                    startActivity(intent)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentExploreBinding.bind(view)

        binding.exploreRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.exploreRecyclerView.adapter = adapter
        showAllCountries()

        binding.searchButton.setOnClickListener {
            val name = binding.searchByNameTextField.editText?.text.toString()
            showCountriesByName(name)
        }

        initRegionsSpinner()
    }

    private fun initRegionsSpinner() {
        val regionsAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, regions)
        binding.regionSpinner.adapter = regionsAdapter
        binding.regionSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                val selectedRegion = regions[position]
                if (position == 0) {
                    showAllCountries()
                } else {
                    showCountriesInRegion(selectedRegion)
                }
            }
        }
    }

    private fun showCountriesByName(name: String) {
        restCountriesRepository.getCountriesByName(
            name,
            successCallback = { countries ->
                adapter.submistList(countries)
                (activity as Communicator).passCountries(ArrayList(countries))
            },
            failureCallback = {
                adapter.submistList(emptyList())
                activity?.toast(R.string.explore_no_countries_found)
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
                adapter.submistList(emptyList())
                activity?.toast(R.string.explore_no_countries_found)
            })
    }

    private fun showAllCountries() {
        restCountriesRepository.getAllCountries(
            successCallback = { countries ->
                adapter.submistList(countries)
                (activity as Communicator).passCountries(ArrayList(countries))
            },
            failureCallback = {
                adapter.submistList(emptyList())
                activity?.toast(R.string.explore_no_countries_found)
            })
    }
}

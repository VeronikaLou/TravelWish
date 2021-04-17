package com.wish.travel.ui.country

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.wish.travel.R
import com.wish.travel.data.Country
import com.wish.travel.databinding.FragmentCountryBinding

class CountryFragment() : Fragment(R.layout.fragment_country) {

    private lateinit var binding: FragmentCountryBinding

    companion object {
        private const val COUNTRY_ID = "country_id"

        fun newIntent(context: Context, countryId: String): Intent =
                Intent(context, CountryFragment::class.java)
                        .putExtra(COUNTRY_ID, countryId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCountryBinding.bind(view)

        val country = Country("Argentina", "South America", 1)

        binding.countryNameTextView.text = country.name
        binding.countryRegionTextView.text = country.region
        binding.countrySubregionTextView.text = country.subregion
        binding.countryNativeNameTextView.text = country.nativeName
        binding.countryCapitalTextView.text = country.capital
        binding.countryPopulationTextView.text = country.population.toString()
        binding.countryAreaTextView.text = country.area.toString()
        binding.countryTimezonesTextView.text = country.timezones.toString()
        binding.countryLanguagesTextView.text = country.languages.toString()
        binding.toolbar.title = country.name
    }
}
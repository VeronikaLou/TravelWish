package com.wish.travel.ui.country

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.wish.travel.R
import com.wish.travel.databinding.FragmentCountryBinding
import com.wish.travel.repository.RestCountriesRepository
import com.wish.travel.util.toStringWithoutBrackets

class CountryFragment : Fragment(R.layout.fragment_country) {

    private val restCountriesRepository = RestCountriesRepository()

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

        restCountriesRepository.getCountryByCode("arg", successCallback = { country ->
            GlideToVectorYou.justLoadImage(activity, Uri.parse(country.flagUrl), binding.countryFlagImageView)
            binding.countryNameTextView.text = country.name
            binding.countryRegionTextView.text = country.region
            binding.countrySubregionTextView.text = country.subregion
            binding.countryNativeNameTextView.text = country.nativeName
            binding.countryCapitalTextView.text = country.capital
            binding.countryPopulationTextView.text = country.population.toString()
            binding.countryAreaTextView.text = country.area?.toString() ?: ""
            binding.countryTimezonesTextView.text = country.timezones.toStringWithoutBrackets()
            binding.countryLanguagesTextView.text = country.languages.toStringWithoutBrackets()
            binding.countryCurrenciesTextView.text = country.currencies.map { it["code"] + " " + it["symbol"] + " - " + it["name"] }.toStringWithoutBrackets()
            binding.toolbar.title = country.name
        }, failureCallback = {})
    }
}
package com.wish.travel.ui.country

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.wish.travel.R
import com.wish.travel.data.Country
import com.wish.travel.databinding.ActivityCountryBinding
import com.wish.travel.repository.RestCountriesRepository
import com.wish.travel.util.toStringWithoutBrackets
import java.text.NumberFormat

class CountryActivity : AppCompatActivity() {

    private val restCountriesRepository = RestCountriesRepository()

    private val binding: ActivityCountryBinding by lazy {
        ActivityCountryBinding.inflate(layoutInflater)
    }

    companion object {
        private const val COUNTRY_ID = "country_id"
        private const val COUNTRY_NAME = "country_name"

        fun newIntent(context: Context, countryId: String, countryName: String): Intent =
                Intent(context, CountryActivity::class.java)
                        .putExtra(COUNTRY_ID, countryId)
                        .putExtra(COUNTRY_NAME, countryName)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.progressBar.visibility = View.VISIBLE

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setTitle(intent.getStringExtra(COUNTRY_NAME))

        restCountriesRepository.getCountryByCode(intent.getStringExtra(COUNTRY_ID)!!, successCallback = { country ->
            bindCountryInfoValues(country)
            bindCountryInfoLabels()
            binding.progressBar.visibility = View.GONE
        }, failureCallback = {})
    }

    private fun bindCountryInfoLabels() {
        binding.countryNameLabelTextView.text = getString(R.string.country_name)
        binding.countryRegionLabelTextView.text = getString(R.string.region)
        binding.countrySubregionLabelTextView.text = getString(R.string.subregion)
        binding.countryNativeNameLabelTextView.text = getString(R.string.native_name)
        binding.countryCapitalLabelTextView.text = getString(R.string.capital)
        binding.countryPopulationLabelTextView.text = getString(R.string.population)
        binding.countryAreaLabelTextView.text = getString(R.string.area)
        binding.countryTimezonesLabelTextView.text = getString(R.string.timezones)
        binding.countryLanguagesLabelTextView.text = getString(R.string.languages)
        binding.countryCurrenciesLabelTextView.text = getString(R.string.currencies)
    }

    private fun bindCountryInfoValues(country: Country) {
        GlideToVectorYou.justLoadImage(
            this,
            Uri.parse(country.flagUrl),
            binding.countryFlagImageView
        )

        binding.countryNameTextView.text = country.name
        binding.countryRegionTextView.text = country.region
        binding.countrySubregionTextView.text = country.subregion
        binding.countryNativeNameTextView.text = country.nativeName
        binding.countryCapitalTextView.text = country.capital
        binding.countryPopulationTextView.text = formattedNumber(country.population)
        if (country.area == null) {
            binding.countryAreaTextView.text = ""
        } else binding.countryAreaTextView.text = formattedNumber(country.area) + " km\u00B2"
        binding.countryTimezonesTextView.text = country.timezones.toStringWithoutBrackets()
        binding.countryLanguagesTextView.text = country.languages.toStringWithoutBrackets()
        binding.countryCurrenciesTextView.text =
            country.currencies.map { it["code"] + " " + it["symbol"] + " - " + it["name"] }
                .toStringWithoutBrackets()
    }

    private fun formattedNumber(number: Number) : String {
        return NumberFormat.getInstance().format(number)
    }
}
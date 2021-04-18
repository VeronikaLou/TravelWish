package com.wish.travel.repository

import android.util.Log
import com.wish.travel.data.Country
import com.wish.travel.webservice.RestCountriesApi
import com.wish.travel.webservice.RetrofitUtil
import com.wish.travel.webservice.response.CountryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestCountriesRepository(private val restCountriesApi: RestCountriesApi = RetrofitUtil.createRestCountriesApi()) {

    fun getCountryByCode(countryCode: String, successCallback: (Country) -> Unit, failureCallback: () -> Unit) {
        restCountriesApi.getCountryByCode(countryCode)
            .enqueue(object : Callback<CountryResponse> {

                override fun onResponse(call: Call<CountryResponse>, response: Response<CountryResponse>) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        val country = responseBody.mapToCountry()
                        successCallback(country)
                    } else {
                        failureCallback()
                    }
                }

                override fun onFailure(call: Call<CountryResponse>, t: Throwable) {
                    failureCallback()
                }
            })
    }

    fun getCountriesByName(name: String, successCallback: (List<Country>) -> Unit, failureCallback: () -> Unit) {
        Log.i("RestCountriesRepository", "getCountriesByName")
        restCountriesApi.getCountriesByName(name)
                .enqueue(object : Callback<List<CountryResponse>> {

                    override fun onResponse(call: Call<List<CountryResponse>>, response: Response<List<CountryResponse>>) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            val countries = responseBody.mapToCountriesList()
                            successCallback(countries)
                        } else {
                            failureCallback()
                        }
                    }

                    override fun onFailure(call: Call<List<CountryResponse>>, t: Throwable) {
                        failureCallback()
                    }
                })
    }

    private fun CountryResponse?.mapToCountry(): Country = Country(this!!.name, this.nativeName, this.region, this.subregion,
                    this.capital, this.population, this.area, this.timezones, this.languages.map { it["name"] ?: "" },
                    this.currencies, this.flag, 0)

    private fun List<CountryResponse>?.mapToCountriesList(): List<Country> =
            this?.map {
                countryResponse ->  countryResponse.mapToCountry()
            } ?: emptyList()
}
package com.wish.travel.webservice

import com.wish.travel.webservice.response.CountryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RestCountriesApi {

    @GET("alpha/{code}")
    fun getCountryByCode(
        @Path("code") countryCode: String
    ): Call<CountryResponse>

    @GET("name/{name}")
    fun getCountriesByName(
            @Path("name") name: String
    ): Call<List<CountryResponse>>
}
package com.wish.travel.webservice.response

data class CountryResponse(
    val name: String,
    val topLevelDomain: List<String>,
    val alpha2Code: String,
    val alpha3Code: String,
    val callingCodes: List<String>,
    val capital: String,
    val altSpellings: List<String>,
    val region: String,
    val subregion: String,
    val population: Number,
    val latlng: List<String>,
    val demonym: String,
    val area: Number,
    val gini: Number,
    val timezones: List<String>,
    val borders: List<String>,
    val nativeName: String,
    val numericCode: String,
    val currencies: List<Map<String, String>>,
    val languages: List<Map<String, String>>,
    val translations: Map<String, String>,
    val flag: String,
    val regionalBlocs: List<Map<String, Any>>,
    val cioc: String
)

data class ExchangeRatesResponse(
    val currency: String,
    val rates: Map<String, Double>
)
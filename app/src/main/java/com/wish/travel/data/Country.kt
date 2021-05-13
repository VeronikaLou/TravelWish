package com.wish.travel.data

import java.io.Serializable

data class Country(
        val name: String,
        val code: String,
        val nativeName: String,
        val region: String,
        val subregion: String,
        val capital: String,
        val population: Number,
        val area: Number?,
        val timezones: List<String>,
        val languages: List<String>,
        val currencies: List<Map<String, String>>,
        val flagUrl: String,
        var wishlistOrder: Number
) : Serializable {
        constructor(name: String, code: String, region: String, wishlistOrder: Number) :
                this(name, code, "", region, "", "", 0, 0,
                        listOf<String>(), listOf<String>(), listOf<Map<String, String>>(), "", wishlistOrder)
}
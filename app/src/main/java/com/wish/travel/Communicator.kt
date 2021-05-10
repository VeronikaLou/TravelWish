package com.wish.travel

import com.wish.travel.data.Country

interface Communicator {
    fun passWishlistCountriesIndexes(position:  Int)
    fun passCountries(countries: ArrayList<Country>)
}
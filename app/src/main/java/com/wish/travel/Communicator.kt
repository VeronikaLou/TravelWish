package com.wish.travel

import com.wish.travel.data.Country

interface Communicator {
    fun getWishlistDB(): DBHandler
}
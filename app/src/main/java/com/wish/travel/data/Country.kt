package com.wish.travel.data

import java.io.Serializable

data class Country(
        val name: String,
        val continent: String
) : Serializable
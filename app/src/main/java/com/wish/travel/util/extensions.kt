package com.wish.travel.util

fun List<String>.toStringWithoutBrackets() = toString().replace("[", "").replace("]", "")
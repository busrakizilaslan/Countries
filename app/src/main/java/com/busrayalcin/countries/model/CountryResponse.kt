package com.busrayalcin.countries.model

import com.google.gson.annotations.SerializedName

data class CountryResponse(
    @SerializedName("data")
    val data: List<Country>
)
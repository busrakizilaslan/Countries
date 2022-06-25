package com.busrayalcin.countries.model

import com.google.gson.annotations.SerializedName

data class CountryDetailResponse (
    @SerializedName("data")
    val data: CountryDetail
)
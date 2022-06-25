package com.busrayalcin.countries.api

import com.busrayalcin.countries.model.CountryDetailResponse
import com.busrayalcin.countries.model.CountryResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {
    @GET("countries")
    suspend fun getCountries(
        @Header("X-RapidAPI-Key") token: String,
        @Query("limit") limit: String
    ): CountryResponse

    @GET("countries/{countryCode}")
    suspend fun getCountryDetail(
        @Path("countryCode") countryCode: String,
        @Header("X-RapidAPI-Key") token: String
    ): CountryDetailResponse
}
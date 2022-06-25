package com.busrayalcin.countries.repo

import com.busrayalcin.countries.api.APIService
import com.busrayalcin.countries.model.CountryDetailResponse
import com.busrayalcin.countries.model.CountryResponse
import com.busrayalcin.countries.utils.Constants
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: APIService) {
    suspend fun getCountries(): CountryResponse {
        return apiService.getCountries(Constants.API_KEY, Constants.LIMIT)
    }

    suspend fun getCountryDetail(countryCode: String): CountryDetailResponse {
        return apiService.getCountryDetail(countryCode, Constants.API_KEY)
    }
}
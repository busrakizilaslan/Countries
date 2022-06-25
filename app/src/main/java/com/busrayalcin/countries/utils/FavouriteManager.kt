package com.busrayalcin.countries.utils

import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.busrayalcin.countries.model.Country
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import javax.inject.Inject

class FavouriteManager @Inject constructor(private val preferences: SharedPreferences) {

    private var savedLiveData: MutableLiveData<Boolean>? = null

    fun getCountries(): ArrayList<Country>? {
        val gson = Gson()
        val jsonString: String? = preferences.getString("countries", null)
        val type: Type? = object : TypeToken<ArrayList<Country>>() {}.type

        return gson.fromJson(jsonString, type)
    }

    fun setCountry(country: Country) {
        val countries = getCountries() ?: arrayListOf()
        countries.add(country)

        val editor = preferences.edit()
        val gson = Gson()
        val jsonString: String? = gson.toJson(countries)
        editor.putString("countries", jsonString)
        editor.apply()

        savedLiveData?.postValue(true)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun removeCountry(country: Country) {
        val countries = getCountries() ?: arrayListOf()

        countries.removeIf { it.code == country.code }

        val editor = preferences.edit()
        val gson = Gson()
        val jsonString: String? = gson.toJson(countries)
        editor.putString("countries", jsonString)
        editor.apply()

        savedLiveData?.postValue(true)
    }

    fun countryIsSaved(country: Country): Boolean {
        val countries = getCountries() ?: arrayListOf()

        for (item in countries) {
            if (country.code == item.code) {
                return true
            }
        }
        return false
    }

    fun getSavedLiveData(): MutableLiveData<Boolean>? {
        if (savedLiveData == null) savedLiveData = MutableLiveData<Boolean>()
        return savedLiveData
    }
}
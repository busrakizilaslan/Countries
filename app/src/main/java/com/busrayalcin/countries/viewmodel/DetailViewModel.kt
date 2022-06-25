package com.busrayalcin.countries.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.busrayalcin.countries.repo.Repository
import com.busrayalcin.countries.utils.Resource
import com.busrayalcin.countries.utils.FavouriteManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val mainRepository: Repository,
    val favouriteManager: FavouriteManager
) : ViewModel() {

    fun fetchCountryDetail(countryCode: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = mainRepository.getCountryDetail(countryCode)))
        } catch (exception: Exception) {
            emit(Resource.error(exception.message ?: "Error !!!", data = null))
        }
    }
}
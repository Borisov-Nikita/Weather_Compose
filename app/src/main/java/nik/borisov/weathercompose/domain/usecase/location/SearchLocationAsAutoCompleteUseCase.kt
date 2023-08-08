package nik.borisov.weathercompose.domain.usecase.location

import nik.borisov.weathercompose.domain.repository.LocationRepository
import javax.inject.Inject

class SearchLocationAsAutoCompleteUseCase @Inject constructor(
    private val repository: LocationRepository
) {

    suspend operator fun invoke(query: String) {
        repository.searchLocationAsAutoComplete(
            query = query
        )
    }
}
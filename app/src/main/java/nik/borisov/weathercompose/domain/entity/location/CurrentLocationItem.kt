package nik.borisov.weathercompose.domain.entity.location

data class CurrentLocationItem(

    val isCurrentLocationUsed: Boolean,
    val lastUpdateEpoch: Long?
)

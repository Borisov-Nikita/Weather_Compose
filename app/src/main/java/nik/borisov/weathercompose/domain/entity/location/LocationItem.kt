package nik.borisov.weathercompose.domain.entity.location

data class LocationItem(

    val id: Int,
    val name: String,
    val country: String? = null,
    val region: String? = null,
    val latitude: String,
    val longitude: String,
    val timeZoneId: String
) {
    companion object {

        const val CURRENT_LOCATION_ID = -1
    }
}

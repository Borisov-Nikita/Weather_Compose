package nik.borisov.weathercompose.presentation.model.location

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationItemUi(

    val id: Int,
    val name: String,
    val regionAndCountry: String
) : Parcelable

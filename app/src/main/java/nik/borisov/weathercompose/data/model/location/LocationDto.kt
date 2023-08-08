package nik.borisov.weathercompose.data.model.location

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "location")
data class LocationDto(

    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    @ColumnInfo(name = "id")
    val id: Int?,
    @SerializedName("name")
    @ColumnInfo(name = "name")
    val name: String?,
    @SerializedName("country")
    @ColumnInfo(name = "country")
    val country: String?,
    @SerializedName("admin1")
    @ColumnInfo(name = "region")
    val region: String?,
    @SerializedName("latitude")
    @ColumnInfo(name = "latitude")
    val latitude: String?,
    @SerializedName("longitude")
    @ColumnInfo(name = "longitude")
    val longitude: String?,
    @SerializedName("timezone")
    @ColumnInfo(name = "time_zone")
    val timeZoneId: String?
)

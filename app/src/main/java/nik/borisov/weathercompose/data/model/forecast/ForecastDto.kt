package nik.borisov.weathercompose.data.model.forecast

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import nik.borisov.weathercompose.data.database.converter.ForecastGsonConverter
import nik.borisov.weathercompose.data.model.location.LocationDto

@TypeConverters(ForecastGsonConverter::class)
@Entity(
    tableName = "forecasts",
    foreignKeys = [
        ForeignKey(
            entity = LocationDto::class,
            parentColumns = ["id"],
            childColumns = ["id_location"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ForecastDto(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id_location")
    val locationId: Int,
    @ColumnInfo(name = "last_update")
    val lastUpdateEpoch: Long,
    @ColumnInfo(name = "forecast")
    val forecast: ForecastResponseDto,
    @ColumnInfo(name = "units")
    val units: ForecastUnitsDto
)

package nik.borisov.weathercompose.data.model.settings

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import nik.borisov.weathercompose.data.database.converter.SettingsGsonConverter

@TypeConverters(SettingsGsonConverter::class)
@Entity(tableName = "settings")
data class AppSettingsDto(

    @PrimaryKey(autoGenerate = false)
    val id: Int = 1,
    @ColumnInfo(name = "temp_unit")
    val tempUnit: TempUnitsData = TempUnitsData.CELSIUS,
    @ColumnInfo(name = "speed_unit")
    val speedUnit: SpeedUnitsData = SpeedUnitsData.KILOMETER,
)
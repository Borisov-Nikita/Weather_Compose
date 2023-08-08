package nik.borisov.weathercompose.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import nik.borisov.weathercompose.data.model.forecast.ForecastDto

@Dao
interface ForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addForecast(forecastDto: ForecastDto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addForecastList(forecastDtoList: List<ForecastDto>)

    @Query("SELECT * FROM forecasts")
    fun getForecasts(): Flow<List<ForecastDto>>
}
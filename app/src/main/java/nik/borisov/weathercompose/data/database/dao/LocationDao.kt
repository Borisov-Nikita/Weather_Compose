package nik.borisov.weathercompose.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import nik.borisov.weathercompose.data.model.location.CurrentLocationDto
import nik.borisov.weathercompose.data.model.location.LocationDto

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLocation(location: LocationDto)

    @Query("DELETE FROM location WHERE id =:locationId")
    suspend fun deleteLocation(locationId: Int)

    @Query("SELECT * FROM location")
    fun getLocations(): Flow<List<LocationDto>>

    @Query("SELECT * FROM location WHERE id =:locationId")
    suspend fun getLocation(locationId: Int): LocationDto

    @Query("SELECT (SELECT count(*) FROM location) > 0")
    fun hasLocation(): Flow<Boolean>

    @Query("SELECT (SELECT count(*) FROM location WHERE id =:locationId) > 0")
    suspend fun hasLocationById(locationId: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCurrentLocationUseState(currentLocation: CurrentLocationDto)

    @Query("SELECT * FROM current_location WHERE id =1")
    fun getCurrentLocationUseState(): Flow<CurrentLocationDto?>
}
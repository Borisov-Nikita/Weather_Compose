package nik.borisov.weathercompose.data.model.location

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_location")
data class CurrentLocationDto(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int = 1,
    @ColumnInfo(name = "use_current_location")
    val isCurrentLocationUsed: Boolean,
    @ColumnInfo(name = "last_update_epoch")
    val lastUpdateEpoch: Long? = null
)

package nik.borisov.weathercompose.util

import android.os.Build
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

class DateTimeUtil @Inject constructor() {

    fun convertTimeDateFromEpochToString(time: Long, timeZoneId: String, pattern: String): String {
        val formatter = SimpleDateFormat(pattern, Locale.ENGLISH)
        formatter.timeZone = TimeZone.getTimeZone(timeZoneId)
        return formatter.format(Date(time * MILLISECONDS_IN_SECONDS))
    }

    fun getCurrentTimeEpoch(): Long {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return ZonedDateTime.now().toEpochSecond()
        } else {
            Calendar.getInstance().timeInMillis / MILLISECONDS_IN_SECONDS
        }
    }

    fun getCurrentDayEpoch(): Long {
        return getCurrentTimeEpoch() / SECONDS_IN_DAY * SECONDS_IN_DAY
    }

    fun getCurrentHourEpoch(): Long {
        return getCurrentTimeEpoch() / SECONDS_IN_HOUR * SECONDS_IN_HOUR
    }

    fun getHourAfterTwentyFourHours(): Long {
        return getCurrentHourEpoch() + SECONDS_IN_DAY
    }

    fun getTimeHalfHourBeforeCurrent(): Long {
        return getCurrentTimeEpoch() - SECONDS_IN_HOUR / 2
    }

    companion object {

        private const val SECONDS_IN_HOUR = 3600
        private const val SECONDS_IN_DAY = 86400
        private const val MILLISECONDS_IN_SECONDS = 1000
    }
}
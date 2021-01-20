package pp.inzynierka.douslug.calendar

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

object DateConverter {
    private val TAG: String = "DATE_CONVERTER"

    fun getCurrentDate() : String {
        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy/MM/dd")
        return sdf.format(calendar.time)
    }

    fun timestampToDateString(timestamp: Long): String? {
        return try {
            Log.v(TAG, "Timestamp = $timestamp")
            val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm")
            val netDate = Date(timestamp)
            Log.v(TAG, "Date = ${sdf.format(netDate)}")
            sdf.format(netDate)
        } catch (e: Exception) {
            Log.v(TAG, "Error while converting date to timestamp - $e")
            null
        }
    }

    fun dateStringToTimestamp(date: String) : Long? {
        return try {
            val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm").parse(date)
            sdf.time
        } catch (e: Exception) {
            Log.v(TAG, "Error while converting date to timestamp - $e")
            null
        }
    }

    fun generateProperDate(year: String, month: String, day: String, hour: String, min: String) : String {
        return "$year/$month/$day $hour:$min"
    }

    fun generateProperDateShort(year: String, month: String, day: String) : String {
        return "$year/$month/$day"
    }

    private fun getDateWithoutHours(date: String) : String {
        Log.v(TAG, date.substring(0, 10))
        return date.substring(0, 10)
    }

    fun getTimestampsOfDay(date: String) : Pair<Long?, Long?> {
        val onlyDate = getDateWithoutHours(date)
        val timeFrom = dateStringToTimestamp(onlyDate.plus(" 00:00"))
        val timeTo = timeFrom?.plus(86399000)
        return Pair(timeFrom, timeTo)
    }

    fun getTimestampsOfMonth(year: String, month: String) : Pair<Long?, Long?> {
        val lastDayOfMonth = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH)
        val timeFrom = dateStringToTimestamp("$year/$month/01".plus(" 00:00"))
        val timeTo = dateStringToTimestamp("$year/$month/$lastDayOfMonth".plus(" 23:59"))
        Log.v(TAG, "Timestamps of month $year/$month: $timeFrom - $timeTo")
        return Pair(timeFrom, timeTo)
    }

    fun getCurrentTimestamp() : Long {
        return Calendar.getInstance().timeInMillis
    }

    private fun durationToHoursMinutes(duration: Long) : String {
        val h = duration / 60
        val m = duration % 60
        val min = "%.2f".format(m)
        return "$h:$min"
    }

    fun combineTimestampWithDuration(timestamp: Long?, duration: Long?) : String {
        if (timestamp != null && duration != null) {
            val dateStart = timestampToDateString(timestamp)
            val durationMillis = duration * 60 * 1000
            val dateEnd = timestampToDateString(timestamp + durationMillis)
            val dateEndHours = dateEnd?.substring(11)
            return "$dateStart - $dateEndHours"
        }
        return ""
    }
}
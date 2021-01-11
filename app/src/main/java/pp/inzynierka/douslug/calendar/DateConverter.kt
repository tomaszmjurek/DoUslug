package pp.inzynierka.douslug.calendar

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

object DateConverter {
    private val TAG: String = "DATE_CONVERTER"

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
}
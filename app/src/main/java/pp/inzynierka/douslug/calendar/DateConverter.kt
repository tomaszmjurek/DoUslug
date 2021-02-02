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
            val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm")
            val netDate = Date(timestamp)
            Log.v(TAG, "Timestamp $timestamp -> Date = ${sdf.format(netDate)}")
            sdf.format(netDate)
        } catch (e: Exception) {
            Log.v(TAG, "Error while converting date to timestamp - $e")
            null
        }
    }

    fun timestampToDateStringShort(timestamp: Long): String? {
        return timestampToDateString(timestamp)?.substring(0, 10)
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

    fun getTimestampsOfWeek(year: Int, month: Int, day: Int) : Pair<Long?, Long?> {
        val calendar = setCalendarToFirstDayOfWeek(year, month, day)
        val timeFrom = dateStringToTimestamp(makeStringFromCalendar(calendar, " 00:00"))
        calendar.add(Calendar.DAY_OF_MONTH, 6) // find next Sunday
        val timeTo = dateStringToTimestamp(makeStringFromCalendar(calendar, " 23:59"))
        return Pair(timeFrom, timeTo)
    }

    private fun setCalendarToFirstDayOfWeek(year: Int, month: Int, day: Int) : Calendar {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        calendar.add(Calendar.DAY_OF_MONTH, -dayOfWeek+2) // find last Monday
        return calendar
    }

    private fun makeStringFromCalendar(c: Calendar, hours: String) : String {
        return "${c.get(Calendar.YEAR)}" +
                "/${makeTwoDigitsIfOne((c.get(Calendar.MONTH)+1).toString())}" +
                "/${makeTwoDigitsIfOne(c.get(Calendar.DAY_OF_MONTH).toString())}" +
                hours
    }

    private fun makeTwoDigitsIfOne(text: String) : String {
        if (text.length == 1) return "0$text"
        return text
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

    fun convertDateToDayName(date: String) : String {
        val calendar = Calendar.getInstance()
        calendar.set(date.substring(0,4).toInt(), date.substring(5,7).toInt()-1, date.substring(8,10).toInt())
        val dayOfWeekNumber = calendar.get(Calendar.DAY_OF_WEEK)
        Log.v(TAG, "$date day of week is: $dayOfWeekNumber")
        return dayNumberToName(dayOfWeekNumber)
    }

    private fun dayNumberToName(num: Int) = when(num) {
        2 -> "Poniedziałek"
        3 -> "Wtorek"
        4 -> "Środa"
        5 -> "Czwartek"
        6 -> "Piątek"
        7 -> "Sobota"
        1 -> "Niedziela"
        else -> "Błędny dzień tygodnia"
    }

    fun getDatesOfWeek(year: Int, month: Int, day: Int) : List<String> {
        val calendar = setCalendarToFirstDayOfWeek(year, month, day)

        var dates = mutableListOf<String>()
        for (i in (1..7)) {
            dates.add(makeStringFromCalendar(calendar, ""))
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        return dates
    }
}
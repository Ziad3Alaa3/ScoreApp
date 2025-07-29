package utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

/**
 * Utility function to get a formatted day label based on the date string.
 *
 * @param dateString The date string in ISO 8601 format (e.g., "2023-10-01T12:00:00Z").
 * @return A string representing the day label, such as "اليوم", "غدًا", or a formatted date.
 */



fun getDayLabel(dateStr: String): String {
    return try {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        format.timeZone = TimeZone.getTimeZone("UTC")
        val date = format.parse(dateStr)

        val calendar = Calendar.getInstance()
        val today = calendar.time

        calendar.add(Calendar.DAY_OF_YEAR, 1)
        val tomorrow = calendar.time

        when {
            isSameDay(date, today) -> "اليوم"
            isSameDay(date, tomorrow) -> "غدًا"
            else -> SimpleDateFormat("EEEE dd MMM", Locale("ar", "EG")).format(date!!)
        }
    } catch (e: Exception) {
        "موعد غير معروف"
    }
}

fun isSameDay(date1: Date?, date2: Date): Boolean {
    if (date1 == null) return false
    val cal1 = Calendar.getInstance().apply { time = date1 }
    val cal2 = Calendar.getInstance().apply { time = date2 }
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
}

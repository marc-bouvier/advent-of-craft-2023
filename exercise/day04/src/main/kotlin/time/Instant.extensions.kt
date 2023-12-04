package time

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Instant.toLocalDate(timeZone: TimeZone): LocalDate {
    val localDateTime = this.toLocalDateTime(timeZone)
    return LocalDate(localDateTime.year, localDateTime.month, localDateTime.dayOfMonth)
}
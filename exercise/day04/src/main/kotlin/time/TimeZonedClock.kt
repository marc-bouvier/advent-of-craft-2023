package time

import kotlinx.datetime.*

data class TimeZonedClock(val clock: Clock, val timeZone: TimeZone) {
    fun todayAsLocalDate(): LocalDate = clock.now().toLocalDate(timeZone)
}
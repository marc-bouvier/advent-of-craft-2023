package time

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone

data class TimeZonedClock(val clock: Clock, val timeZone: TimeZone) {
    fun today(): LocalDate = clock.now().toLocalDate(timeZone)
}
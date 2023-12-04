package test.utilities

import kotlinx.datetime.*

data class TimeContext(val clock: Clock, val timeZone: TimeZone) {
    fun nowAsLocalDate(): LocalDate = clock.now().toLocalDate(timeZone)
}

 fun timeZone() = TimeZone.currentSystemDefault()

fun Instant.toLocalDate(timeZone: TimeZone): LocalDate {
    val localDateTime = this.toLocalDateTime(timeZone)
    return LocalDate(localDateTime.year, localDateTime.month, localDateTime.dayOfMonth)
}

class SpyClock(private val now: Instant) : Clock {
    override fun now(): Instant {
        return now
    }
}

 fun now(isoString: String = "2023-12-06T21:57:43.145Z") = Instant.parse(isoString)



package test.utilities

import kotlinx.datetime.*

fun timeZone() = TimeZone.currentSystemDefault()



class SpyClock(private val now: Instant) : Clock {
    override fun now(): Instant {
        return now
    }
}

 fun now(isoString: String = "2023-12-06T21:57:43.145Z") = Instant.parse(isoString)



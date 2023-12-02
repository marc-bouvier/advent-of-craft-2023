package food

import kotlinx.datetime.DateTimeUnit.Companion.DAY
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import java.util.*

@JvmRecord
data class FoodKt(
    val expirationDate: LocalDate,
    val approvedForConsumption: Boolean,
    val inspectorId: UUID?
) {

    fun isEdible(now: () -> LocalDate): Boolean {
        val expiryDateNotPassed = expirationDate > now.invoke().plus(1, DAY)
        val wasInspected = inspectorId != null

        return expiryDateNotPassed && approvedForConsumption && wasInspected
    }
}

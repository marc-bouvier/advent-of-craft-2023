package food

import kotlinx.datetime.DateTimeUnit.Companion.DAY
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import java.util.*
import java.util.function.Supplier

@JvmRecord
data class FoodKt(
    val expirationDate: LocalDate,
    val approvedForConsumption: Boolean,
    val inspectorId: UUID?
) {

    fun isEdible(now: Supplier<LocalDate>): Boolean {
        val expiryDateNotPassed = expirationDate > now.get().plus(1, DAY)
        val wasInspected = inspectorId != null

        return expiryDateNotPassed && approvedForConsumption && wasInspected
    }
}

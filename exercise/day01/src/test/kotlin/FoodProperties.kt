import food.Food
import io.kotest.core.spec.style.StringSpec
import io.kotest.property.Arb
import io.kotest.property.arbitrary.localDate
import io.kotest.property.forAll
import kotlinx.datetime.DateTimeUnit.Companion.DAY
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import java.util.*
import kotlinx.datetime.toJavaLocalDate as toJava

class FoodProperties : StringSpec({

    "is edible 1 day before expiration date" {
        val expirationDate = LocalDate(2023, 12, 24)
        forAll(Arb.localDate(maxDate = expirationDate.minus(1, DAY).toJava())) { now ->
            Food(
                expirationDate.toJava(),
                true,
                UUID.randomUUID()
            ).isEdible { now }
        }
    }

    "is not edible from expiration date" {
        val expirationDate = LocalDate(2023, 12, 24)
        forAll(Arb.localDate(minDate = expirationDate.toJava())) { now ->
            Food(
                expirationDate.toJava(),
                true,
                UUID.randomUUID()
            ).isEdible { now }.not()
        }
    }

})
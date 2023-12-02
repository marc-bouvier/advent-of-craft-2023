import games.FizzBuzz
import io.kotest.core.spec.style.StringSpec
import io.kotest.property.Arb
import io.kotest.property.arbitrary.filter
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.multiple
import io.kotest.property.forAll
import io.kotest.property.forNone

class FizzBuzzProperties : StringSpec({
    "Multiples of 3 Fizzes" {
        forAll(Arb.multiple(k = 3, max = 100).filter { it > 0 }) {
            FizzBuzz.convert(it).contains("Fizz")
        }

    }

    "Multiples of 5 Buzzes" {
        forAll(Arb.multiple(k = 5, max = 100).filter { it > 0 }) {
            FizzBuzz.convert(it).contains("Buzz")
        }
    }

    "Multiples of 15 FizzBuzzes" {
        forAll(Arb.multiple(k = 15, max = 100).filter { it > 0 }) {
            FizzBuzz.convert(it).contains("FizzBuzz")
        }
    }


    "Numbers non multiple of 3 or 5 are themselve" {
        forNone(Arb.int(min = 1, max = 100).filter { it % 3 == 0 && it % 5 == 0 }) {
            FizzBuzz.convert(it).equals("${it}")
        }
    }
})

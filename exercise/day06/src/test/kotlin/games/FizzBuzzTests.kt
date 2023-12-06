package games

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe


class FizzBuzzTests : StringSpec({

    // To Parameterize :
    //
    // The given number
    // Fizz
    // Buzz
    // FizzBuzz
    // Out of range

    "The given number" {
        forAll(
            row(1, "1"),
            row(67, "67"),
            row(82, "82"),
        )
        { givenNumber, conversion ->
            FizzBuzz.convert(givenNumber) shouldBe conversion
        }
    }

    "Fizz" {
        forAll(
            row(3),
            row(66),
            row(99),
        )
        { givenNumber ->
            FizzBuzz.convert(givenNumber) shouldBe "Fizz"
        }
    }

    "Buzz" {
        forAll(
            row(5),
            row(50),
            row(85),
        )
        { givenNumber ->
            FizzBuzz.convert(givenNumber) shouldBe "Buzz"
        }
    }

    "FizzBuzz" {
        forAll(
            row(15),
            row(30),
            row(45),
        )
        { givenNumber ->
            FizzBuzz.convert(givenNumber) shouldBe "FizzBuzz"
        }
    }

    "Out of range" {
        forAll(
            row(0),
            row(101),
            row(-1),
        )
        { givenNumber ->
            shouldThrow<OutOfRangeException> { FizzBuzz.convert(givenNumber) }
        }
    }
})
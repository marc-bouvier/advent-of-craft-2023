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
        FizzBuzz.convert(3) shouldBe "Fizz"
    }

    "Fizz for 3" {
        FizzBuzz.convert(3) shouldBe "Fizz"
    }

    "Fizz for 66" {
        FizzBuzz.convert(66) shouldBe "Fizz"
    }

    "Fizz for 99" {
        FizzBuzz.convert(99) shouldBe "Fizz"
    }

    "Buzz for 5" {
        FizzBuzz.convert(5) shouldBe "Buzz"
    }

    "Buzz for 50" {
        FizzBuzz.convert(50) shouldBe "Buzz"
    }

    "Buzz for 85" {
        FizzBuzz.convert(85) shouldBe "Buzz"
    }

    "Fizz Buzz for 15" {
        FizzBuzz.convert(15) shouldBe "FizzBuzz"
    }

    "Fizz Buzz for 30" {
        FizzBuzz.convert(30) shouldBe "FizzBuzz"
    }

    "Fizz Buzz for 45" {
        FizzBuzz.convert(45) shouldBe "FizzBuzz"
    }

    "Out of range for 0" {
        shouldThrow<OutOfRangeException> { FizzBuzz.convert(0) }
    }

    "Out of range for 101" {
        shouldThrow<OutOfRangeException> { FizzBuzz.convert(101) }
    }

    "Out of range for minus 1" {
        shouldThrow<OutOfRangeException> { FizzBuzz.convert(-1) }
    }
})
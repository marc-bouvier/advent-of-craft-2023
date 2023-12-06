package games

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.assertj.core.api.Assertions

class FizzBuzzTests : StringSpec({

    // To Parameterize :
    //
    // The given number
    // Fizz
    // Buzz
    // FizzBuzz
    // Out of range

    "The given number for X" {
        FizzBuzz.convert(1) shouldBe "1"
    }

    "The given number for 1" {
        FizzBuzz.convert(1) shouldBe "1"
    }

    "The given number for 67" {
        FizzBuzz.convert(67) shouldBe "67"
    }

    "The given number for 82" {
        FizzBuzz.convert(82) shouldBe "82"
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
package games

import io.kotest.core.spec.style.StringSpec
import org.assertj.core.api.Assertions

class FizzBuzzTests : StringSpec({

    "The given number for 1" {
        Assertions.assertThat(FizzBuzz.convert(1))
            .isEqualTo("1")
    }

    "The given number for 67" {
        Assertions.assertThat(FizzBuzz.convert(67))
            .isEqualTo("67")
    }

    "The given number for 82" {
        Assertions.assertThat(FizzBuzz.convert(82))
            .isEqualTo("82")
    }

    "Fizz for 3" {
        Assertions.assertThat(FizzBuzz.convert(3))
            .isEqualTo("Fizz")
    }

    "Fizz for 66" {
        Assertions.assertThat(FizzBuzz.convert(66))
            .isEqualTo("Fizz")
    }

    "Fizz for 99" {
        Assertions.assertThat(FizzBuzz.convert(99))
            .isEqualTo("Fizz")
    }

    "Buzz for 5" {
        Assertions.assertThat(FizzBuzz.convert(5))
            .isEqualTo("Buzz")
    }

    "Buzz for 50" {
        Assertions.assertThat(FizzBuzz.convert(50))
            .isEqualTo("Buzz")
    }

    "Buzz for 85" {
        Assertions.assertThat(FizzBuzz.convert(85))
            .isEqualTo("Buzz")
    }

    "Fizz Buzz for 15" {
        Assertions.assertThat(FizzBuzz.convert(15))
            .isEqualTo("FizzBuzz")
    }

    "Fizz Buzz for 30" {
        Assertions.assertThat(FizzBuzz.convert(30))
            .isEqualTo("FizzBuzz")
    }

    "Fizz Buzz for 45" {
        Assertions.assertThat(FizzBuzz.convert(45))
            .isEqualTo("FizzBuzz")
    }

    "Out of range for 0" {
        Assertions.assertThatThrownBy { FizzBuzz.convert(0) }
            .isInstanceOf(OutOfRangeException::class.java)
    }

    "Out of range for 101" {
        Assertions.assertThatThrownBy { FizzBuzz.convert(101) }
            .isInstanceOf(OutOfRangeException::class.java)
    }

    "Out of range for minus 1" {
        Assertions.assertThatThrownBy { FizzBuzz.convert(-1) }
            .isInstanceOf(OutOfRangeException::class.java)
    }
})
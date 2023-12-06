package games

import io.kotest.core.spec.style.StringSpec
import org.assertj.core.api.Assertions

class FizzBuzzTests : StringSpec({

    "Returns the given number for 1" {
        Assertions.assertThat(FizzBuzz.convert(1))
            .isEqualTo("1")
    }

    "Returns the given number for 67" {
        Assertions.assertThat(FizzBuzz.convert(67))
            .isEqualTo("67")
    }

    "Returns the given number for 82" {
        Assertions.assertThat(FizzBuzz.convert(82))
            .isEqualTo("82")
    }

    "Returns fizz for 3" {
        Assertions.assertThat(FizzBuzz.convert(3))
            .isEqualTo("Fizz")
    }

    "Returns fizz for 66" {
        Assertions.assertThat(FizzBuzz.convert(66))
            .isEqualTo("Fizz")
    }

    "Returns fizz for 99" {
        Assertions.assertThat(FizzBuzz.convert(99))
            .isEqualTo("Fizz")
    }

    "Returns buzz for 5" {
        Assertions.assertThat(FizzBuzz.convert(5))
            .isEqualTo("Buzz")
    }

    "Returns buzz for 50" {
        Assertions.assertThat(FizzBuzz.convert(50))
            .isEqualTo("Buzz")
    }

    "Returns buzz for 85" {
        Assertions.assertThat(FizzBuzz.convert(85))
            .isEqualTo("Buzz")
    }

    "Returns fizz buzz for 15" {
        Assertions.assertThat(FizzBuzz.convert(15))
            .isEqualTo("FizzBuzz")
    }

    "Returns fizz buzz for 30" {
        Assertions.assertThat(FizzBuzz.convert(30))
            .isEqualTo("FizzBuzz")
    }

    "Returns fizz buzz for 45" {
        Assertions.assertThat(FizzBuzz.convert(45))
            .isEqualTo("FizzBuzz")
    }

    "Throws an exception for 0" {
        Assertions.assertThatThrownBy { FizzBuzz.convert(0) }
            .isInstanceOf(OutOfRangeException::class.java)
    }

    "Throws an exception for 101" {
        Assertions.assertThatThrownBy { FizzBuzz.convert(101) }
            .isInstanceOf(OutOfRangeException::class.java)
    }

    "Throws an exception for minus 1" {
        Assertions.assertThatThrownBy { FizzBuzz.convert(-1) }
            .isInstanceOf(OutOfRangeException::class.java)
    }
})
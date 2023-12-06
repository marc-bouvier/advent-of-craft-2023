package games

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class FizzBuzzTests {
    @Test
    @Throws(OutOfRangeException::class)
    fun returns_the_given_number_for_1() {
        Assertions.assertThat(FizzBuzz.convert(1))
            .isEqualTo("1")
    }

    @Test
    @Throws(OutOfRangeException::class)
    fun returns_the_given_number_for_67() {
        Assertions.assertThat(FizzBuzz.convert(67))
            .isEqualTo("67")
    }

    @Test
    @Throws(OutOfRangeException::class)
    fun returns_the_given_number_for_82() {
        Assertions.assertThat(FizzBuzz.convert(82))
            .isEqualTo("82")
    }

    @Test
    @Throws(OutOfRangeException::class)
    fun returns_Fizz_for_3() {
        Assertions.assertThat(FizzBuzz.convert(3))
            .isEqualTo("Fizz")
    }

    @Test
    @Throws(OutOfRangeException::class)
    fun returns_Fizz_for_66() {
        Assertions.assertThat(FizzBuzz.convert(66))
            .isEqualTo("Fizz")
    }

    @Test
    @Throws(OutOfRangeException::class)
    fun returns_Fizz_for_99() {
        Assertions.assertThat(FizzBuzz.convert(99))
            .isEqualTo("Fizz")
    }

    @Test
    @Throws(OutOfRangeException::class)
    fun returns_Buzz_for_5() {
        Assertions.assertThat(FizzBuzz.convert(5))
            .isEqualTo("Buzz")
    }

    @Test
    @Throws(OutOfRangeException::class)
    fun returns_Buzz_for_50() {
        Assertions.assertThat(FizzBuzz.convert(50))
            .isEqualTo("Buzz")
    }

    @Test
    @Throws(OutOfRangeException::class)
    fun returns_Buzz_for_85() {
        Assertions.assertThat(FizzBuzz.convert(85))
            .isEqualTo("Buzz")
    }

    @Test
    @Throws(OutOfRangeException::class)
    fun returns_FizzBuzz_for_15() {
        Assertions.assertThat(FizzBuzz.convert(15))
            .isEqualTo("FizzBuzz")
    }

    @Test
    @Throws(OutOfRangeException::class)
    fun returns_FizzBuzz_for_30() {
        Assertions.assertThat(FizzBuzz.convert(30))
            .isEqualTo("FizzBuzz")
    }

    @Test
    @Throws(OutOfRangeException::class)
    fun returns_FizzBuzz_for_45() {
        Assertions.assertThat(FizzBuzz.convert(45))
            .isEqualTo("FizzBuzz")
    }

    @Test
    fun throws_an_exception_for_0() {
        Assertions.assertThatThrownBy { FizzBuzz.convert(0) }
            .isInstanceOf(OutOfRangeException::class.java)
    }

    @Test
    fun throws_an_exception_for_101() {
        Assertions.assertThatThrownBy { FizzBuzz.convert(101) }
            .isInstanceOf(OutOfRangeException::class.java)
    }

    @Test
    fun throws_an_exception_for_minus_1() {
        Assertions.assertThatThrownBy { FizzBuzz.convert(-1) }
            .isInstanceOf(OutOfRangeException::class.java)
    }
}
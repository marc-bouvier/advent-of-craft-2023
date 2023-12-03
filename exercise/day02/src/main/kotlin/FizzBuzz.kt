@file:JvmName("FizzBuzz")

package games

fun convert(number: Int): String = when {
    number <= 0 || number > 100 -> throw OutOfRange()
    number `is multiple of` 3 && number `is multiple of` 5 -> "FizzBuzz"
    number `is multiple of` 3 -> "Fizz"
    number `is multiple of` 5 -> "Buzz"
    else -> "$number"
}

infix fun Int.`is multiple of`(i: Int) = this % i == 0
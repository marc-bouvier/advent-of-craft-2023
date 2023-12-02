@file:JvmName("FizzBuzz")

package games

fun convert(input: Int): String = when {
    input <= 0 -> throw OutOfRangeException()
    input > 100 -> throw OutOfRangeException()
    input % 3 == 0 && input % 5 == 0 -> "FizzBuzz"
    input % 3 == 0 -> "Fizz"
    input % 5 == 0 -> "Buzz"
    else -> "$input"
}


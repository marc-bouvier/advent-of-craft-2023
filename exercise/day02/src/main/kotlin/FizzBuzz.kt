@file:JvmName("FizzBuzz")

package games

fun convert(input: Int): String {
    if (input <= 0) {
        throw OutOfRangeException()
    }

    if (input <= 100) {
        if (input % 3 == 0 && input % 5 == 0) {
            return "FizzBuzz"
        }
        if (input % 3 == 0) {
            return "Fizz"
        }
        return if (input % 5 == 0) {
            "Buzz"
        } else input.toString()
    } else {
        throw OutOfRangeException()
    }

}

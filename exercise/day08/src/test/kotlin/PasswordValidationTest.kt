import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

// alias tcr="mvn test && git commit -am working || git reset --hard"
// tcr
class PasswordValidationTest : StringSpec({
//    🔴 Write a failing test
//    🟢 Make the test pass
//    🔵 Refactor your code
//    - Write production code only to pass a failing unit test.
//    - Write no more of a unit test than sufficient to fail (compilation failures are failures).
//    - Write no more production code than necessary to pass the one failing unit test.

    "test" {
        "a" shouldBe "a"
    }

    // - Contains at least 8 characters
    // - Contains at least one capital letter
    // - Contains at least one lowercase letter
    // - Contains at least a number
    // - Contains at least a special character in this list `. * # @ $ % &`.
    // - Any other characters are not authorized.

})
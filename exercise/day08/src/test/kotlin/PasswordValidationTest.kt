import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

//  mvn test && git commit -am working || git reset --hard
// - Contains at least 8 characters
// - Contains at least one capital letter
// - Contains at least one lowercase letter
// - Contains at least a number
// - Contains at least a special character in this list `. * # @ $ % &`.
// - Any other characters are not authorized.
// The TDD rules are quite simple:
//
//    - Write production code only to pass a failing unit test.
//    - Write no more of a unit test than sufficient to fail (compilation failures are failures).
//    - Write no more production code than necessary to pass the one failing unit test.
//
//A gentle reminder, the steps to use the TDD magic can be applied this way:
//
//    🔴 Write a failing test
//    🟢 Make the test pass
//    🔵 Refactor your code
//The magic will continue to work only with one condition:
//**if at any time, your tests are not passing, you need to revert your latest change.**
class PasswordValidationTest : StringSpec({
"test"{
    "a" shouldBe "a"
}
})
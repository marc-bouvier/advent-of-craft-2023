import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

// Does not rollback if failing
class PasswordValidationAcceptation : StringSpec({
"test"{
    "a" shouldBe "b"
}
})
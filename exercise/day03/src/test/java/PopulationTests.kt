import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import people.Person
import people.Pet
import people.PetType
import java.util.*
import kotlin.Int.Companion.MAX_VALUE

class PopulationTests : StringSpec({
    lateinit var population: List<Person>
    beforeSpec {
        population = listOf(
            Person("Peter", "Griffin")
                .addPet(PetType.CAT, "Tabby", 2),
            Person("Stewie", "Griffin")
                .addPet(PetType.CAT, "Dolly", 3)
                .addPet(PetType.DOG, "Brian", 9),
            Person("Joe", "Swanson")
                .addPet(PetType.DOG, "Spike", 4),
            Person("Lois", "Griffin")
                .addPet(PetType.SNAKE, "Serpy", 1),
            Person("Meg", "Griffin")
                .addPet(PetType.BIRD, "Tweety", 1),
            Person("Chris", "Griffin")
                .addPet(PetType.TURTLE, "Speedy", 4),
            Person("Cleveland", "Brown")
                .addPet(PetType.HAMSTER, "Fuzzy", 1)
                .addPet(PetType.HAMSTER, "Wuzzy", 2),
            Person("Glenn", "Quagmire")
        )
    }

    "Who owns the youngest pet" {
        population
            .minByOrNull { person ->
                person.`youngest pet's age`()
                    .orElse(MAX_VALUE)
            }.also { `youngest pet owner` ->
                `youngest pet owner`!!.firstName shouldBe "Lois"
            }


    }


})

fun Person.`youngest pet's age`(): OptionalInt = pets
    .stream()
    .mapToInt(Pet::age)
    .min()




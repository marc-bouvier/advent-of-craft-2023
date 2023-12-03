import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import people.Person
import people.Pet
import people.PetType.*
import kotlin.Int.Companion.MAX_VALUE

class PopulationTests : StringSpec({
    lateinit var population: List<Person>
    beforeSpec {
        population = listOf(
            Person("Peter", "Griffin")
                .addPet(CAT, "Tabby", 2),
            Person("Stewie", "Griffin")
                .addPet(CAT, "Dolly", 3)
                .addPet(DOG, "Brian", 9),
            Person("Joe", "Swanson")
                .addPet(DOG, "Spike", 4),
            Person("Lois", "Griffin")
                .addPet(SNAKE, "Serpy", 1),
            Person("Meg", "Griffin")
                .addPet(BIRD, "Tweety", 1),
            Person("Chris", "Griffin")
                .addPet(TURTLE, "Speedy", 4),
            Person("Cleveland", "Brown")
                .addPet(HAMSTER, "Fuzzy", 1)
                .addPet(HAMSTER, "Wuzzy", 2),
            Person("Glenn", "Quagmire")
        )
    }

    "Who owns the youngest pet" {

        val ownerOfYoungestPet = population.minByOrNull { person ->
            person.`youngest pet's age`()
                ?: MAX_VALUE
        }

        ownerOfYoungestPet!!.firstName shouldBe "Lois"

    }
})

fun Person.`youngest pet's age`() =
    pets.minOfOrNull(Pet::age)




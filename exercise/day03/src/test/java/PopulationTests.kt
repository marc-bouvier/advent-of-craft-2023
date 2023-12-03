import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.assertj.core.api.Assertions
import people.Person
import people.Pet
import people.PetType
import java.util.*

class PopulationTests : FunSpec({
    var population: List<Person>? = null
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

    test("whoOwnsTheYoungestPet") {
        val filtered = population!!.stream().min(Comparator.comparingInt { person: Person ->
            person.pets.stream().mapToInt { (_, _, age): Pet -> age }
                .min().orElse(Int.MAX_VALUE)
        }).orElse(null)!!
        filtered.firstName.shouldBe("Lois")
    }


})




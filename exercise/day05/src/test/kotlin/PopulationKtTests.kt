import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.assertj.core.api.Assertions
import people.Person
import people.Pet
import people.PetType

typealias Population = List<Person>

class PopulationKtTests : StringSpec({
    lateinit var population: Population
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
        val filtered = population
            .minByOrNull { person -> person.youngestPetAgeOfThePerson() }
        filtered!!.firstName shouldBe "Lois"
    }

    "People with their pets" {
        val response = population.format()
        Assertions.assertThat(response.toString())
            .hasToString(
                "Peter Griffin who owns : Tabby " + System.lineSeparator() +
                        "Stewie Griffin who owns : Dolly Brian " + System.lineSeparator() +
                        "Joe Swanson who owns : Spike " + System.lineSeparator() +
                        "Lois Griffin who owns : Serpy " + System.lineSeparator() +
                        "Meg Griffin who owns : Tweety " + System.lineSeparator() +
                        "Chris Griffin who owns : Speedy " + System.lineSeparator() +
                        "Cleveland Brown who owns : Fuzzy Wuzzy " + System.lineSeparator() +
                        "Glenn Quagmire"
            )
    }

})

fun Population.format(): StringBuilder {
    val response = StringBuilder()
    for (person in this) {
        response.append(String.format("%s %s", person.firstName, person.lastName))
        if (!person.pets.isEmpty()) {
            response.append(" who owns : ")
        }
        for (pet in person.pets) {
            response.append(pet.name).append(" ")
        }
        if (this.last != person) {
            response.append(System.lineSeparator())
        }
    }
    return response
}

fun Person.youngestPetAgeOfThePerson(): Int {
    return this.pets.minOfOrNull(Pet::age) ?: Int.MAX_VALUE
}

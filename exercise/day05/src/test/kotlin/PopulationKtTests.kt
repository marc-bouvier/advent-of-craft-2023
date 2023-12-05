import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.assertj.core.api.Assertions
import people.Person
import people.Pet
import people.PetType

typealias Population = List<Person>

val EOL = System.lineSeparator()!!

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
        val filtered = population.minByOrNull(Person::youngestPetAge)
        filtered!!.firstName shouldBe "Lois"
    }

    "People with their pets" {
        val response = population.format()
        Assertions.assertThat(response.toString())
            .hasToString(
                "Peter Griffin who owns : Tabby " + EOL +
                        "Stewie Griffin who owns : Dolly Brian " + EOL +
                        "Joe Swanson who owns : Spike " + EOL +
                        "Lois Griffin who owns : Serpy " + EOL +
                        "Meg Griffin who owns : Tweety " + EOL +
                        "Chris Griffin who owns : Speedy " + EOL +
                        "Cleveland Brown who owns : Fuzzy Wuzzy " + EOL +
                        "Glenn Quagmire"
            )
    }

})

fun Population.format(): StringBuilder {
    val response = StringBuilder()
    for (person in this) {
        response.append("${person.firstName} ${person.lastName}")
        if (person.pets.isNotEmpty()) {
            response.append(" who owns : ")
        }
        for (pet in person.pets) {
            response.append(pet.name).append(" ")
        }
        if (this.last() != person) {
            response.append(EOL)
        }
    }
    return response
}

fun Person.youngestPetAge(): Int {
    return this.pets.minOfOrNull(Pet::age) ?: Int.MAX_VALUE
}

package people

data class Person(
    val firstName: String,
    val lastName: String,
    val pets: MutableList<Pet>
) {

    constructor(firstName: String, lastName: String) :
            this(firstName, lastName, mutableListOf())

    fun addPet(petType: PetType, name: String, age: Int): Person {
        pets.add(Pet(petType, name, age))
        return this
    }
}

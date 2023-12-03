package people

@JvmRecord
data class Person(@JvmField val firstName: String?, val lastName: String?, @JvmField val pets: MutableList<Pet?>) {
    constructor(firstName: String?, lastName: String?) : this(firstName, lastName, ArrayList<Pet?>())

    fun addPet(petType: PetType?, name: String?, age: Int): Person {
        pets.add(Pet(petType, name, age))
        return this
    }
}

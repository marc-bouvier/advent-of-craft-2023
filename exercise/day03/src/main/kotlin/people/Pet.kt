package people

@JvmRecord
data class Pet(val type: PetType, val name: String, @JvmField val age: Int)

package ci.dependencies

interface Logger {
    fun info(message: String?)
    fun error(message: String?)
}

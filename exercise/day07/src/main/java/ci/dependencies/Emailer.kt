package ci.dependencies

interface Emailer {
    fun send(message: String?)
}

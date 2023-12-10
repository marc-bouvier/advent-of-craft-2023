package ci.dependencies

interface Config {
    fun sendEmailSummary(): Boolean
}

fun Config.emailDisabled() = !this.sendEmailSummary()
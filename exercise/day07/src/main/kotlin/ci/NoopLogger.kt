package ci

import ci.dependencies.Logger

/** Does nothing */
class NoopLogger : Logger {
    override fun info(message: String) = Unit
    override fun error(message: String) = Unit
}
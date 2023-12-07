package ci

import ci.dependencies.Logger

class CapturingLogger : Logger {
    private val lines: MutableList<String> = ArrayList()
    override fun info(message: String) {
        lines.add("INFO: $message")
    }

    override fun error(message: String) {
        lines.add("ERROR: $message")
    }

    val loggedLines: List<String>
        get() = lines
}
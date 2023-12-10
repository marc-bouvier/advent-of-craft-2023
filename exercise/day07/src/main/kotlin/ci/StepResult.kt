package ci

import ci.dependencies.Logger

class StepResult private constructor(
    private val success: Boolean,
    private val message: String = "",
    private val log: Logger
) {

    fun succeeded() = success

    fun failed() = !success

    fun log(): StepResult {
        when {
            success -> log.info(message)
            else -> log.error(message)
        }
        return this
    }

    companion object {

        fun succeed(message: String, log: Logger) = StepResult(success = true, message, log)
        fun succeedSilently() = StepResult(success = true, log = NoopLogger())

        fun fail(message: String, log: Logger) = StepResult(success = false, message, log)
        fun failSilently() = StepResult(success = false, log = NoopLogger())
    }
}
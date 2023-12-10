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

        fun succeed(message: String, log: Logger): StepResult = StepResult(success = true, message = message, log = log)
        fun succeedSilently(): StepResult = StepResult(success = true, log = NoopLogger())

        fun fail(message: String, log: Logger): StepResult = StepResult(success = false, message = message, log = log)
        fun failSilently(): StepResult = StepResult(success = false, log = NoopLogger())
    }
}
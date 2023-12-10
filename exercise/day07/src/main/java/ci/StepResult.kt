package ci

import ci.dependencies.Logger

class StepResult(
    private val success: Boolean, // Maybe enum/value object or builder?
    private val message: String = "",
    private val log: Logger
) {

    fun succeeded() = success
    fun failed() = !success
    fun log(): StepResult {
        if (success) log.info(message)
        else log.error(message)
        return this
    }

    companion object {
        fun failingSilently(): StepResult {
            return StepResult(success = false, log = NoopLogger())
        }

        fun succeedingSilently(): StepResult {
            return StepResult(success = true, log = NoopLogger())
        }

        fun succeeding(message: String, log: Logger): StepResult {
            return StepResult(success = true, message = message, log = log)
        }

        fun failing(message: String, log: Logger): StepResult {
            return StepResult(success = false, message = message, log = log)
        }
    }
}
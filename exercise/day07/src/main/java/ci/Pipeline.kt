package ci

import ci.dependencies.Config
import ci.dependencies.Emailer
import ci.dependencies.Logger
import ci.dependencies.Project

private const val SUCCESS = "success"

class Pipeline(
    private val config: Config,
    private val emailer: Emailer,
    private val log: Logger
) {

    // Read code to try understand what it does

    // Code seems to have 3 groups of behaviour
    // - tests
    // - deploy (depends on tests)
    // - send email (depends on both test and deploy or test only)
    fun run(project: Project) {
        // ✅ Challenge of day 2: Your code can only have one level of indentation.
        // ✅ Challenge of day 3: One dot per line.
        // ✅ Challenge of day 1: Make your production code easier to understand.

        val testsResult = runJobTest(project).log()
        val deployResult = runJobDeploy(project, testsResult).log()
        runJobSendEmail(testsResult, deployResult)
    }

    private fun runJobTest(project: Project): StepResult {
        if (project.hasNoTests()) {
            return StepResult(true, "No tests", log)
        }
        return project.runStep(
            runStep = Project::runTests,
            successMessage = "Tests passed",
            errorMessage = "Tests failed",
        )
    }

    class StepResult(val success: Boolean, private val message: String, private val log: Logger) {

        fun log(): StepResult {
            if (success) log.info(message)
            else log.error(message)
            return this
        }

        companion object {
            fun failingSilently(): StepResult {
                return StepResult(false, "", NoopLogger())
            }
        }
    }

    private fun runJobDeploy(project: Project, testsPassed: StepResult): StepResult {
        if (!testsPassed.success) return StepResult.failingSilently()

        return project.runStep(
            runStep = Project::deploy,
            successMessage = "Deployment successful",
            errorMessage = "Deployment failed",
        )
    }

    private fun Project.runStep(
        runStep: (Project) -> String,
        successMessage: String,
        errorMessage: String
    ): StepResult {
        val onStepSuccess = SUCCESS == runStep(this)
        return if (onStepSuccess) {
            StepResult(true, successMessage, log)
        } else {
            StepResult(false, errorMessage, log)
        }
    }

    private fun runJobSendEmail(testsResult: StepResult, deployResut: StepResult) {
        if (config.emailDisabled()) {
            StepResult(true, "Email disabled", log).log()
            return
        }
        log.info("Sending email")
        sendEmail(testsResult, deployResut)
    }

    private fun Config.emailDisabled() =
        !this.sendEmailSummary()

    private fun sendEmail(onTestJobSuccess: StepResult, onDeployJobSuccess: StepResult) {
        if (!onTestJobSuccess.success) {
            emailer.send("Tests failed")
            return
        }
        if (onDeployJobSuccess.success) {
            emailer.send("Deployment completed successfully")
            return
        }
        emailer.send("Deployment failed")
        return
    }


}

class NoopLogger : Logger {
    override fun info(message: String) {
        // Does nothing
    }

    override fun error(message: String) {
        // Does nothing
    }

}

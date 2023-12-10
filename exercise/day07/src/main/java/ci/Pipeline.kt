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

        val onTestJobSuccess = runJobTest(project).log().success
        val onDeployJobSuccess = runJobDeploy(project, onTestJobSuccess).log().success
        runJobSendEmail(onTestJobSuccess, onDeployJobSuccess)
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
    }

    private fun runJobDeploy(project: Project, testsPassed: Boolean): StepResult {
        if (!testsPassed) return StepResult(false, "", NoopLogger())

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
        if (!onStepSuccess) {
            return StepResult(false, errorMessage, log)
        } else {
            return StepResult(true, successMessage, log)
        }
    }

    private fun runJobSendEmail(onTestJobSuccess: Boolean, onDeployJobSuccess: Boolean) {
        if (config.emailDisabled()) {
            StepResult(true, "Email disabled", log).log()
            return
        }
        log.info("Sending email")
        runStepSendEmail(onTestJobSuccess, onDeployJobSuccess)
    }

    private fun Config.emailDisabled() =
        !this.sendEmailSummary()

    private fun runStepSendEmail(onTestJobSuccess: Boolean, onDeployJobSuccess: Boolean) {
        if (!onTestJobSuccess) {
            emailer.send("Tests failed")
            return
        }
        if (onDeployJobSuccess) {
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

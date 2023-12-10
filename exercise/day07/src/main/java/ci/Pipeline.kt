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

        val testsResult = test(project).log()
        val deployResult = deploy(project, testsResult).log()
        summary(testsResult, deployResult).log()
    }

    private fun test(project: Project): StepResult {
        return if (project.hasNoTests()) {
            StepResult.succeeding("No tests", log)
        } else {
            project.runStep(
                runStep = Project::runTests,
                successMessage = "Tests passed",
                errorMessage = "Tests failed",
            )
        }
    }

    private fun deploy(project: Project, tests: StepResult): StepResult {

        return if (tests.failed()) {
            StepResult.failingSilently()
        } else {
            project.runStep(
                runStep = Project::deploy,
                successMessage = "Deployment successful",
                errorMessage = "Deployment failed",
            )
        }

    }

    private fun Project.runStep(
        runStep: (Project) -> String,
        successMessage: String,
        errorMessage: String
    ): StepResult {

        return if (SUCCESS == runStep(this)) {
            StepResult.succeeding(successMessage, log)
        } else {
            StepResult.failing(errorMessage, log)
        }
    }

    private fun summary(tests: StepResult, deploy: StepResult): StepResult {

        if (config.emailDisabled()) {
            return StepResult.succeeding("Email disabled", log)
        }

        log.info("Sending email")
        return sendEmail(tests, deploy)
    }

    private fun Config.emailDisabled() =
        !this.sendEmailSummary()

    private fun sendEmail(tests: StepResult, deploy: StepResult): StepResult {

        if (tests.failed()) {
            emailer.send("Tests failed")
            return StepResult.succeedingSilently()
        }

        if (deploy.succeeded()) {
            emailer.send("Deployment completed successfully")
            return StepResult.succeedingSilently()
        }

        emailer.send("Deployment failed")
        return StepResult.failingSilently()
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

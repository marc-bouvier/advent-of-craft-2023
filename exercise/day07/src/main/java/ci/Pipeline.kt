package ci

import ci.dependencies.*

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
    ): StepResult =
        if (SUCCESS == runStep(this)) {
            StepResult.succeeding(successMessage, log)
        } else {
            StepResult.failing(errorMessage, log)
        }

    private fun summary(tests: StepResult, deploy: StepResult): StepResult =
        if (config.emailDisabled())
            StepResult.succeeding("Email disabled", log)
        else sendEmailSummary(tests, deploy)

    private fun sendEmailSummary(tests: StepResult, deploy: StepResult): StepResult {
        log.info("Sending email")
        return when {
            tests.failed() -> sendEmail("Tests failed")
            deploy.succeeded() -> sendEmail("Deployment completed successfully")
            else -> sendEmail("Deployment failed")
        }
    }

    private fun sendEmail(message: String): StepResult {
        emailer.send(message)
        return StepResult.succeedingSilently()
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

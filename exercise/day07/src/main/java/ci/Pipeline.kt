package ci

import ci.dependencies.*

private const val SUCCESS = "success"

class Pipeline(
    private val config: Config,
    private val emailer: Emailer,
    private val log: Logger
) {

    // Read code to try understanding what it does

    // Code seems to have 3 groups of behaviour
    // - tests
    // - deploy (depends on tests)
    // - send email (depends on both test and deploy or test only)
    fun run(project: Project) {

        // ✅ Challenge of day 2: Your code can only have one level of indentation.
        // ✅ Challenge of day 3: One dot per line.
        // ✅ Challenge of day 1: Make your production code easier to understand.

        val testsResult = test(project)
        testsResult.log()

        val deployResult = deploy(project, testsResult)
        deployResult.log()

        val summaryResult = summary(testsResult, deployResult)
        summaryResult.log()
    }

    private fun test(project: Project): StepResult =
        when {
            project.hasNoTests() -> StepResult.succeed("No tests", log)
            project.runTests() == SUCCESS -> StepResult.succeed("Tests passed", log)
            else -> StepResult.fail("Tests failed", log)
        }

    private fun deploy(project: Project, tests: StepResult): StepResult =
        when {
            tests.failed() -> StepResult.failSilently()
            project.deploy() == SUCCESS -> StepResult.succeed("Deployment successful", log)
            else -> StepResult.fail("Deployment failed", log)
        }

    private fun summary(tests: StepResult, deploy: StepResult): StepResult =
        when {
            config.emailDisabled() -> StepResult.succeed("Email disabled", log)
            else -> sendEmailSummary(tests, deploy)
        }

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
        return StepResult.succeedSilently()
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

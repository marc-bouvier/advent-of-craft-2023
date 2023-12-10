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

        val onTestJobSuccess = runJobTest(project)
        val onDeployJobSuccess = runJobDeploy(project, onTestJobSuccess)
        runJobSendEmail(onTestJobSuccess, onDeployJobSuccess)
    }

    private fun runJobTest(project: Project): Boolean {
        if (project.hasNoTests()) {
            log.info("No tests")
            return true
        }
        return project.runStep(
            step = Project::runTests,
            successMessage = "Tests passed",
            errorMessage = "Tests failed",
        )
    }

    private fun Project.runStep(
        errorMessage: String,
        successMessage: String,
        step: (Project) -> String
    ): Boolean {
        val onStepSuccess = SUCCESS == step(this)
        if (!onStepSuccess) {
            log.error(errorMessage)
            return false
        } else {
            log.info(successMessage)
            return true
        }
    }

    private fun runJobDeploy(project: Project, testsPassed: Boolean): Boolean {
        if (!testsPassed) return false
        return project.runStep(
            step = Project::deploy,
            successMessage = "Deployment successful",
            errorMessage = "Deployment failed",
        )
    }

    private fun runJobSendEmail(onTestJobSuccess: Boolean, onDeployJobSuccess: Boolean) {
        if (config.emailDisabled()) {
            log.info("Email disabled")
            return
        }
        runStepSendEmail(onTestJobSuccess, onDeployJobSuccess)
    }

    private fun Config.emailDisabled() =
        !this.sendEmailSummary()

    private fun runStepSendEmail(onTestJobSuccess: Boolean, onDeployJobSuccess: Boolean) {
        log.info("Sending email")
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

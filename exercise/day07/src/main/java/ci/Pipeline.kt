package ci

import ci.dependencies.Config
import ci.dependencies.Emailer
import ci.dependencies.Logger
import ci.dependencies.Project

class Pipeline(
    private val config: Config, private val emailer: Emailer, private val log: Logger
) {

    // TODO:
    //   Challenge of day 7: Simplify the run method by extracting the right behavior.
    //   May your crafting journey bring new challenges!
    //  💡HINT: Use all the techniques you've learned this week.
    fun run(project: Project) {
        val deploySuccessful: Boolean
        val testsPassed: Boolean = if (project.hasTests()) {
            if ("success" == project.runTests()) {
                log.info("Tests passed")
                true
            } else {
                log.error("Tests failed")
                false
            }
        } else {
            log.info("No tests")
            true
        }
        deploySuccessful = if (testsPassed) {
            if ("success" == project.deploy()) {
                log.info("Deployment successful")
                true
            } else {
                log.error("Deployment failed")
                false
            }
        } else {
            false
        }
        if (config.sendEmailSummary()) {
            log.info("Sending email")
            if (testsPassed) {
                if (deploySuccessful) {
                    emailer.send("Deployment completed successfully")
                } else {
                    emailer.send("Deployment failed")
                }
            } else {
                emailer.send("Tests failed")
            }
        } else {
            log.info("Email disabled")
        }
    }
}

package ci

import ci.dependencies.Config
import ci.dependencies.Emailer
import ci.dependencies.Project
import ci.dependencies.TestStatus
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito

internal class PipelineTest {
    private val config = Mockito.mock(Config::class.java)
    private val log = CapturingLogger()
    private val emailer = Mockito.mock(Emailer::class.java)
    private var pipeline: Pipeline? = null
    @BeforeEach
    fun setUp() {
        pipeline = Pipeline(config, emailer, log)
    }

    @Test
    fun project_with_tests_that_deploys_successfully_with_email_notification() {
        Mockito.`when`(config.sendEmailSummary()).thenReturn(true)
        val project = Project.builder()
            .setTestStatus(TestStatus.PASSING_TESTS)
            .setDeploysSuccessfully(true)
            .build()
        pipeline!!.run(project)
        Assertions.assertEquals(
            mutableListOf(
                "INFO: Tests passed",
                "INFO: Deployment successful",
                "INFO: Sending email"
            ), log.loggedLines
        )
        Mockito.verify(emailer).send("Deployment completed successfully")
    }

    @Test
    fun project_with_tests_that_deploys_successfully_without_email_notification() {
        Mockito.`when`(config.sendEmailSummary()).thenReturn(false)
        val project = Project.builder()
            .setTestStatus(TestStatus.PASSING_TESTS)
            .setDeploysSuccessfully(true)
            .build()
        pipeline!!.run(project)
        Assertions.assertEquals(
            mutableListOf(
                "INFO: Tests passed",
                "INFO: Deployment successful",
                "INFO: Email disabled"
            ), log.loggedLines
        )
        Mockito.verify(emailer, Mockito.never()).send(ArgumentMatchers.any())
    }

    @Test
    fun project_without_tests_that_deploys_successfully_with_email_notification() {
        Mockito.`when`(config.sendEmailSummary()).thenReturn(true)
        val project = Project.builder()
            .setTestStatus(TestStatus.NO_TESTS)
            .setDeploysSuccessfully(true)
            .build()
        pipeline!!.run(project)
        Assertions.assertEquals(
            mutableListOf(
                "INFO: No tests",
                "INFO: Deployment successful",
                "INFO: Sending email"
            ), log.loggedLines
        )
        Mockito.verify(emailer).send("Deployment completed successfully")
    }

    @Test
    fun project_without_tests_that_deploys_successfully_without_email_notification() {
        Mockito.`when`(config.sendEmailSummary()).thenReturn(false)
        val project = Project.builder()
            .setTestStatus(TestStatus.NO_TESTS)
            .setDeploysSuccessfully(true)
            .build()
        pipeline!!.run(project)
        Assertions.assertEquals(
            mutableListOf(
                "INFO: No tests",
                "INFO: Deployment successful",
                "INFO: Email disabled"
            ), log.loggedLines
        )
        Mockito.verify(emailer, Mockito.never()).send(ArgumentMatchers.any())
    }

    @Test
    fun project_with_tests_that_fail_with_email_notification() {
        Mockito.`when`(config.sendEmailSummary()).thenReturn(true)
        val project = Project.builder()
            .setTestStatus(TestStatus.FAILING_TESTS)
            .build()
        pipeline!!.run(project)
        Assertions.assertEquals(
            mutableListOf(
                "ERROR: Tests failed",
                "INFO: Sending email"
            ), log.loggedLines
        )
        Mockito.verify(emailer).send("Tests failed")
    }

    @Test
    fun project_with_tests_that_fail_without_email_notification() {
        Mockito.`when`(config.sendEmailSummary()).thenReturn(false)
        val project = Project.builder()
            .setTestStatus(TestStatus.FAILING_TESTS)
            .build()
        pipeline!!.run(project)
        Assertions.assertEquals(
            mutableListOf(
                "ERROR: Tests failed",
                "INFO: Email disabled"
            ), log.loggedLines
        )
        Mockito.verify(emailer, Mockito.never()).send(ArgumentMatchers.any())
    }

    @Test
    fun project_with_tests_and_failing_build_with_email_notification() {
        Mockito.`when`(config.sendEmailSummary()).thenReturn(true)
        val project = Project.builder()
            .setTestStatus(TestStatus.PASSING_TESTS)
            .setDeploysSuccessfully(false)
            .build()
        pipeline!!.run(project)
        Assertions.assertEquals(
            mutableListOf(
                "INFO: Tests passed",
                "ERROR: Deployment failed",
                "INFO: Sending email"
            ), log.loggedLines
        )
        Mockito.verify(emailer).send("Deployment failed")
    }

    @Test
    fun project_with_tests_and_failing_build_without_email_notification() {
        Mockito.`when`(config.sendEmailSummary()).thenReturn(false)
        val project = Project.builder()
            .setTestStatus(TestStatus.PASSING_TESTS)
            .setDeploysSuccessfully(false)
            .build()
        pipeline!!.run(project)
        Assertions.assertEquals(
            mutableListOf(
                "INFO: Tests passed",
                "ERROR: Deployment failed",
                "INFO: Email disabled"
            ), log.loggedLines
        )
        Mockito.verify(emailer, Mockito.never()).send(ArgumentMatchers.any())
    }

    @Test
    fun project_without_tests_and_failing_build_with_email_notification() {
        Mockito.`when`(config.sendEmailSummary()).thenReturn(true)
        val project = Project.builder()
            .setTestStatus(TestStatus.NO_TESTS)
            .setDeploysSuccessfully(false)
            .build()
        pipeline!!.run(project)
        Assertions.assertEquals(
            mutableListOf(
                "INFO: No tests",
                "ERROR: Deployment failed",
                "INFO: Sending email"
            ), log.loggedLines
        )
        Mockito.verify(emailer).send("Deployment failed")
    }

    @Test
    fun project_without_tests_and_failing_build_without_email_notification() {
        Mockito.`when`(config.sendEmailSummary()).thenReturn(false)
        val project = Project.builder()
            .setTestStatus(TestStatus.NO_TESTS)
            .setDeploysSuccessfully(false)
            .build()
        pipeline!!.run(project)
        Assertions.assertEquals(
            mutableListOf(
                "INFO: No tests",
                "ERROR: Deployment failed",
                "INFO: Email disabled"
            ), log.loggedLines
        )
        Mockito.verify(emailer, Mockito.never()).send(ArgumentMatchers.any())
    }
}
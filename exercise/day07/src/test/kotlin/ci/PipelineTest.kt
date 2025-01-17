package ci

import ci.dependencies.Config
import ci.dependencies.Emailer
import ci.dependencies.Project
import ci.dependencies.TestStatus.*
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

internal class PipelineTest {

    private val config = mock(Config::class.java)
    private val log = CapturingLogger()
    private val emailer = mock(Emailer::class.java)

    private lateinit var pipeline: Pipeline

    private val newConfig = mockk<Config>()
    private val newEmailer = mockk<Emailer>()
    private lateinit var newPipeline: Pipeline

    @BeforeEach
    fun setUp() {
        pipeline = Pipeline(config, emailer, log)
        newPipeline = Pipeline(newConfig, newEmailer, log)
    }

    @Test
    fun test_mockk_framework() {

        every{newConfig.sendEmailSummary()} returns true
        justRun{newEmailer.send("Deployment completed successfully")}
        val project = Project.builder()
            .setTestStatus(PASSING_TESTS)
            .setDeploysSuccessfully(true)
            .build()

        newPipeline.run(project)

        assertEquals(
            mutableListOf(
                "INFO: Tests passed",
                "INFO: Deployment successful",
                "INFO: Sending email"
            ), log.loggedLines
        )
       io.mockk.verify{newEmailer.send("Deployment completed successfully")}
    }

    @Test
    fun project_with_tests_that_deploys_successfully_with_email_notification() {

        `when`(config.sendEmailSummary()).thenReturn(true)
        val project = Project.builder()
            .setTestStatus(PASSING_TESTS)
            .setDeploysSuccessfully(true)
            .build()

        pipeline.run(project)

        assertEquals(
            mutableListOf(
                "INFO: Tests passed",
                "INFO: Deployment successful",
                "INFO: Sending email"
            ), log.loggedLines
        )
        verify(emailer).send("Deployment completed successfully")
    }

    @Test
    fun project_with_tests_that_deploys_successfully_without_email_notification() {

        `when`(config.sendEmailSummary()).thenReturn(false)
        val project = Project.builder()
            .setTestStatus(PASSING_TESTS)
            .setDeploysSuccessfully(true)
            .build()

        pipeline.run(project)

        assertEquals(
            mutableListOf(
                "INFO: Tests passed",
                "INFO: Deployment successful",
                "INFO: Email disabled"
            ), log.loggedLines
        )
        verify(emailer, never()).send(any())
    }

    @Test
    fun project_without_tests_that_deploys_successfully_with_email_notification() {

        `when`(config.sendEmailSummary()).thenReturn(true)
        val project = Project.builder()
            .setTestStatus(NO_TESTS)
            .setDeploysSuccessfully(true)
            .build()

        pipeline.run(project)

        assertEquals(
            mutableListOf(
                "INFO: No tests",
                "INFO: Deployment successful",
                "INFO: Sending email"
            ), log.loggedLines
        )
        verify(emailer).send("Deployment completed successfully")
    }

    @Test
    fun project_without_tests_that_deploys_successfully_without_email_notification() {

        `when`(config.sendEmailSummary()).thenReturn(false)

        val project = Project.builder()
            .setTestStatus(NO_TESTS)
            .setDeploysSuccessfully(true)
            .build()
        pipeline.run(project)

        assertEquals(
            mutableListOf(
                "INFO: No tests",
                "INFO: Deployment successful",
                "INFO: Email disabled"
            ), log.loggedLines
        )
        verify(emailer, never()).send(any())
    }

    @Test
    fun project_with_tests_that_fail_with_email_notification() {

        `when`(config.sendEmailSummary()).thenReturn(true)
        val project = Project.builder()
            .setTestStatus(FAILING_TESTS)
            .build()

        pipeline.run(project)

        assertEquals(
            mutableListOf(
                "ERROR: Tests failed",
                "INFO: Sending email"
            ), log.loggedLines
        )
        verify(emailer).send("Tests failed")
    }

    @Test
    fun project_with_tests_that_fail_without_email_notification() {

        `when`(config.sendEmailSummary()).thenReturn(false)
        val project = Project.builder()
            .setTestStatus(FAILING_TESTS)
            .build()

        pipeline.run(project)

        assertEquals(
            mutableListOf(
                "ERROR: Tests failed",
                "INFO: Email disabled"
            ), log.loggedLines
        )
        verify(emailer, never()).send(any())
    }

    @Test
    fun project_with_tests_and_failing_build_with_email_notification() {

        `when`(config.sendEmailSummary()).thenReturn(true)
        val project = Project.builder()
            .setTestStatus(PASSING_TESTS)
            .setDeploysSuccessfully(false)
            .build()

        pipeline.run(project)

        assertEquals(
            mutableListOf(
                "INFO: Tests passed",
                "ERROR: Deployment failed",
                "INFO: Sending email"
            ), log.loggedLines
        )
        verify(emailer).send("Deployment failed")
    }

    @Test
    fun project_with_tests_and_failing_build_without_email_notification() {

        `when`(config.sendEmailSummary()).thenReturn(false)
        val project = Project.builder()
            .setTestStatus(PASSING_TESTS)
            .setDeploysSuccessfully(false)
            .build()

        pipeline.run(project)

        assertEquals(
            mutableListOf(
                "INFO: Tests passed",
                "ERROR: Deployment failed",
                "INFO: Email disabled"
            ), log.loggedLines
        )
        verify(emailer, never()).send(any())
    }

    @Test
    fun project_without_tests_and_failing_build_with_email_notification() {

        `when`(config.sendEmailSummary()).thenReturn(true)
        val project = Project.builder()
            .setTestStatus(NO_TESTS)
            .setDeploysSuccessfully(false)
            .build()

        pipeline.run(project)

        assertEquals(
            mutableListOf(
                "INFO: No tests",
                "ERROR: Deployment failed",
                "INFO: Sending email"
            ), log.loggedLines
        )
        verify(emailer).send("Deployment failed")
    }

    @Test
    fun project_without_tests_and_failing_build_without_email_notification() {

        `when`(config.sendEmailSummary()).thenReturn(false)
        val project = Project.builder()
            .setTestStatus(NO_TESTS)
            .setDeploysSuccessfully(false)
            .build()

        pipeline.run(project)

        assertEquals(
            mutableListOf(
                "INFO: No tests",
                "ERROR: Deployment failed",
                "INFO: Email disabled"
            ), log.loggedLines
        )
        verify(emailer, never()).send(any())
    }

    @Test
    fun characterization_project_with_test_status_is_null() {

        `when`(config.sendEmailSummary()).thenReturn(false)
        val project = Project.builder()
            .setDeploysSuccessfully(false)
            .build()

        pipeline.run(project)

        assertEquals(
            mutableListOf(
                "ERROR: Tests failed",
                "INFO: Email disabled",
            ), log.loggedLines
        )
        verify(emailer, never()).send(any())
    }
}
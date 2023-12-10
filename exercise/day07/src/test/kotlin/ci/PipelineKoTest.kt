package ci

import ci.dependencies.Config
import ci.dependencies.Emailer
import ci.dependencies.Project
import ci.dependencies.TestStatus.*
import io.kotest.core.spec.style.StringSpec
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals

// 🛠️ Find and replace (regex mode)
// \`when\`\((.*)\)\.thenReturn\((.*)\)
//    every{ $1 } returns $2

// 🛠️ Find and replace (regex mode)
// verify\((.*)\)(.*)\)
// verify{ $1$2) }

// fun (.*)\(\) \{
// "$1" {
class PipelineKoTest : StringSpec({

    // Can you see my face and ear me?
    // i just see your code. trying to join call
    lateinit var log: CapturingLogger
    lateinit var config: Config
    lateinit var emailer: Emailer
    lateinit var pipeline: Pipeline

    beforeEach {
        log = CapturingLogger()
        config = mockk<Config>()
        emailer = mockk<Emailer>()
        pipeline = Pipeline(config, emailer, log)
    }

    "Project with tests that deploys successfully with email notification" {

        every { config.sendEmailSummary() } returns true
        justRun { emailer.send(any()) }
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
        verify { emailer.send("Deployment completed successfully") }
    }


    "Project with tests that deploys successfully without email notification" {

        every { config.sendEmailSummary() } returns false
        justRun { emailer.send(any()) }
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
        // Different options
        // verify(inverse = true) { emailer.send(any()) }
        // verify(exactly = 0) { emailer.send(any()) }
        // verify { emailer wasNot Called }
        verify(exactly = 0) { emailer.send(any()) }
    }

    "Project without tests that deploys successfully with email notification" {

        every { config.sendEmailSummary() } returns true
        justRun { emailer.send(any()) }
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
        verify { emailer.send("Deployment completed successfully") }
    }


    "Project without tests that deploys successfully without email notification" {

        every { config.sendEmailSummary() } returns false

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
        verify(exactly = 0) { emailer.send(any()) }
    }


    "Project with tests that fail with email notification" {

        every { config.sendEmailSummary() } returns true
        justRun { emailer.send(any()) }
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
        verify { emailer.send("Tests failed") }
    }


    "Project with tests that fail without email notification" {

        every { config.sendEmailSummary() } returns false
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
        verify(exactly = 0) { emailer.send(any()) }
    }


    "Project with tests and failing build with email notification" {

        every { config.sendEmailSummary() } returns true
        justRun { emailer.send(any()) }
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
        verify { emailer.send("Deployment failed") }
    }


    "Project with tests and failing build without email notification" {

        every { config.sendEmailSummary() } returns false
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
        verify(exactly = 0) { emailer.send(any()) }
    }


    "Project without tests and failing build with email notification" {

        every { config.sendEmailSummary() } returns true
        justRun { emailer.send(any()) }
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
        verify { emailer.send("Deployment failed") }
    }

    "Project without tests and failing build without email notification" {

        every { config.sendEmailSummary() } returns false
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
        verify(exactly = 0) { emailer.send(any()) }
    }

    "[Characterization] : project with test status is null" {

        every { config.sendEmailSummary() } returns false
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
        verify(exactly = 0) { emailer.send(any()) }
    }
})
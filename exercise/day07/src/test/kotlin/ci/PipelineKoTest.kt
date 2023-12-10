package ci

import ci.dependencies.Config
import ci.dependencies.Emailer
import ci.dependencies.Project
import ci.dependencies.TestStatus.PASSING_TESTS
import io.kotest.core.spec.style.StringSpec
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals

// TODO:
//  - migrate to kotest with mockk
//  - 💡HINT: Use all the techniques you've learned this week.

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

    "test_mockk_framework" {
        every { config.sendEmailSummary() } returns true
        justRun { emailer.send("Deployment completed successfully") }
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



    "project_with_tests_that_deploys_successfully_with_email_notification" {

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


    "project_with_tests_that_deploys_successfully_without_email_notification" {

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
// ne devrait pas être appelé
//  verify(emailer, never()).send(any())
//  https://www.baeldung.com/kotlin/mockk-check-method-invoked
// Different options
//        verify(inverse = true) { emailer.send(any()) }
//        verify(exactly = 0) { emailer.send(any()) }
//        verify { emailer wasNot Called }

        verify(exactly = 0) { emailer.send(any()) }
    }
//
//    
//    "project_without_tests_that_deploys_successfully_with_email_notification" {
//
//        every{ config.sendEmailSummary() } returns true
//        val project = Project.builder()
//            .setTestStatus(NO_TESTS)
//            .setDeploysSuccessfully(true)
//            .build()
//
//        pipeline.run(project)
//
//        assertEquals(
//            mutableListOf(
//                "INFO: No tests",
//                "INFO: Deployment successful",
//                "INFO: Sending email"
//            ), log.loggedLines
//        )
//        verify{ emailer.send("Deployment completed successfully") }
//    }
//
//    
//    "project_without_tests_that_deploys_successfully_without_email_notification" {
//
//        every{ config.sendEmailSummary() } returns false
//
//        val project = Project.builder()
//            .setTestStatus(NO_TESTS)
//            .setDeploysSuccessfully(true)
//            .build()
//        pipeline.run(project)
//
//        assertEquals(
//            mutableListOf(
//                "INFO: No tests",
//                "INFO: Deployment successful",
//                "INFO: Email disabled"
//            ), log.loggedLines
//        )
//        verify{ emailer, never()).send(any() }
//    }
//
//    
//    "project_with_tests_that_fail_with_email_notification" {
//
//        every{ config.sendEmailSummary() } returns true
//        val project = Project.builder()
//            .setTestStatus(FAILING_TESTS)
//            .build()
//
//        pipeline.run(project)
//
//        assertEquals(
//            mutableListOf(
//                "ERROR: Tests failed",
//                "INFO: Sending email"
//            ), log.loggedLines
//        )
//        verify{ emailer.send("Tests failed") }
//    }
//
//    
//    "project_with_tests_that_fail_without_email_notification" {
//
//        every{ config.sendEmailSummary() } returns false
//        val project = Project.builder()
//            .setTestStatus(FAILING_TESTS)
//            .build()
//
//        pipeline.run(project)
//
//        assertEquals(
//            mutableListOf(
//                "ERROR: Tests failed",
//                "INFO: Email disabled"
//            ), log.loggedLines
//        )
//        verify{ emailer, never()).send(any() }
//    }
//
//    
//    "project_with_tests_and_failing_build_with_email_notification" {
//
//        every{ config.sendEmailSummary() } returns true
//        val project = Project.builder()
//            .setTestStatus(PASSING_TESTS)
//            .setDeploysSuccessfully(false)
//            .build()
//
//        pipeline.run(project)
//
//        assertEquals(
//            mutableListOf(
//                "INFO: Tests passed",
//                "ERROR: Deployment failed",
//                "INFO: Sending email"
//            ), log.loggedLines
//        )
//        verify{ emailer.send("Deployment failed") }
//    }
//
//    
//    "project_with_tests_and_failing_build_without_email_notification" {
//
//        every{ config.sendEmailSummary() } returns false
//        val project = Project.builder()
//            .setTestStatus(PASSING_TESTS)
//            .setDeploysSuccessfully(false)
//            .build()
//
//        pipeline.run(project)
//
//        assertEquals(
//            mutableListOf(
//                "INFO: Tests passed",
//                "ERROR: Deployment failed",
//                "INFO: Email disabled"
//            ), log.loggedLines
//        )
//        verify{ emailer, never()).send(any() }
//    }
//
//    
//    "project_without_tests_and_failing_build_with_email_notification" {
//
//        every{ config.sendEmailSummary() } returns true
//        val project = Project.builder()
//            .setTestStatus(NO_TESTS)
//            .setDeploysSuccessfully(false)
//            .build()
//
//        pipeline.run(project)
//
//        assertEquals(
//            mutableListOf(
//                "INFO: No tests",
//                "ERROR: Deployment failed",
//                "INFO: Sending email"
//            ), log.loggedLines
//        )
//        verify{ emailer.send("Deployment failed") }
//    }
//
//    
//    "project_without_tests_and_failing_build_without_email_notification" {
//
//        every{ config.sendEmailSummary() } returns false
//        val project = Project.builder()
//            .setTestStatus(NO_TESTS)
//            .setDeploysSuccessfully(false)
//            .build()
//
//        pipeline.run(project)
//
//        assertEquals(
//            mutableListOf(
//                "INFO: No tests",
//                "ERROR: Deployment failed",
//                "INFO: Email disabled"
//            ), log.loggedLines
//        )
//        verify{ emailer, never()).send(any() }
//    }
//
//    
//    "characterization_project_with_test_status_is_null" {
//
//        every{ config.sendEmailSummary() } returns false
//        val project = Project.builder()
//            .setDeploysSuccessfully(false)
//            .build()
//
//        pipeline.run(project)
//
//        assertEquals(
//            mutableListOf(
//                "ERROR: Tests failed",
//                "INFO: Email disabled",
//            ), log.loggedLines
//        )
//        verify{ emailer, never()).send(any() }
//    }
})
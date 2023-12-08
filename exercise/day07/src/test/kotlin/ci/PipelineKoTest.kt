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

class PipelineKoTest : StringSpec ({

     val log = CapturingLogger()

     val config = mockk<Config>()
     val emailer = mockk<Emailer>()
     lateinit var pipeline: Pipeline

     beforeEach {
         pipeline = Pipeline(config, emailer, log)
     }


    "test_mockk_framework"() {

        every{config.sendEmailSummary()} returns true
        justRun{emailer.send("Deployment completed successfully")}
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
       verify{emailer.send("Deployment completed successfully")}
    }

//    @Test
//    fun project_with_tests_that_deploys_successfully_with_email_notification() {
//
//        `when`(config.sendEmailSummary()).thenReturn(true)
//        val project = Project.builder()
//            .setTestStatus(PASSING_TESTS)
//            .setDeploysSuccessfully(true)
//            .build()
//
//        pipeline.run(project)
//
//        assertEquals(
//            mutableListOf(
//                "INFO: Tests passed",
//                "INFO: Deployment successful",
//                "INFO: Sending email"
//            ), log.loggedLines
//        )
//        verify(emailer).send("Deployment completed successfully")
//    }
//
//    @Test
//    fun project_with_tests_that_deploys_successfully_without_email_notification() {
//
//        `when`(config.sendEmailSummary()).thenReturn(false)
//        val project = Project.builder()
//            .setTestStatus(PASSING_TESTS)
//            .setDeploysSuccessfully(true)
//            .build()
//
//        pipeline.run(project)
//
//        assertEquals(
//            mutableListOf(
//                "INFO: Tests passed",
//                "INFO: Deployment successful",
//                "INFO: Email disabled"
//            ), log.loggedLines
//        )
//        verify(emailer, never()).send(any())
//    }
//
//    @Test
//    fun project_without_tests_that_deploys_successfully_with_email_notification() {
//
//        `when`(config.sendEmailSummary()).thenReturn(true)
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
//        verify(emailer).send("Deployment completed successfully")
//    }
//
//    @Test
//    fun project_without_tests_that_deploys_successfully_without_email_notification() {
//
//        `when`(config.sendEmailSummary()).thenReturn(false)
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
//        verify(emailer, never()).send(any())
//    }
//
//    @Test
//    fun project_with_tests_that_fail_with_email_notification() {
//
//        `when`(config.sendEmailSummary()).thenReturn(true)
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
//        verify(emailer).send("Tests failed")
//    }
//
//    @Test
//    fun project_with_tests_that_fail_without_email_notification() {
//
//        `when`(config.sendEmailSummary()).thenReturn(false)
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
//        verify(emailer, never()).send(any())
//    }
//
//    @Test
//    fun project_with_tests_and_failing_build_with_email_notification() {
//
//        `when`(config.sendEmailSummary()).thenReturn(true)
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
//        verify(emailer).send("Deployment failed")
//    }
//
//    @Test
//    fun project_with_tests_and_failing_build_without_email_notification() {
//
//        `when`(config.sendEmailSummary()).thenReturn(false)
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
//        verify(emailer, never()).send(any())
//    }
//
//    @Test
//    fun project_without_tests_and_failing_build_with_email_notification() {
//
//        `when`(config.sendEmailSummary()).thenReturn(true)
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
//        verify(emailer).send("Deployment failed")
//    }
//
//    @Test
//    fun project_without_tests_and_failing_build_without_email_notification() {
//
//        `when`(config.sendEmailSummary()).thenReturn(false)
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
//        verify(emailer, never()).send(any())
//    }
//
//    @Test
//    fun characterization_project_with_test_status_is_null() {
//
//        `when`(config.sendEmailSummary()).thenReturn(false)
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
//        verify(emailer, never()).send(any())
//    }
})
package ci.dependencies

class Project private constructor(private val buildsSuccessfully: Boolean, private val testStatus: TestStatus?) {
    fun hasTests(): Boolean {
        return testStatus != TestStatus.NO_TESTS
    }

    fun runTests(): String {
        return if (testStatus == TestStatus.PASSING_TESTS) "success" else "failure"
    }

    fun deploy(): String {
        return if (buildsSuccessfully) "success" else "failure"
    }

    class ProjectBuilder {
        private var buildsSuccessfully = false
        private var testStatus: TestStatus? = null
        fun setTestStatus(testStatus: TestStatus?): ProjectBuilder {
            this.testStatus = testStatus
            return this
        }

        fun setDeploysSuccessfully(buildsSuccessfully: Boolean): ProjectBuilder {
            this.buildsSuccessfully = buildsSuccessfully
            return this
        }

        fun build(): Project {
            return Project(buildsSuccessfully, testStatus)
        }
    }

    companion object {
        fun builder(): ProjectBuilder {
            return ProjectBuilder()
        }
    }
}
package ci.dependencies

class Project private constructor(
    private val buildsSuccessfully: Boolean,
    private val testStatus: TestStatus

    // What is the meaning of null value here?
    //   Could we make it non nullable?
    //   Should we make a builder attribute non-nullable
    //   Could we use a default ? Should we ?


) {

    fun hasTests(): Boolean {
        return testStatus != TestStatus.NO_TESTS
    }

    fun hasNoTests(): Boolean {
        return !hasTests()
    }

    fun runTests(): String {
        return if (testStatus == TestStatus.PASSING_TESTS) "success" else "failure"
    }

    fun deploy(): String {
        return if (buildsSuccessfully) "success" else "failure"
    }

    class ProjectBuilder {
        private var buildsSuccessfully = false
        // EDIT : running characterization test shown that the current meanin of null is FAILING_TESTS
        // Default value null was replaced with FAILING_TESTS
        private var testStatus: TestStatus = TestStatus.FAILING_TESTS
        fun setTestStatus(testStatus: TestStatus): ProjectBuilder {
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

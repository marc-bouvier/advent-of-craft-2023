package blog

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSingleElement
import io.kotest.matchers.collections.shouldHaveSize
import kotlinx.datetime.*


class ArticleTests : StringSpec({

    // No assertion
    // What means "valid comment"? Are there invalid comments?
    "It should add valid comment" {
        val article = anArticle()
        article.addComment("Amazing article !!!", "Pablo Escobar")

        article.getComments() shouldHaveSize 1
    }

    // Asserts 2 things :
    // - adding an article
    // - text of the content
    "It should add a comment with the given text" {
        val article = anArticle()

        article.addComment(text = "Amazing article !!!", author = "Pablo Escobar")

        article.getComments() shouldHaveSingleElement { it.text == "Amazing article !!!" }
    }

    // Asserts 2 things :
    // - adding an article (already asserted)
    // - author of the content
    "It should add a comment with the given author" {
        val article = anArticle()

        article.addComment(text = "Amazing article !!!", author = "Pablo Escobar")

        article.getComments() shouldHaveSingleElement { it.author == "Pablo Escobar" }
    }

    // No assertion, no date is visible in this test
    "It should add a comment with the date of the day" {
        val timeContext = TimeContext(SpyClock(now()), timeZone())
        val article = anArticle(timeContext)

        article.addComment(text = "Amazing article !!!", author = "Pablo Escobar")
        article.getComments() shouldHaveSingleElement { it.creationDate == timeContext.nowAsLocalDate() }
    }

    // The behavior should be understandable by the business folks
    "It should throw an exception when adding existing comment" {
        val article = anArticle()

        article.addComment(text = "Amazing article !!!", author = "Pablo Escobar")

        shouldThrow<CommentAlreadyExist> {
            article.addComment(text = "Amazing article !!!", author = "Pablo Escobar")
        }
    }
})

private fun now() = Instant.parse("2023-12-06T21:57:43.145Z")

fun Instant.toLocalDate(timeZone: TimeZone): LocalDate {
    val localDateTime = this.toLocalDateTime(timeZone)
    val localDate = LocalDate(localDateTime.year, localDateTime.month, localDateTime.dayOfMonth)
    return localDate
}

class SpyClock(private val now: Instant) : Clock {
    override fun now(): Instant {
        return now
    }
}

data class TimeContext(val clock: Clock, val timeZone: TimeZone) {
    fun nowAsLocalDate(): LocalDate = clock.now().toLocalDate(timeZone)
}

private fun anArticle(timeContext: TimeContext = TimeContext(Clock.System, TimeZone.currentSystemDefault())): Article {
    return Article(
        "Lorem Ipsum",
        "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore",
        timeContext.clock,
        timeContext.timeZone
    )
}

private fun timeZone() = TimeZone.currentSystemDefault()



package blog

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSingleElement
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import test.utilities.SpyClock
import test.utilities.now
import test.utilities.timeZone
import time.TimeZonedClock
class `An article` : StringSpec({

    "can be commented" {
        val article = anArticle()

        article.addComment(text = "Amazing article !!!", author = "Pablo Escobar")

        article.comments() shouldHaveSingleElement {
            it.text == "Amazing article !!!"
                    && it.author == "Pablo Escobar"
        }
    }

    // No assertion, no date is visible in this test
    "is commented with the date of the day" {
        val time = TimeZonedClock(SpyClock(now()), timeZone())
        val article = anArticle(time)

        article.addComment(text = "Amazing article !!!", author = "Pablo Escobar")
        article.comments() shouldHaveSingleElement { it.creationDate == time.todayAsLocalDate() }
    }

    // The behavior should be understandable by the business folks
    "cannot have the exact same comment from the same author" {
        val article = anArticle()

        article.addComment(text = "Amazing article !!!", author = "Pablo Escobar")

        shouldThrow<CommentAlreadyExist> {
            article.addComment(text = "Amazing article !!!", author = "Pablo Escobar")
        }
    }
})



private fun anArticle(
    timeZonedClock: TimeZonedClock = TimeZonedClock(
        Clock.System,
        TimeZone.currentSystemDefault()
    )
): Article {
    return Article(
        "Lorem Ipsum",
        "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore",
        timeZonedClock
    )
}

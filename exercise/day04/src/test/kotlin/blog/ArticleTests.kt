package blog

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSingleElement


class ArticleTests : StringSpec({

    // No assertion
    "It should add valid comment" {
        shouldNotThrowAny {
            val article = Article(
                "Lorem Ipsum",
                "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
            )
            article.addComment("Amazing article !!!", "Pablo Escobar")
        }
    }

    // Asserts 2 things :
    // - adding an article
    // - text of the content
    "It should add a comment with the given text" {
        val article = Article(
            "Lorem Ipsum",
            "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        )
        val text = "Amazing article !!!"
        article.addComment(text, "Pablo Escobar")

        article.getComments() shouldHaveSingleElement { it.text == text }
    }

    // Asserts 2 things :
    // - adding an article (already asserted)
    // - author of the content
    "It should add a comment with the given author" {
        val article = Article(
            "Lorem Ipsum",
            "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        )
        val author = "Pablo Escobar"
        article.addComment("Amazing article !!!", author)

        article.getComments() shouldHaveSingleElement { it.author == author }

    }

    // No assertion, no date is visible in this test
    "It should add a comment with the date of the day" {
        val article = Article(
            "Lorem Ipsum",
            "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        )
        shouldNotThrowAny {
            article.addComment("Amazing article !!!", "Pablo Escobar")
        }
    }

    // The behavior should be understandable by the business folks
    "It should throw an exception when adding existing comment" {
        val article = Article(
            "Lorem Ipsum",
            "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        )
        article.addComment("Amazing article !!!", "Pablo Escobar")
        shouldThrow<CommentAlreadyExist> {
            article.addComment("Amazing article !!!", "Pablo Escobar")
        }
    }
})



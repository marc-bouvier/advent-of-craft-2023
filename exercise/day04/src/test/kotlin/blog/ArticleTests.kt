package blog

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSingleElement


class ArticleTests : StringSpec({

    // No assertion
    "It should add valid comment" {
        shouldNotThrowAny {
            val article = article()
            article.addComment("Amazing article !!!", "Pablo Escobar")
        }
    }

    // Asserts 2 things :
    // - adding an article
    // - text of the content
    "It should add a comment with the given text" {
        val article = anArticle()
        val text = "Amazing article !!!"
        article.addComment(text, "Pablo Escobar")

        article.getComments() shouldHaveSingleElement { it.text == text }
    }

    // Asserts 2 things :
    // - adding an article (already asserted)
    // - author of the content
    "It should add a comment with the given author" {
        val article = anArticle()
        val author = "Pablo Escobar"
        article.addComment("Amazing article !!!", author)

        article.getComments() shouldHaveSingleElement { it.author == author }

    }

    // No assertion, no date is visible in this test
    "It should add a comment with the date of the day" {
        val article = anArticle()
        shouldNotThrowAny {
            article.addComment("Amazing article !!!", "Pablo Escobar")
        }
    }

    // The behavior should be understandable by the business folks
    "It should throw an exception when adding existing comment" {
        val article = anArticle()
        article.addComment("Amazing article !!!", "Pablo Escobar")
        shouldThrow<CommentAlreadyExist> {
            article.addComment("Amazing article !!!", "Pablo Escobar")
        }
    }
})

private fun article(): Article {
    val article = anArticle()
    return article
}

private fun anArticle() = Article(
    "Lorem Ipsum",
    "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
)



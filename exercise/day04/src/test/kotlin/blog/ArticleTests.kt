package blog

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSingleElement


class ArticleTests : StringSpec({

    "it_should_add_valid_comment" {
        shouldNotThrowAny {
            val article = Article(
                "Lorem Ipsum",
                "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
            )
            article.addComment("Amazing article !!!", "Pablo Escobar")
        }
    }

    "it_should_add_a_comment_with_the_given_text" {
        val article = Article(
            "Lorem Ipsum",
            "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        )
        val text = "Amazing article !!!"
        article.addComment(text, "Pablo Escobar")

        article.getComments() shouldHaveSingleElement { it.text == text }
    }

    "it_should_add_a_comment_with_the_given_author" {
        val article = Article(
            "Lorem Ipsum",
            "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        )
        val author = "Pablo Escobar"
        article.addComment("Amazing article !!!", author)

        article.getComments() shouldHaveSingleElement { it.author == author }

    }

    "it_should_add_a_comment_with_the_date_of_the_day" {
        val article = Article(
            "Lorem Ipsum",
            "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        )
        shouldNotThrowAny {
            article.addComment("Amazing article !!!", "Pablo Escobar")
        }
    }

    "it_should_throw_an_exception_when_adding_existing_comment" {
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



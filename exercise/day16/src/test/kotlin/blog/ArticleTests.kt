package blog

import blog.ArticleBuilder.Companion.AUTHOR
import blog.ArticleBuilder.Companion.COMMENT_TEXT
import org.assertj.core.api.Assertions
import org.assertj.core.api.ThrowingConsumer
import org.instancio.Instancio
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.function.Function

internal class ArticleTests {
    private var article: Article? = null

    @Test
    @Throws(CommentAlreadyExistException::class)
    fun should_add_comment_in_an_article() {
        `when` { article: Article? ->
            article!!.addComment(
                COMMENT_TEXT,
                AUTHOR
            )
        }
        then { article: Article? ->
            Assertions.assertThat(article!!.comments).hasSize(1)
            assertComment(article.comments[0], COMMENT_TEXT, AUTHOR)
        }
    }

    @Test
    @Throws(Throwable::class)
    fun should_add_comment_in_an_article_containing_already_a_comment() {
        val newComment = Instancio.create(String::class.java)
        val newAuthor = Instancio.create(String::class.java)

        `when`(
            { obj: ArticleBuilder -> obj.commented() },
            { article: Article? -> article!!.addComment(newComment, newAuthor) })
        then { article: Article? ->
            Assertions.assertThat(article!!.comments).hasSize(2)
            assertComment(article.comments.last, newComment, newAuthor)
        }
    }

    @Nested
    internal inner class Fail {
        @Test
        @Throws(CommentAlreadyExistException::class)
        fun when__adding_an_existing_comment() {
            val article: Article = ArticleBuilder.anArticle()
                .commented()
                .build()

            Assertions.assertThatThrownBy {
                article.addComment(article.comments[0].text, article.comments[0].author)
            }.isInstanceOf(CommentAlreadyExistException::class.java)
        }
    }

    @Throws(CommentAlreadyExistException::class)
    private fun `when`(articleBuilder: ArticleBuilder?, act: ThrowingConsumer<Article?>) {
        article = articleBuilder!!.build()
        act.accept(article)
    }

    @Throws(CommentAlreadyExistException::class)
    private fun `when`(act: ThrowingConsumer<Article?>) {
        `when`(ArticleBuilder.anArticle(), act)
    }

    @Throws(Throwable::class)
    private fun `when`(options: Function<ArticleBuilder, ArticleBuilder?>, act: ThrowingConsumer<Article?>) {
        `when`(options.apply(ArticleBuilder.anArticle()), act)
    }

    private fun then(act: ThrowingConsumer<Article?>) {
        act.accept(article)
    }

    companion object {
        private fun assertComment(comment: Comment, commentText: String, author: String) {
            Assertions.assertThat(comment.text).isEqualTo(commentText)
            Assertions.assertThat(comment.author).isEqualTo(author)
        }
    }
}
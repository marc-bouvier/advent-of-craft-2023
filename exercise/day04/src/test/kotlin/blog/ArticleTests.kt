package blog

import io.kotest.core.spec.style.StringSpec
import org.assertj.core.api.Assertions

class ArticleTests: StringSpec({
     "it_should_add_valid_comment" {
         val article = Article(
             "Lorem Ipsum",
             "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
         )
         article.addComment("Amazing article !!!", "Pablo Escobar")
         // Missing assertion
     }

     "it_should_add_a_comment_with_the_given_text" {
         val article = Article(
             "Lorem Ipsum",
             "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
         )
         val text = "Amazing article !!!"
         article.addComment(text, "Pablo Escobar")
         Assertions.assertThat(article.getComments())
             .hasSize(1)
             .anyMatch { comment: Comment? -> comment?.text == text }
     }

     "it_should_add_a_comment_with_the_given_author"() {
         val article = Article(
             "Lorem Ipsum",
             "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
         )
         val author = "Pablo Escobar"
         article.addComment("Amazing article !!!", author)
         Assertions.assertThat(article.getComments())
             .hasSize(1)
             .anyMatch { comment: Comment? -> comment?.author == author }
     }

     "it_should_add_a_comment_with_the_date_of_the_day"() {
         val article = Article(
             "Lorem Ipsum",
             "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
         )
         article.addComment("Amazing article !!!", "Pablo Escobar")
     }

     "it_should_throw_an_exception_when_adding_existing_comment"() {
         val article = Article(
             "Lorem Ipsum",
             "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
         )
         article.addComment("Amazing article !!!", "Pablo Escobar")
         Assertions.assertThatThrownBy { article.addComment("Amazing article !!!", "Pablo Escobar") }.isInstanceOf(
             CommentAlreadyExist::class.java
         )
     }
 })



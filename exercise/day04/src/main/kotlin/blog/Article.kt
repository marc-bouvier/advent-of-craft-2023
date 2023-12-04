package blog

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn


class Article(
    private val name: String,
    private val content: String,
    private val clock: Clock
) {
    private val comments: MutableList<Comment> = mutableListOf()

    private fun addComment(text: String, author: String, creationDate: LocalDate) {

        val comment = Comment(text, author, creationDate)
        if (comments.contains(comment)) {
            throw CommentAlreadyExist()
        } else comments.add(comment)
    }

    fun addComment(text: String, author: String) {
        addComment(text, author, clock.todayIn(TimeZone.currentSystemDefault()))
    }

    fun getComments(): List<Comment> {
        return comments.toList()
    }
}

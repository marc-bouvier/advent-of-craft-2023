package blog

import kotlinx.datetime.LocalDate
import time.TimeZonedClock


class Article(
    private val name: String,
    private val content: String,
    private val clock:TimeZonedClock
) {
    private val comments: MutableList<Comment> = mutableListOf()

    fun addComment(text: String, author: String) {
        addComment(text, author, clock.todayAsLocalDate())
    }

    private fun addComment(text: String, author: String, creationDate: LocalDate) {

        val comment = Comment(text, author, creationDate)
        if (comments.contains(comment)) {
            throw CommentAlreadyExist()
        } else comments.add(comment)
    }

    fun comments(): List<Comment> {
        return comments.toList() // immutable list
    }
}

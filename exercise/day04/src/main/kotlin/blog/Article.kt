package blog

import kotlinx.datetime.LocalDate
import time.TimeZonedClock


class Article(
    private val name: String,
    private val content: String,
    private val time:TimeZonedClock
) {
    private val comments: MutableList<Comment> = mutableListOf()

    private fun addComment(text: String, author: String, creationDate: LocalDate) {

        val comment = Comment(text, author, creationDate)
        if (comments.contains(comment)) {
            throw CommentAlreadyExist()
        } else comments.add(comment)
    }

    fun addComment(text: String, author: String) {
        addComment(text, author, time.today())
    }

    fun getComments(): List<Comment> {
        return comments.toList()
    }
}

package blog

import java.time.LocalDate

class Article(private val name: String?, private val content: String?) {
    private val comments: ArrayList<Comment?>?

    init {
        comments = ArrayList()
    }

    @Throws(CommentAlreadyExistException::class)
    private fun addComment(
        text: String?,
        author: String?,
        creationDate: LocalDate?
    ) {
        val comment = Comment(text, author, creationDate)
        if (comments.contains(comment)) {
            throw CommentAlreadyExistException()
        } else comments.add(comment)
    }

    @Throws(CommentAlreadyExistException::class)
    fun addComment(text: String?, author: String?) {
        addComment(text, author, LocalDate.now())
    }

    fun getComments(): MutableList<Comment?>? {
        return comments
    }
}

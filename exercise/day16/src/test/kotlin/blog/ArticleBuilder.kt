package blog

class ArticleBuilder {
    private val comments = HashMap<String, String>()

    fun commented(): ArticleBuilder {
        comments[COMMENT_TEXT] = AUTHOR
        return this
    }

    @Throws(CommentAlreadyExistException::class)
    fun build(): Article {
        val article = Article(
            "Lorem Ipsum",
            "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        )
        for ((key, value) in comments) {
            article.addComment(key, value)
        }
        return article
    }

    companion object {
        const val AUTHOR: String = "Pablo Escobar"
        const val COMMENT_TEXT: String = "Amazing article !!!"
        fun anArticle(): ArticleBuilder {
            return ArticleBuilder()
        }
    }
}

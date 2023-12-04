package blog

import kotlinx.datetime.LocalDate


data class Comment(
    val text: String,
    val author: String,
    val creationDate: LocalDate
)

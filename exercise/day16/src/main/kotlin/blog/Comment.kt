package blog

import java.time.LocalDate

@JvmRecord
data class Comment(val text: String, val author: String, val creationDate: LocalDate)

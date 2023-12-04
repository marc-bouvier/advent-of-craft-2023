package blog

import java.time.LocalDate

data class Comment(val text: String, val author: String, val creationDate: LocalDate) {
}

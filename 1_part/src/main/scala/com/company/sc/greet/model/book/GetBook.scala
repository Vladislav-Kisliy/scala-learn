package com.company.sc.greet.model.book

import java.util.UUID

case class GetBookRequest(bookId: UUID)

sealed trait GetBookResponse
object GetBookResponse {
  case class Found(book: Book)      extends GetBookResponse
  case class NotFound(bookId: UUID) extends GetBookResponse
  case object CantGetBookWithoutId  extends GetBookResponse
}

package com.company.sc.greet.model.book

import java.util.UUID

case class UpdateBookRequest(book: Book)

sealed trait UpdateBookResponse
object UpdateBookResponse {
  case class Updated(book: Book)      extends UpdateBookResponse
  case class NotFound(bookId: UUID)   extends UpdateBookResponse
  case object CantUpdateBookWithoutId extends UpdateBookResponse
}

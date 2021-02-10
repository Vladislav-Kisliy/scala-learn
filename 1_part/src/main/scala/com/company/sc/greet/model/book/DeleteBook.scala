package com.company.sc.greet.model.book

import java.util.UUID

case class DeleteBookRequest(bookId: UUID)

sealed trait DeleteBookResponse
object DeleteBookResponse {
  case class Deleted(book: Book)    extends DeleteBookResponse
  case class NotFound(bookId: UUID) extends DeleteBookResponse
  case object CantGetBookWithoutId  extends DeleteBookResponse
}

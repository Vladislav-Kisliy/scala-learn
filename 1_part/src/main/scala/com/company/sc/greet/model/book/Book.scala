package com.company.sc.greet.model.book

import java.util.UUID

case class Book(
    id: Option[UUID],
    name: String,
    isbn: String,
    author: Author,
    genres: Set[Genre],
    publishYear: Int,
    pages: Int
)
object Book {
  type Name = String
  type Isbn = String
}

sealed trait Genre
object Genre {
  case object Classic   extends Genre
  case object Drama     extends Genre
  case object Detective extends Genre
  case object Fantasy   extends Genre
  case object Fiction   extends Genre
  case object Science   extends Genre
}

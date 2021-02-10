package com.company.sc.greet.model.book

import Book.{Isbn, Name}

sealed trait FindBooksRequest
object FindBooksRequest {
  case class ByName(name: Name)                                       extends FindBooksRequest
  case class ByIsbn(isbn: Isbn)                                       extends FindBooksRequest
  case class ByAuthor(author: Author)                                 extends FindBooksRequest
  case class ByGenres(genres: Set[Genre])                             extends FindBooksRequest
  case class StartWitnNameAndGenres(name: String, genres: Set[Genre]) extends FindBooksRequest
  case class ByPublishYear(publishYear: Int)                          extends FindBooksRequest
  case class ByPages(pages: Int)                                      extends FindBooksRequest
  case class ByPagesAndAuthorLimit(pages: Int, authorLimit: Int)      extends FindBooksRequest
}

sealed trait FindBooksResponse
object FindBooksResponse {
  case class Result(books: Seq[Book]) extends FindBooksResponse
  case class EmptyResult()            extends FindBooksResponse
}

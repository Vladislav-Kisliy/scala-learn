package com.company.sc.greet.model.book

sealed trait FindAuthorsRequest
object FindAuthorsRequest {
  case class ByPublishYear(publishYear: Int) extends FindAuthorsRequest
  case class ByPages(pages: Int)             extends FindAuthorsRequest
}

sealed trait FindAuthorsResponse
object FindAuthorsResponse {
  case class Result(Authors: Seq[Author]) extends FindAuthorsResponse
  case class EmptyResult()                extends FindAuthorsResponse
}

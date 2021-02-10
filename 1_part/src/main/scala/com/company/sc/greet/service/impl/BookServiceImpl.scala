package com.company.sc.greet.service.impl

import com.company.sc.greet.dao.BookDao
import com.company.sc.greet.model.book._
import com.company.sc.greet.service.BookService

class BookServiceImpl(dao: BookDao) extends BookService {
  def createBook(request: CreateBookRequest): CreateBookResponse =
    CreateBookResponse(dao.createBook(request.book))

  def getBook(request: GetBookRequest): GetBookResponse =
    dao
      .getBook(request.bookId)
      .map(GetBookResponse.Found(_))
      .getOrElse(GetBookResponse.NotFound(request.bookId))

  def updateBook(request: UpdateBookRequest): UpdateBookResponse =
    request.book.id match {
      case None => UpdateBookResponse.CantUpdateBookWithoutId
      case Some(bookId) =>
        dao
          .updateBook(request.book)
          .map(UpdateBookResponse.Updated(_))
          .getOrElse(UpdateBookResponse.NotFound(bookId))
    }

  def deleteBook(request: DeleteBookRequest): DeleteBookResponse =
    dao
      .deleteBook(request.bookId)
      .map(DeleteBookResponse.Deleted)
      .getOrElse(DeleteBookResponse.NotFound(request.bookId))

  def findBooks(request: FindBooksRequest): FindBooksResponse =
    request match {
      case FindBooksRequest.ByAuthor(author) =>
        FindBooksResponse.Result(
          dao.findByFunction(_.author.surname == author.surname)
        )
      case FindBooksRequest.ByIsbn(isbn) => FindBooksResponse.Result(dao.findByIsbn(isbn))
      case FindBooksRequest.ByName(name) => FindBooksResponse.Result(dao.findByName(name))
      case FindBooksRequest.ByPublishYear(year) =>
        FindBooksResponse.Result(dao.findByPublishYear(year))
      case FindBooksRequest.ByPages(pages) =>
        FindBooksResponse.Result(dao.findByFunction(_.pages == pages))
      case FindBooksRequest.ByPagesAndAuthorLimit(pages, authorLimit) =>
        FindBooksResponse.Result(dao.findByPagesAndAuthorLimit(pages, authorLimit))
      case _ => FindBooksResponse.EmptyResult()
    }

  override def findAuthors(request: FindAuthorsRequest): FindAuthorsResponse =
    request match {
      case FindAuthorsRequest.ByPages(pages) =>
        FindAuthorsResponse.Result(dao.findByFunction(_.pages == pages).map(_.author))
      case FindAuthorsRequest.ByPublishYear(publishYear) =>
        FindAuthorsResponse.Result(dao.findByPublishYear(publishYear).map(_.author))
      case _ => FindAuthorsResponse.EmptyResult()
    }
}

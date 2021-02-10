package com.company.sc.greet.dao.impl

import com.company.sc.greet.dao.BookDao
import com.company.sc.greet.model.book.{Book, Genre}

import java.util.UUID

class BookDaoMapImpl extends BookDao {
  private var books: Map[UUID, Book] = Map.empty

  def createBook(book: Book): Book = {
    val id         = UUID.randomUUID()
    val bookWithId = book.copy(id = Some(id))
    books += (id -> bookWithId)
    bookWithId
  }

  def getBook(bookId: UUID): Option[Book] = books.get(bookId)

  def updateBook(book: Book): Option[Book] =
    for {
      id <- book.id
      _  <- books.get(id)
    } yield {
      books += (id -> book)
      book
    }

  def deleteBook(bookId: UUID): Option[Book] =
    books.get(bookId) match {
      case Some(book) =>
        books -= bookId
        Some(book)
      case None => None
    }

  override def findByName(name: String): Seq[Book] =
    books.values.filter(_.name == name).toVector

  def findByAuthorSurname(authorSurname: String): Seq[Book] =
    books.values.filter(_.author.surname == authorSurname).toVector

  override def findByIsbn(isbn: String): Seq[Book] =
    books.values.filter(_.isbn == isbn).toVector

  override def findByGenres(genres: Set[Genre]): Seq[Book] =
    books.values.filter(book => genres.subsetOf(book.genres)).toVector

  override def findStartWitnNameAndGenres(prefix: String, genres: Set[Genre]): Seq[Book] =
    books.values
      .filter(_.name.startsWith(prefix))
      .filter(book => genres.subsetOf(book.genres))
      .toVector

  def findAll(): Seq[Book] = books.values.toVector

  override def findByPublishYear(publishYear: Int): Seq[Book] =
    books.values.filter(_.publishYear == publishYear).toVector

  override def findByFunction(func: Book => Boolean): Seq[Book] =
    books.values.filter(func(_)).toVector

  override def findByPagesAndAuthorLimit(pages: Int, authorLimit: Int): Seq[Book] = {
    val authors = books.values
      .filter(_.pages <= authorLimit)
      .groupBy(_.author)
    books.values
      .filter(book => authors.contains(book.author))
      .filter(_.pages >= pages)
      .toVector
  }
}

package com.company.sc.greet.dao

import com.company.sc.greet.model.book.{Book, Genre}

import java.util.UUID

trait BookDao {
  def createBook(Book: Book): Book

  def getBook(BookId: UUID): Option[Book]

  def updateBook(Book: Book): Option[Book]

  def deleteBook(BookId: UUID): Option[Book]

  def findByName(name: String): Seq[Book]

  def findByIsbn(isbn: String): Seq[Book]

  def findByPublishYear(publishYear: Int): Seq[Book]

  def findByFunction(func: Book => Boolean): Seq[Book]

  def findByGenres(genres: Set[Genre]): Seq[Book]

  def findStartWitnNameAndGenres(name: String, genres: Set[Genre]): Seq[Book]

  def findAll(): Seq[Book]

  def findByPagesAndAuthorLimit(pages: Int, authorLimit: Int): Seq[Book]
}

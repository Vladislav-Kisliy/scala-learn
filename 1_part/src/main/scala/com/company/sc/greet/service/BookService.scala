package com.company.sc.greet.service

import com.company.sc.greet.model.book._

trait BookService {
  def createBook(request: CreateBookRequest): CreateBookResponse
  def getBook(request: GetBookRequest): GetBookResponse
  def updateBook(request: UpdateBookRequest): UpdateBookResponse
  def deleteBook(request: DeleteBookRequest): DeleteBookResponse
  def findBooks(request: FindBooksRequest): FindBooksResponse
  def findAuthors(request: FindAuthorsRequest): FindAuthorsResponse
}

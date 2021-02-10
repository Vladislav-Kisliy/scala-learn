package com.company.sc

import com.company.sc.greet.dao.BookDao
import com.company.sc.greet.dao.impl.{BookDaoMapImpl, DictionaryDaoImpl, GreetingDaoImpl, LazyDaoImpl}
import com.company.sc.greet.model.book.Genre.Classic
import com.company.sc.greet.model.book._
import com.company.sc.greet.model._
import com.company.sc.greet.service.impl._
import com.company.sc.greet.service.{BookService, GreetingService}

trait App {
  def greet(request: GreetRequest): GreetResponse

  def createBook(request: CreateBookRequest): CreateBookResponse
  def getBook(request: GetBookRequest): GetBookResponse
  def updateBook(request: UpdateBookRequest): UpdateBookResponse
  def deleteBook(request: DeleteBookRequest): DeleteBookResponse
  def findBooks(request: FindBooksRequest): FindBooksResponse
}

object App {
  private class AppImpl(
      greeting: GreetingService,
      counterService: GreetingService,
      echoService: GreetingService,
      dictionaryService: GreetingService,
      lazyService: GreetingService,
      bookService: BookService
  ) extends App {
    def greet(request: GreetRequest): GreetResponse = {
      request.requestType match {
        case COUNTER    => counterService.greet(request)
        case GREETER    => greeting.greet(request)
        case ECHO       => echoService.greet(request)
        case DICTIONARY => dictionaryService.greet(request)
        case LAZY       => lazyService.greet(request)
        case _          => EmptyResponse
      }
    }
    def createBook(request: CreateBookRequest): CreateBookResponse = bookService.createBook(request)
    def getBook(request: GetBookRequest): GetBookResponse          = bookService.getBook(request)
    def updateBook(request: UpdateBookRequest): UpdateBookResponse = bookService.updateBook(request)
    def deleteBook(request: DeleteBookRequest): DeleteBookResponse = bookService.deleteBook(request)
    def findBooks(request: FindBooksRequest): FindBooksResponse    = bookService.findBooks(request)
  }

  def apply(): App = {
    val greetingDao       = new GreetingDaoImpl
    val greetingService   = new GreetingServiceImpl(greetingDao)
    val counterService    = new CountingGreetingService()
    val echoService       = new EchoGreetingService()
    val dictionaryService = new DictionaryGreetingService(new DictionaryDaoImpl())
    val lazyService       = new LazyGreetingService(new LazyDaoImpl())

    val bookDao: BookDao         = new BookDaoMapImpl
    val bookService: BookService = new BookServiceImpl(bookDao)
    bookDao.findStartWitnNameAndGenres("authorName", Set(Classic))

    new AppImpl(
      greetingService,
      counterService,
      echoService,
      dictionaryService,
      lazyService,
      bookService
    )
  }
}

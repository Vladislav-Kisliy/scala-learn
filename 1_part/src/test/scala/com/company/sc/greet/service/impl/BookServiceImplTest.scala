package com.company.sc.greet.service.impl

import com.company.sc.greet.dao.BookDao
import com.company.sc.greet.model.book.Genre.{Classic, Drama, Fantasy}
import com.company.sc.greet.model.book.{
  Author,
  Book,
  CreateBookRequest,
  CreateBookResponse,
  DeleteBookRequest,
  DeleteBookResponse,
  FindAuthorsRequest,
  FindAuthorsResponse,
  FindBooksRequest,
  FindBooksResponse,
  GetBookRequest,
  GetBookResponse,
  UpdateBookRequest,
  UpdateBookResponse
}

import java.util.UUID
import org.scalamock.scalatest.MockFactory
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers._

class BookServiceImplTest extends AnyFreeSpec with MockFactory {

  val book1 = Book(
    Some(UUID.randomUUID()),
    "Anna Karenina",
    "B0023EFB1O",
    Author(Option.empty, name = "Leo", surname = "Tolstoy"),
    Set(Classic, Drama),
    2004,
    864
  )
  val book2 = Book(
    Some(UUID.randomUUID()),
    "The Ultimate Hitchhiker's Guide to the Galaxy",
    "978-0345453747",
    Author(Option.empty, name = "Adam", surname = "Douglas"),
    Set(Fantasy),
    2002,
    832
  )

  "BookServiceTest tests" - {
    "createBook" - {
      "should create Book" in {
        val dao = mock[BookDao]
        val srv = new BookServiceImpl(dao)

        (dao.createBook _).expects(book1).returns(book2)

        srv.createBook(CreateBookRequest(book1)) shouldBe CreateBookResponse(book2)
      }
    }

    "getBook" - {
      "should return Book" in {
        val dao = mock[BookDao]
        val srv = new BookServiceImpl(dao)
        val id  = UUID.randomUUID()

        (dao.getBook _).expects(id).returns(Some(book1))

        srv.getBook(GetBookRequest(id)) shouldBe GetBookResponse.Found(book1)
      }

      "should return NotFound on unknown Book" in {
        val dao = mock[BookDao]
        val srv = new BookServiceImpl(dao)
        val id  = UUID.randomUUID()

        (dao.getBook _).expects(id).returns(None)

        srv.getBook(GetBookRequest(id)) shouldBe GetBookResponse.NotFound(id)
      }
    }

    "updateBook" - {
      "should update existing Book" in {
        val dao = mock[BookDao]
        val srv = new BookServiceImpl(dao)

        (dao.updateBook _).expects(book1).returns(Some(book2))

        srv.updateBook(UpdateBookRequest(book1)) shouldBe UpdateBookResponse.Updated(book2)
      }

      "should return NotFound on unknown Book" in {
        val dao = mock[BookDao]
        val srv = new BookServiceImpl(dao)

        (dao.updateBook _).expects(book1).returns(None)

        srv.updateBook(UpdateBookRequest(book1)) shouldBe UpdateBookResponse.NotFound(book1.id.get)
      }

      "should return CantUpdateBookWithoutId on Book without id" in {
        val dao  = mock[BookDao]
        val srv  = new BookServiceImpl(dao)
        val Book = book1.copy(id = None)

        srv.updateBook(UpdateBookRequest(Book)) shouldBe UpdateBookResponse.CantUpdateBookWithoutId
      }
    }

    "deleteBook" - {
      "should delete Book" in {
        val dao = mock[BookDao]
        val srv = new BookServiceImpl(dao)
        val id  = UUID.randomUUID()

        (dao.deleteBook _).expects(id).returns(Some(book1))

        srv.deleteBook(DeleteBookRequest(id)) shouldBe DeleteBookResponse.Deleted(book1)
      }

      "should return NotFound on unknown Book" in {
        val dao = mock[BookDao]
        val srv = new BookServiceImpl(dao)
        val id  = UUID.randomUUID()

        (dao.deleteBook _).expects(id).returns(None)

        srv.deleteBook(DeleteBookRequest(id)) shouldBe DeleteBookResponse.NotFound(id)
      }
    }

    "findBooks" - {
      "by author" - {
        "should return empty list" in {
          val dao    = mock[BookDao]
          val srv    = new BookServiceImpl(dao)
          val author = Author(Option.empty, name = "name", surname = "surname")

          (dao.findByFunction _)
            .expects(*)
            .returns(Seq.empty)

          srv.findBooks(FindBooksRequest.ByAuthor(author)) shouldBe FindBooksResponse
            .Result(
              Seq.empty
            )
        }

        "should return non-empty list" in {
          val dao    = mock[BookDao]
          val srv    = new BookServiceImpl(dao)
          val author = Author(Option.empty, name = "name", surname = "surname")

          (dao.findByFunction _).expects(*).returns(Seq(book1, book2))

          srv.findBooks(FindBooksRequest.ByAuthor(author)) shouldBe FindBooksResponse
            .Result(
              Seq(book1, book2)
            )
        }
      }
      "by publish year" - {
        "should return empty list" in {
          val dao         = mock[BookDao]
          val srv         = new BookServiceImpl(dao)
          val publishYear = 2010

          (dao.findByPublishYear _)
            .expects(publishYear)
            .returns(Seq.empty)

          srv.findBooks(FindBooksRequest.ByPublishYear(publishYear)) shouldBe FindBooksResponse
            .Result(
              Seq.empty
            )
        }

        "should return non-empty list" in {
          val dao         = mock[BookDao]
          val srv         = new BookServiceImpl(dao)
          val publishYear = 2010

          (dao.findByPublishYear _).expects(publishYear).returns(Seq(book1, book2))

          srv.findBooks(FindBooksRequest.ByPublishYear(publishYear)) shouldBe FindBooksResponse
            .Result(
              Seq(book1, book2)
            )
        }
      }

      "by pages" - {
        "should return empty list" in {
          val dao   = mock[BookDao]
          val srv   = new BookServiceImpl(dao)
          val pages = 200

          (dao.findByFunction _).expects(*).returns(Seq.empty)

          srv.findBooks(FindBooksRequest.ByPages(pages)) shouldBe FindBooksResponse
            .Result(
              Seq.empty
            )
        }

        "should return non-empty list" in {
          val dao   = mock[BookDao]
          val srv   = new BookServiceImpl(dao)
          val pages = 200

          (dao.findByFunction _).expects(*).returns(Seq(book1, book2))

          srv.findBooks(FindBooksRequest.ByPages(pages)) shouldBe FindBooksResponse
            .Result(
              Seq(book1, book2)
            )
        }
      }

    }
    "findAuthors" - {
      "by pages" - {
        "should return empty list" in {
          val dao   = mock[BookDao]
          val srv   = new BookServiceImpl(dao)
          val pages = 200

          (dao.findByFunction _).expects(*).returns(Seq.empty)

          srv.findAuthors(FindAuthorsRequest.ByPages(pages)) shouldBe FindAuthorsResponse
            .Result(
              Seq.empty
            )
        }

        "should return non-empty list" in {
          val dao   = mock[BookDao]
          val srv   = new BookServiceImpl(dao)
          val pages = 200

          (dao.findByFunction _).expects(*).returns(Seq(book1, book2))

          srv.findAuthors(FindAuthorsRequest.ByPages(pages)) shouldBe FindAuthorsResponse
            .Result(
              Seq(book1.author, book2.author)
            )
        }
      }
      "by publish year" - {
        "should return empty list" in {
          val dao         = mock[BookDao]
          val srv         = new BookServiceImpl(dao)
          val publishYear = 2010

          (dao.findByPublishYear _).expects(publishYear).returns(Seq.empty)

          srv.findAuthors(
            FindAuthorsRequest.ByPublishYear(publishYear)
          ) shouldBe FindAuthorsResponse
            .Result(
              Seq.empty
            )
        }

        "should return non-empty list" in {
          val dao         = mock[BookDao]
          val srv         = new BookServiceImpl(dao)
          val publishYear = 2010

          (dao.findByPublishYear _).expects(publishYear).returns(Seq(book1, book2))

          srv.findAuthors(
            FindAuthorsRequest.ByPublishYear(publishYear)
          ) shouldBe FindAuthorsResponse
            .Result(
              Seq(book1.author, book2.author)
            )
        }
      }
    }
  }
}

package com.company.sc.greet.dao

import com.company.sc.greet.model.book.{Author, Book, Genre}

import java.util.UUID
import org.scalacheck.Arbitrary._
import org.scalacheck.{Arbitrary, Gen}
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers._
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks

/**
  * Abstract test class that should bw inherited by tests for any BookDao implementation
  */
abstract class BookDaoTest(name: String, createDao: () => BookDao)
    extends AnyFreeSpec
    with ScalaCheckDrivenPropertyChecks {
  implicit val genGenre: Gen[Genre] =
    Gen.oneOf(Genre.Classic, Genre.Fantasy, Genre.Drama, Genre.Fiction)
  implicit val arbitraryGenre: Arbitrary[Genre] = Arbitrary(genGenre)
  implicit val genAuthor: Gen[Author] = for {
    id      <- Gen.option(Gen.uuid)
    name    <- Gen.alphaStr
    surname <- Gen.alphaStr
  } yield Author(id = id, name = name, surname = surname)
  implicit val arbitraryAuthor: Arbitrary[Author] = Arbitrary(genAuthor)

  implicit val genBook: Gen[Book] = for {
    id          <- Gen.option(Gen.uuid)
    name        <- Gen.alphaStr
    isbn        <- Gen.alphaNumStr
    author      <- arbitrary[Author]
    genres      <- arbitrary[Set[Genre]]
    publishYear <- Gen.posNum[Int]
    pages       <- Gen.posNum[Int]
  } yield Book(
    id = id,
    name = name,
    isbn = isbn,
    author = author,
    genres = genres,
    publishYear = publishYear,
    pages = pages
  )

  implicit val arbitraryBook: Arbitrary[Book] = Arbitrary(genBook)
  val testGenre                               = Set(Genre.Classic, Genre.Drama)

  trait init {
    val dao = createDao()
  }

  name - {
    "createBook" - {
      "create any number of Books" in new init {
        forAll { (books: Seq[Book], book: Book) =>
          books.foreach(dao.createBook)

          val createdBook = dao.createBook(book)
          createdBook.id shouldNot be(book.id)
          createdBook.id shouldNot be(None)

          createdBook shouldBe book.copy(id = createdBook.id)
        }
      }
    }

    "getBook" - {
      "get unknown Book" in new init {
        forAll { (books: Seq[Book], BookId: UUID) =>
          books.foreach(dao.createBook)

          dao.getBook(BookId) shouldBe None
        }
      }

      "get known Book" in new init {
        forAll { (books1: Seq[Book], book: Book, books2: Seq[Book]) =>
          books1.foreach(dao.createBook)
          val createdBook = dao.createBook(book)
          books2.foreach(dao.createBook)

          dao.getBook(createdBook.id.get) shouldBe Some(createdBook)
        }
      }
    }

    "updateBook" - {
      "update unknown Book - keep all Books the same" in new init {
        forAll { (books: Seq[Book], book: Book) =>
          val createdBooks = books.map(dao.createBook)

          dao.updateBook(book) shouldBe None

          createdBooks.foreach { u =>
            dao.getBook(u.id.get) shouldBe Some(u)
          }
        }
      }

      "update known Book - keep other Books the same" in new init {
        forAll { (books1: Seq[Book], book1: Book, book2: Book, books2: Seq[Book]) =>
          val createdBooks1 = books1.map(dao.createBook)
          val createdBook   = dao.createBook(book1)
          val createdBooks2 = books2.map(dao.createBook)

          val toUpdate = book2.copy(id = createdBook.id)
          dao.updateBook(toUpdate) shouldBe Some(toUpdate)
          dao.getBook(toUpdate.id.get) shouldBe Some(toUpdate)

          createdBooks1.foreach { u =>
            dao.getBook(u.id.get) shouldBe Some(u)
          }

          createdBooks2.foreach { u =>
            dao.getBook(u.id.get) shouldBe Some(u)
          }
        }
      }
    }

    "deleteBook" - {
      "delete unknown Book - keep all Books the same" in new init {
        forAll { (Books: Seq[Book], BookId: UUID) =>
          val createdBooks = Books.map(dao.createBook)

          dao.deleteBook(BookId) shouldBe None

          createdBooks.foreach { u =>
            dao.getBook(u.id.get) shouldBe Some(u)
          }
        }
      }

      "delete known Book - keep other Books the same" in new init {
        forAll { (Books1: Seq[Book], Book1: Book, Books2: Seq[Book]) =>
          val createdBooks1 = Books1.map(dao.createBook)
          val createdBook   = dao.createBook(Book1)
          val createdBooks2 = Books2.map(dao.createBook)

          dao.getBook(createdBook.id.get) shouldBe Some(createdBook)
          dao.deleteBook(createdBook.id.get) shouldBe Some(createdBook)
          dao.getBook(createdBook.id.get) shouldBe None

          createdBooks1.foreach { u =>
            dao.getBook(u.id.get) shouldBe Some(u)
          }

          createdBooks2.foreach { u =>
            dao.getBook(u.id.get) shouldBe Some(u)
          }
        }
      }

    }

    "findByName" in new init {
      forAll { (books1: Seq[Book], bookName: String) =>
        val withOtherName = books1.filterNot(_.name == bookName)
        val withName      = books1.filter(_.name == bookName)

        val createdWithOtherBookName = withOtherName.map(dao.createBook)
        val createdWithBookName      = withName.map(dao.createBook)

        createdWithOtherBookName.foreach { book =>
          book.id shouldNot be(None)
        }

        dao.findByName(bookName).toSet shouldBe createdWithBookName.toSet
      }
    }

    "findByIsbn" in new init {
      forAll { (books1: Seq[Book], isbn: String) =>
        val withOtherIsbn = books1.filterNot(_.isbn == isbn)
        val withIsbn      = books1.filter(_.isbn == isbn)

        withOtherIsbn.foreach(dao.createBook)
        val createdWithIsbn = withIsbn.map(dao.createBook)

        dao.findByIsbn(isbn).toSet shouldBe createdWithIsbn.toSet
      }
    }

    "findByAuthor" in new init {
      forAll { (books1: Seq[Book], author: Author, books2: Seq[Book]) =>
        val withOtherAuthor = books1.filterNot(_.author == author)
        val withAuthor      = books2.map(_.copy(author = author))

        withOtherAuthor.foreach(dao.createBook)
        val createdWithAuthorName = withAuthor.map(dao.createBook)

        dao
          .findByFunction(_.author == author)
          .toSet shouldBe createdWithAuthorName.toSet
      }
    }

    "findByAuthorSurname" in new init {
      forAll { (books1: Seq[Book], surname: String, books2: Seq[Book]) =>
        val dao               = createDao()
        val withOtherAuthor   = books1.filterNot(_.author.surname == surname)
        val author            = Author(Option.empty, name = "authorName", surname = surname)
        val withAuthorSurname = books2.map(_.copy(author = author))

        withOtherAuthor.foreach(dao.createBook)
        val createdWithAuthSurname = withAuthorSurname.map(dao.createBook)

        dao.findByFunction(_.author.surname == surname).toSet shouldBe createdWithAuthSurname.toSet
      }
    }

    "findByPublishYear" in {
      forAll { (books1: Seq[Book], publishYear: Int) =>
        val dao                  = createDao()
        val withOtherPublishYear = books1.filterNot(_.publishYear == publishYear)
        val withPublishYear      = books1.filter(_.publishYear == publishYear)

        withOtherPublishYear.foreach(dao.createBook)
        val createdWithPublishYear = withPublishYear.map(dao.createBook)

        dao.findByPublishYear(publishYear).toSet shouldBe createdWithPublishYear.toSet
      }
    }

    "findByPages" in {
      forAll { (books1: Seq[Book], pages: Int, books2: Seq[Book]) =>
        val dao            = createDao()
        val withOtherPages = books1.filterNot(_.pages == pages)
        val withPages      = books2.map(_.copy(pages = pages))

        withOtherPages.foreach(dao.createBook)
        val createdWithPublishYear        = withPages.map(dao.createBook)
        val filterFunc: (Book => Boolean) = _.pages == pages
        dao.findByFunction(filterFunc).toSet shouldBe createdWithPublishYear.toSet
      }
    }

    "findByGenre" in {
      forAll { (books1: Seq[Book], genres: Set[Genre], books2: Seq[Book]) =>
        val dao             = createDao()
        val withOtherGenres = books1.filterNot(book => genres.subsetOf(book.genres))
        val withGenres      = books2.map(_.copy(genres = genres))

        withOtherGenres.foreach(dao.createBook)
        val createdWithGenres = withGenres.map(dao.createBook)

        dao.findByGenres(genres).toSet shouldBe createdWithGenres.toSet
      }
    }

    "findStartWitnNameAndGenres" in {
      forAll {
        (
            books1: Seq[Book],
            genres: Seq[Genre],
            books2: Seq[Book],
            books3: Seq[Book]
        ) =>
          val dao      = createDao()
          val bookName = "Fyodor"
          val withOtherAuthorGenres = books1
            .filterNot(_.name.startsWith(bookName))
          val genreItems = books3.map(_.copy(genres = genres.toSet))
          val correctItems =
            books2.map(_.copy(name = bookName + "in the Sky", genres = genres.toSet))

          withOtherAuthorGenres.foreach(dao.createBook)
          genreItems.foreach(dao.createBook)
          val createdCorrectedItems = correctItems.map(dao.createBook)

          dao
            .findStartWitnNameAndGenres(bookName, genres.toSet)
            .toSet shouldBe createdCorrectedItems.toSet
      }
    }

    "findByPagesAndAuthorLimit" in {
      forAll {
        (
            books1: Seq[Book],
            books2: Seq[Book],
            pages: Int,
            authorLimit: Int
        ) =>
          val dao                  = createDao()
          val withOtherPublishYear = books1.filterNot(_.pages == pages)
          val author               = Author(Option(UUID.randomUUID()), "Ivan", "Petrov")
          val withPagesAndAuthor   = books2.filter(_.pages == pages).map(_.copy(author = author))
          val withPagesAndAuthorLess =
            books2.filter(_.pages <= authorLimit).map(_.copy(author = author))

          withOtherPublishYear.foreach(dao.createBook)
          val createdWithPagesAndAuthor = withPagesAndAuthor.map(dao.createBook)
          withPagesAndAuthorLess.foreach(dao.createBook)
          dao
            .findByPagesAndAuthorLimit(pages, authorLimit)
            .toSet shouldBe createdWithPagesAndAuthor.toSet
      }
    }

    "findAll" in {
      forAll { Books: Seq[Book] =>
        val dao          = createDao()
        val createdBooks = Books.map(dao.createBook)

        dao.findAll().toSet shouldBe createdBooks.toSet
      }
    }
  }
}

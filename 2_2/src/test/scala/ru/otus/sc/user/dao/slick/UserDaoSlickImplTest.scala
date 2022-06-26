package ru.otus.sc.user.dao.slick

import com.dimafeng.testcontainers.{ForAllTestContainer, PostgreSQLContainer}
import ru.otus.sc.Config
import ru.otus.sc.db.Migrations
import ru.otus.sc.user.dao.{UserDao, UserDaoTest}
import ru.otus.sc.user.model.User
import slick.jdbc.JdbcBackend.Database

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class UserDaoSlickImplTest extends UserDaoTest("UserDaoDoobieImplTrest") with ForAllTestContainer {

  override val container: PostgreSQLContainer = PostgreSQLContainer()

  var db: Database = _

  override def afterStart(): Unit = {
    super.afterStart()
    new Migrations(Config(container.jdbcUrl, container.username, container.password))
      .applyMigrationsSync()

    db = Database.forURL(container.jdbcUrl, container.username, container.password)
  }

  override def beforeStop(): Unit = {
    db.close()
    super.beforeStop()
  }

  def createEmptyDao(): UserDao = {
    val dao                = new UserDaoSlickImpl(db)
    val user: Future[User] = dao createUser User(Option.empty, "", "", 1, Set.empty)
    user.futureValue
    dao.deleteAll().futureValue
    dao
  }
}

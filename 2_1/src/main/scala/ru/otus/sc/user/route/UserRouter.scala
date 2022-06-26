package ru.otus.sc.user.route

import akka.event.Logging
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.directives.DebuggingDirectives
import ru.otus.sc.route.BaseRouter
import ru.otus.sc.user.model.{CreateUserRequest, GetUserRequest, GetUserResponse, User}
import ru.otus.sc.user.service.UserService
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport._
import ru.otus.sc.user.json.UserJsonProtocol._

class UserRouter(service: UserService) extends BaseRouter {
  def route: Route =
    pathPrefix("user") {
      logRequest("user", Logging.InfoLevel)
      getUser ~
        createUser
    }

  private val UserIdRequest = JavaUUID.map(GetUserRequest)
  DebuggingDirectives.logRequest(("get-user", Logging.InfoLevel))(route)

  private def getUser: Route =
    (get & path(UserIdRequest)) { userIdRequest =>
      logRequest("getUser", Logging.InfoLevel)
      service.getUser(userIdRequest) match {
        case GetUserResponse.Found(user) =>
          complete(user)
        case GetUserResponse.NotFound(_) =>
          complete(StatusCodes.NotFound)
      }
    }

  /*
   * CRUD:
   *
   * POST - without ID
   * PUT - with ID
   *
   * /users - add new - POST
   * /users/{ID} - add/update - PUT
   * Create - /users - POST
   * Read - /users GET - all
   * Read - /users/{ID} GET - one
   * Update /users/{ID} PUT
   * Delete - DELETE
   *
   *
   * */

  private def createUser: Route =
    (post & entity(as[User])) { user =>
      logRequest("createUser", Logging.InfoLevel)
      val response = service.createUser(CreateUserRequest(user))
      complete(response.user)
    }
}

package ru.otus.sc.route
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

class Router(greetRouter: BaseRouter, userRouter: BaseRouter) extends BaseRouter {
  def route: Route =
    pathPrefix("api" / "v1") {
      concat(
        greetRouter.route,
        userRouter.route
      )
    }
}

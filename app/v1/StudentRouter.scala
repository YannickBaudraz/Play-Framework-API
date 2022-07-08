package v1

import play.api.routing.Router.Routes
import play.api.routing.{Router, SimpleRouter}
import play.api.routing.sird._

import javax.inject.{Inject, Singleton}

class StudentRouter @Inject() (controller: StudentController) extends SimpleRouter {
  override def routes: Routes = {
    case GET(p"/") =>
      controller.index
    case GET(p"/${int(id)}") =>
      controller.show(id)
    case POST(p"/") =>
      controller.create
    case PUT(p"/${int(id)}") =>
      controller.update(id)
    case DELETE(p"/${int(id)}") =>
      controller.destroy(id)
  }
}

object StudentRouter

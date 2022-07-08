package api

import api.v1.StudentRouter
import play.api.libs.json.Json
import play.api.mvc._

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext

@Singleton
class PingController @Inject() (val cc: ControllerComponents, studentRouter: StudentRouter)(implicit
    ec: ExecutionContext
) extends AbstractController(cc) {

  def ping: Action[AnyContent] = Action {
    val response = Json.obj(
      "ping" -> "pong",
      "apiVersion" -> "v1",
    )

    Ok(response)
  }
}

package api

import play.api.Configuration
import play.api.libs.json.Json
import play.api.mvc._

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext

@Singleton
class InformationController @Inject() (
    val cc: ControllerComponents,
    config: Configuration
)(implicit ec: ExecutionContext)
    extends AbstractController(cc) {

  def info: Action[AnyContent] = Action {
    val response = Json.obj(
      "application" -> Json.obj(
        "name" -> BuildInfo.name,
        "version" -> BuildInfo.version,
        "author" -> Json.obj(
          "name" -> config.get[String]("application.author.name"),
          "email" -> config.get[String]("application.author.email")
        )
      ),
      "api" -> Json.obj(
        "version" -> config.get[String]("api.version")
      )
    )

    Ok(response)
  }
}

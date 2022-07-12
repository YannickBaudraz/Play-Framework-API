import play.api._
import play.api.http.DefaultHttpErrorHandler
import play.api.http.Status._
import play.api.libs.json.{JsObject, Json}
import play.api.mvc.Results._
import play.api.mvc._
import play.api.routing.Router

import javax.inject.{Inject, Provider, Singleton}
import scala.concurrent._

@Singleton class ErrorHandler @Inject() (
    environment: Environment,
    configuration: Configuration,
    sourceMapper: OptionalSourceMapper,
    router: Provider[Router]
) extends DefaultHttpErrorHandler(environment, configuration, sourceMapper, router)
    with Logging {

  override def onClientError(request: RequestHeader, statusCode: Int, message: String = ""): Future[Result] = {
    logger.error(s"onClientError: statusCode = $statusCode, uri = ${request.uri}, message = $message")

    Future.successful(statusCode match {
      case BAD_REQUEST => BadRequest(JsonError("Bad Request", message))
      case FORBIDDEN   => Forbidden(JsonError("Forbidden", message))
      case NOT_FOUND   => NotFound(JsonError("Not Found", message))
      case clientError
          if statusCode >= 400
            && statusCode < 500 =>
        Status(statusCode)(JsonError(statusCode.toString, message))
      case nonServerError if statusCode >= 500 =>
        val msg = s"onClientError invoked with non client error status code $statusCode: $message"
        throw new IllegalArgumentException(msg)
      case _ => throw new IllegalArgumentException(s"onClientError invoked with non error status code $statusCode")
    })
  }

  override protected def onDevServerError(request: RequestHeader, exception: UsefulException): Future[Result] = {
    logger.error(s"onServerError: uri = ${request.uri}, message = ${exception.getMessage}")

    exception.getCause match {
      case e: NoSuchElementException => Future.successful(NotFound(JsonError("Not Found", e.getMessage)))
      case _                         => Future.successful(InternalServerError(JsonError("Internal Server Error", exception.getMessage)))
    }
  }

  override protected def onProdServerError(request: RequestHeader, exception: UsefulException): Future[Result] = {
    logger.error(s"onServerError: uri = ${request.uri}, message = ${exception.getMessage}")

    Future.successful(InternalServerError)
  }

  private object JsonError {
    def apply(code: String, message: String): JsObject = Json.obj("code" -> code, "message" -> message)
  }
}

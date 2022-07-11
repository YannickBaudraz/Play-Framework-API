package api.v1.controller

import api.v1.model.{BaseModel, School, Student, StudentWithSchool}
import api.v1.service.Service
import play.api.libs.json.Format._
import play.api.libs.json.Json.toJson
import play.api.libs.json.OFormat._
import play.api.libs.json._
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

import javax.inject.{Inject, Singleton}
import scala.concurrent._
import scala.reflect.runtime.universe._

@Singleton
abstract class ApiController[M <: BaseModel] @Inject() (
    cc: ControllerComponents,
    service: Service
)(implicit ec: ExecutionContext, tag: TypeTag[M])
    extends AbstractController(cc)
    with SimpleRouter {

  private val modelName = tag.tpe.typeSymbol.name.toString

  override def routes: Routes = {
    case GET(p"/")              => index
    case GET(p"/${int(id)}")    => show(id)
    case POST(p"/")             => create
    case PUT(p"/${int(id)}")    => update(id)
    case DELETE(p"/${int(id)}") => destroy(id)
  }

  def index: Action[AnyContent] = Action.async {
    service
      .list()
      .map(model => Ok(toJson(model)))
  }

  def show(id: Int): Action[AnyContent] = Action.async {
    service
      .get(id)
      .map(model => Ok(toJson(model)))
  }

  def create: Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body
      .validate[M]
      .fold(
        _ => Future.successful(BadRequest(s"Invalid $modelName provided")),
        model => {
          service
            .create(model)
            .map(school => Created(toJson(school)))
        }
      )
  }

  def update(id: Int): Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body
      .validate[M]
      .fold(
        _ => Future.successful(BadRequest(s"Invalid $modelName provided")),
        model => {
          service
            .update(model.copy(Some(id)))
            .map(school => Ok(toJson(school)))
        }
      )
  }

  def destroy(id: Int): Action[AnyContent] = Action.async {
    service
      .delete(id)
      .map(_ => Ok)
  }

  implicit def format: OFormat[M] = {
    val modelFormat = tag.tpe match {
      case t if t =:= typeOf[Student] => BaseModel.formatStudent
      case t if t =:= typeOf[School] => BaseModel.formatSchool
      case t if t =:= typeOf[StudentWithSchool] => BaseModel.formatStudentWithSchool
      case _ => throw new IllegalArgumentException(s"Invalid model type: ${tag.tpe}")
    }
    modelFormat.asInstanceOf[OFormat[M]]
  }
}

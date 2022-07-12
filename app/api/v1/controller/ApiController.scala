package api.v1.controller

import api.v1.model.{BaseModel, BaseModelCompanion}
import api.v1.service.Service
import play.api.libs.json.Json.toJson
import play.api.libs.json.OFormat._
import play.api.libs.json._
import play.api.mvc._
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

import javax.inject.{Inject, Singleton}
import scala.concurrent._
import scala.reflect.runtime.universe._

@Singleton
abstract class ApiController[Model <: BaseModel] @Inject() (
    cc: ControllerComponents,
    service: Service
)(implicit ec: ExecutionContext, modelTypeTag: TypeTag[Model])
    extends AbstractController(cc)
    with SimpleRouter {

  private val modelName = modelTypeTag.tpe.typeSymbol.name.toString

  override def routes: Routes = {
    case GET(p"/")              => index
    case GET(p"/${int(id)}")    => show(id)
    case POST(p"/")             => create
    case PUT(p"/${int(id)}")    => update(id)
    case DELETE(p"/${int(id)}") => destroy(id)
  }

  /** GET /
    * @return JSON array of all models
    */
  def index: Action[AnyContent] = Action.async {
    service
      .list()
      .map(models => Ok(toJson(models)))
  }

  /** GET /:id
    * @param id The id of the model to show
    * @return JSON of the model in a 200 response
    */
  def show(id: Int): Action[AnyContent] = Action.async {
    service
      .get(id)
      .map(model => Ok(toJson(model)))
  }

  /** POST /
    * The body of the request should be a JSON object of the model to create.
    * @return JSON of the created model in a 201 response
    */
  def create: Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body
      .validate[Model]
      .fold(
        _ => Future.successful(BadRequest(s"Invalid $modelName provided")),
        model => {
          service
            .create(model)
            .map(model => Created(toJson(model)))
        }
      )
  }

  /** PUT /:id
    * The body of the request should be a JSON object of the model to update.
    * @param id The id of the model to update
    * @return JSON of the updated model in a 200 response
    */
  def update(id: Int): Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body
      .validate[Model]
      .fold(
        _ => Future.successful(BadRequest(s"Invalid $modelName provided")),
        model => {
          service
            .update(model.copy(Some(id)))
            .map(model => Ok(toJson(model)))
        }
      )
  }

  /** DELETE /:id
    * @param id The id of the model to delete
    * @return A 204 response
    */
  def destroy(id: Int): Action[AnyContent] = Action.async {
    service
      .delete(id)
      .map(_ => NoContent)
  }

  implicit private def format: OFormat[Model] = {
    val companion = modelTypeTag.tpe.typeSymbol.companion.asModule
    val moduleMirror = runtimeMirror(getClass.getClassLoader).reflectModule(companion)
    val instance = moduleMirror.instance.asInstanceOf[BaseModelCompanion[Model]]

    instance.format
  }
}

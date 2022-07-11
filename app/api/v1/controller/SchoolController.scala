package api.v1.controller

import api.v1.model.School
import api.v1.service.SchoolService
import play.api.libs.json.Json.toJson
import play.api.libs.json.{JsValue, Json, OFormat}
import play.api.mvc._
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class SchoolController @Inject() (
    val cc: ControllerComponents,
    schoolService: SchoolService
)(implicit ec: ExecutionContext)
    extends AbstractController(cc)
    with SimpleRouter {

  implicit val schoolFormat: OFormat[School] = Json.format[School]

  override def routes: Routes = {
    case GET(p"/")              => index
    case GET(p"/${int(id)}")    => show(id)
    case POST(p"/")             => create
    case PUT(p"/${int(id)}")    => update(id)
    case DELETE(p"/${int(id)}") => destroy(id)
  }

  /** GET /api/v1/schools
    * @return A list of schools.
    */
  def index: Action[AnyContent] = Action.async {
    schoolService
      .list()
      .map(students => Ok(toJson(students)))
  }

  /** GET /api/v1/schools/:id
    * @param id The id of the school to get.
    * @return The school with the given id, or a 404 if not found.
    */
  def show(id: Int): Action[AnyContent] = Action.async {
    schoolService
      .get(id)
      .map(school => Ok(toJson(school)))
  }

  /** POST /api/v1/schools<br>
    * The body of the request should be a JSON object of type [[School]].
    *
    * @return The created school.
    */
  def create: Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body
      .validate[School]
      .fold(
        _ => Future.successful(BadRequest("Invalid school provided")),
        school => {
          schoolService
            .create(school)
            .map(school => Created(toJson(school)))
        }
      )
  }

  /** PUT /api/v1/schools/:id<br>
    * The body of the request should be a JSON object of type [[School]].
    *
    * @param id The id of the school to update.
    * @return The updated school, or a 404 if not found.
    */
  def update(id: Int): Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body
      .validate[School]
      .fold(
        _ => Future.successful(BadRequest("Invalid school provided")),
        school => {
          schoolService
            .update(school.copy(id = Some(id)))
            .map(school => Ok(toJson(school)))
        }
      )
  }

  /** DELETE /api/v1/schools/:id
    * @param id The id of the school to delete.
    * @return A status code of 205 if the school was deleted, or a 404 if not found.
    */
  def destroy(id: Int): Action[AnyContent] = Action.async {
    schoolService
      .delete(id)
      .map(_ => ResetContent)
  }
}

object SchoolController

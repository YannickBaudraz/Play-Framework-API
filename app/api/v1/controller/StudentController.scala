package api.v1.controller

import api.v1.model.{School, Student, StudentWithSchool}
import api.v1.service.StudentService
import play.api.libs.json.Json.toJson
import play.api.libs.json.{JsValue, Json, OFormat}
import play.api.mvc._
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class StudentController @Inject() (
    val cc: ControllerComponents,
    studentService: StudentService
)(implicit ec: ExecutionContext)
    extends AbstractController(cc)
    with SimpleRouter {

  implicit val studentFormat: OFormat[Student] = Json.format[Student]
  implicit val schoolFormat: OFormat[School] = Json.format[School]
  implicit val studentWithSchoolFormat: OFormat[StudentWithSchool] = Json.format[StudentWithSchool]

  override def routes: Routes = {
    case GET(p"/")              => index
    case GET(p"/${int(id)}")    => show(id)
    case POST(p"/")             => create
    case PUT(p"/${int(id)}")    => update(id)
    case DELETE(p"/${int(id)}") => destroy(id)
  }

  /** GET /api/v1/students
    * @return A list of students.
    */
  def index: Action[AnyContent] = Action.async {
    studentService.list().map { students =>
      Ok(toJson(students))
    }
  }

  /** GET /api/v1/students/:id
    * @param id The id of the student to get.
    * @return The student with the given id, or a 404 if not found.
    */
  def show(id: Int): Action[AnyContent] = Action.async {
    studentService
      .get(id)
      .map(student => Ok(toJson(student)))
  }

  /** POST /api/v1/students<br>
    * The body of the request should be a JSON object of type [[Student]].
    *
    * @return The created student.
    */
  def create: Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body
      .validate[Student]
      .fold(
        _ => Future.successful(BadRequest("Invalid student provided")),
        student =>
          studentService
            .create(student)
            .map(student => Created(toJson(student)))
      )
  }

  /** PUT /api/v1/students/:id<br>
    * The body of the request should be a JSON object of type [[Student]].
    *
    * @param id The id of the student to update.
    * @return The updated student.
    */
  def update(id: Int): Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body
      .validate[Student]
      .fold(
        _ => Future.successful(BadRequest("Invalid student provided")),
        student =>
          studentService
            .update(student.copy(id = Some(id)))
            .map(student => Ok(toJson(student)))
      )
  }

  /** DELETE /api/v1/students/:id
    * @param id The id of the student to delete.
    * @return An OK response if the student was deleted.
    */
  def destroy(id: Int): Action[AnyContent] = Action.async {
    studentService
      .delete(id)
      .map(_ => Ok("Student deleted"))
  }
}

object StudentController

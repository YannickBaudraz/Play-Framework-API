package api.v1

import play.api.libs.json.Json.toJson
import play.api.libs.json.{JsValue, Json, OFormat}
import play.api.mvc._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class StudentController @Inject() (
    val controllerComponents: ControllerComponents,
    studentService: StudentService
)(implicit ec: ExecutionContext)
    extends BaseController {

  implicit val studentDTOJson: OFormat[StudentDTO] = Json.format[StudentDTO]
  implicit val studentJson: OFormat[Student] = Json.format[Student]

  def index: Action[AnyContent] = Action.async {
    studentService.list().map { students =>
      Ok(toJson(students))
    }
  }

  def show(id: Int): Action[AnyContent] = Action.async {
    studentService
      .get(id)
      .map(student => Ok(toJson(student)))
      .recover { case e: NoSuchElementException => NotFound(e.getMessage) }
  }

  def create: Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body
      .validate[StudentDTO]
      .fold(
        _ => Future.successful(BadRequest("Invalid student provided")),
        studentDTO =>
          studentService
            .create(studentDTO)
            .map(student => Created(toJson(student)))
            .recover { case e: NoSuchElementException => NotFound(e.getMessage) }
      )
  }

  def update(id: Int): Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body
      .validate[StudentDTO]
      .fold(
        _ => Future.successful(BadRequest("Invalid student provided")),
        studentDTO =>
          studentService
            .update(id, studentDTO)
            .map(student => Ok(toJson(student)))
            .recover { case e: NoSuchElementException => NotFound(e.getMessage) }
      )
  }

  def destroy(id: Int): Action[AnyContent] = Action.async {
    studentService
      .delete(id)
      .map { _ => Ok("Student deleted") }
      .recover { case e: NoSuchElementException => NotFound(e.getMessage) }
  }
}

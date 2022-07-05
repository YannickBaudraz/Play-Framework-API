package v1

import play.api.libs.json.Json.toJson
import play.api.libs.json.{JsValue, Json, OFormat}
import play.api.mvc._

import javax.inject.{Inject, Singleton}

@Singleton
class StudentController @Inject() (
    val controllerComponents: ControllerComponents,
    studentService: StudentService
) extends BaseController {

  implicit val studentDTOJson: OFormat[StudentDTO] = Json.format[StudentDTO]
  implicit val studentJson: OFormat[Student] = Json.format[Student]

  def index: Action[AnyContent] = Action(Ok(toJson(studentService.getAll)))

  def show(id: Int): Action[AnyContent] = Action {
    val student = studentService.getById(id)
    student.fold(NotFound("Student not found"))(student => Ok(toJson(student)))
  }

  def create: Action[JsValue] = Action(parse.json) { request =>
    request.body
      .validate[StudentDTO]
      .fold(
        _ => BadRequest("Invalid student provided"),
        studentDTO => {
          studentService.create(studentDTO)
          Created(toJson(studentDTO))
        }
      )
  }

  def update(id: Int): Action[JsValue] = Action(parse.json) { request =>
    request.body
      .validate[StudentDTO]
      .fold(
        _ => BadRequest("Invalid student provided"),
        studentDTO => {
          studentService.update(id, studentDTO)
          Ok("Student updated")
        }
      )
  }

  def destroy(id: Int): Action[AnyContent] = Action {
    studentService.delete(id)
    Ok("Student deleted")
  }
}

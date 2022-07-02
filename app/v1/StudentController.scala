package v1

import play.api.libs.json.Json.toJson
import play.api.libs.json.{JsValue, Json, OFormat}
import play.api.mvc._

import javax.inject.{Inject, Singleton}
import scala.collection.mutable.ListBuffer

@Singleton
class StudentController @Inject() (
    val controllerComponents: ControllerComponents
) extends BaseController {

  implicit val studentJson: OFormat[StudentResource] =
    Json.format[StudentResource]
  implicit val studentDTOJson: OFormat[StudentDTO] =
    Json.format[StudentDTO]

  val students = new ListBuffer[StudentResource]
  students += StudentResource(1, "johndoe@gmail.com")
  students += StudentResource(2, "janedote@gmail.com")

  def index: Action[AnyContent] = Action(Ok(toJson(students)))

  def show(id: Int): Action[AnyContent] = Action {
    val student = students.find(_.id == id)
    student.fold {
      NotFound("Student not found")
    } { student =>
      Ok(toJson(student))
    }
  }

  def create: Action[JsValue] = Action(parse.json) { request =>
    request.body
      .validate[StudentDTO]
      .fold(
        _ => BadRequest("Invalid student provided"),
        studentDTO => {
          val nextId = students.map(_.id).max + 1
          val newStudent = StudentResource(nextId, studentDTO.email)
          students += newStudent

          Created(toJson(newStudent))
        }
      )
  }

  def update(id: Int): Action[JsValue] = Action(parse.json) { request =>
    request.body
      .validate[StudentDTO]
      .fold(
        _ => BadRequest("Invalid student id provided"),
        studentDTO => {
          val student = students.find(_.id == id)
          student.fold(NotFound("Student not found"))(student => {
            students -= student
            val updatedStudent = StudentResource(id, studentDTO.email)
            students += updatedStudent

            Ok(toJson(updatedStudent))
          })
        }
      )
  }
}

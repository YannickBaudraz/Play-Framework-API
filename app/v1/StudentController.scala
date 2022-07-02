package v1

import play.api.libs.json.{JsValue, Json, OFormat}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}

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

  def index: Action[AnyContent] = Action(Ok(Json.toJson(students)))

  def show(id: Int): Action[AnyContent] = Action {
    val student = students.find(_.id == id)
    student match {
      case Some(s) => Ok(Json.toJson(s))
      case None    => NotFound
    }
  }

  def create: Action[JsValue] = Action(parse.json) { implicit request =>
    request.body
      .validate[StudentDTO]
      .asOpt
      .fold { BadRequest("No valid student provided") } { student =>
        val nextId = students.map(_.id).max + 1
        val newStudent = StudentResource(nextId, student.email)
        students += newStudent
        Ok(Json.toJson(newStudent))
      }
  }
}

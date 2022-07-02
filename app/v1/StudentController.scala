package v1

import play.api.libs.json.{Json, OFormat}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}

import javax.inject.{Inject, Singleton}
import scala.collection.mutable.ListBuffer

@Singleton
class StudentController @Inject() (
    val controllerComponents: ControllerComponents
) extends BaseController {

  implicit val studentsJson: OFormat[StudentResource] =
    Json.format[StudentResource]

  val students = new ListBuffer[StudentResource]
  students += StudentResource(1, "johndoe@gmail.com", "John", "Doe")
  students += StudentResource(2, "janedote@gmail.com", "Jane", "Dow")

  def index: Action[AnyContent] = Action(Ok(Json.toJson(students)))
}

package api.v1.model

import play.api.libs.json._

sealed abstract class BaseModel extends Product with Serializable {
  val id: Option[Int]
  def copy(id: Option[Int] = id): BaseModel = this.copy(id)
}
object BaseModel {
  implicit val format: OFormat[BaseModel] = Json.format[BaseModel]
  implicit val formatStudent: OFormat[Student] = Json.format[Student]
  implicit val formatSchool: OFormat[School] = Json.format[School]
  implicit val formatStudentWithSchool: OFormat[StudentWithSchool] = Json.format[StudentWithSchool]
}

case class Student(
    id: Option[Int] = None,
    email: String,
    schoolId: Option[Int] = None
) extends BaseModel

case class School(
    id: Option[Int] = None,
    name: String,
    phone: Option[String] = None,
    email: Option[String] = None,
    website: Option[String] = None
) extends BaseModel

case class StudentWithSchool(
    id: Option[Int] = None,
    email: String,
    school: Option[School] = None
) extends BaseModel

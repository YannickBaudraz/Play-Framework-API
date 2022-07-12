package api.v1.model

import play.api.libs.json._

sealed abstract class BaseModel(
    id: Option[Int] = None
) extends Product
    with Serializable {
  def copy(id: Option[Int] = id): BaseModel = this.copy(id)
}
trait BaseModelCompanion[A <: BaseModel] {
  implicit val format: OFormat[A]
}
object BaseModel extends BaseModelCompanion[BaseModel] {
  implicit val format: OFormat[BaseModel] = Json.format[BaseModel]
}

case class Student(
    id: Option[Int] = None,
    email: String,
    schoolId: Option[Int] = None
) extends BaseModel
case object Student extends BaseModelCompanion[Student] {
  implicit val format: OFormat[Student] = Json.format[Student]
}

case class School(
    id: Option[Int] = None,
    name: String,
    phone: Option[String] = None,
    email: Option[String] = None,
    website: Option[String] = None
) extends BaseModel
case object School extends BaseModelCompanion[School] {
  implicit val format: OFormat[School] = Json.format[School]
}

case class StudentWithSchool(
    id: Option[Int] = None,
    email: String,
    school: Option[School] = None
) extends BaseModel
case object StudentWithSchool extends BaseModelCompanion[StudentWithSchool] {
  implicit val format: OFormat[StudentWithSchool] = Json.format[StudentWithSchool]
}

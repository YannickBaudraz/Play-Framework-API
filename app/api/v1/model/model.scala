package api.v1.model

import play.api.libs.json._

sealed abstract class BaseModel extends Product with Serializable with Cloneable {
  var id: Option[Int]
  def copy(id: Option[Int] = id): BaseModel = {
    val model = clone.asInstanceOf[BaseModel]
    model.id = id
    model
  }
}
trait BaseModelCompanion[A <: BaseModel] {
  implicit val format: OFormat[A]
}
object BaseModel extends BaseModelCompanion[BaseModel] {
  implicit val format: OFormat[BaseModel] = Json.format[BaseModel]
}

case class Student(
    var id: Option[Int],
    var email: String,
    var schoolId: Option[Int] = None
) extends BaseModel
object Student extends BaseModelCompanion[Student] {
  implicit val format: OFormat[Student] = Json.format[Student]
}

case class School(
    var id: Option[Int] = None,
    var name: String,
    var phone: Option[String] = None,
    var email: Option[String] = None,
    var website: Option[String] = None
) extends BaseModel
object School extends BaseModelCompanion[School] {
  implicit val format: OFormat[School] = Json.format[School]
}

case class StudentWithSchool(
    var id: Option[Int] = None,
    var email: String,
    var school: Option[School] = None
) extends BaseModel
object StudentWithSchool extends BaseModelCompanion[StudentWithSchool] {
  implicit val format: OFormat[StudentWithSchool] = Json.format[StudentWithSchool]
}

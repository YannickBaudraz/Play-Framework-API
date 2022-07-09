package api.v1.model.database.entity

import api.v1.model.{School, Student}
import slick.jdbc.H2Profile.api._
import slick.lifted.{ForeignKeyQuery, ProvenShape}
import slick.model.ForeignKeyAction.{Cascade, SetNull}

class StudentEntity(tag: Tag) extends Table[Student](tag, "students") {

  type SchoolFK = ForeignKeyQuery[SchoolEntity, School]

  def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def email: Rep[String] = column[String]("email")
  def schoolId: Rep[Option[Int]] = column[Option[Int]]("school_id")

  def school: SchoolFK = foreignKey("school_fk", schoolId, TableQuery[SchoolEntity])(_.id, Cascade, SetNull)

  def * : ProvenShape[Student] = (id.?, email, schoolId) <> (Student.tupled, Student.unapply)
}
